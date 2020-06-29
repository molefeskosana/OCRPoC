package com.ocrapi.myapp.services;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.ocrapi.myapp.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.*;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.imgscalr.Scalr;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

@Service
public class OcrService
{
    private static   List<Words>  allWords = new ArrayList<>();
    private static List<Words> allMultiPartWords = new ArrayList<>();

    public static void ocrCall(String name )
    {
        allWords.clear();
        HttpClient httpclient = HttpClients.createDefault();

        if(!name.isEmpty()) {

            String image = "http://fff6d91e0063.ngrok.io/"+name;

            try {
                URIBuilder builder = new URIBuilder("http://localhost:1880/test");

                builder.setParameter("language", "en");
                builder.setParameter("detectOrientation", "true");

                URI uri = builder.build();
                HttpPost request = new HttpPost(uri);
                request.setHeader("Content-Type", "application/json");
                //request.setHeader("Ocp-Apim-Subscription-Key", "b788af3c5d534be98dd587be71cf3ec0");

                JSONObject requestBody = new JSONObject();
                requestBody.put("url", image);
                System.out.println("My request body :  " + requestBody.toString());
                // Request body
                StringEntity reqEntity = new StringEntity(requestBody.toString());
                request.setEntity(reqEntity);

                HttpResponse response = httpclient.execute(request);
                String responseString = new BasicResponseHandler().handleResponse(response);
                getListOfWords(responseString);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void multipartTest(String path){

        HttpClient client = HttpClients.createDefault();
        //String textFileName = "C:\\Users\\molef\\Documents\\OCRDEMO\\IMG-20200625-WA0015.jpg";

        File file = new File(path);

        try {
            //URIBuilder builderUri = new URIBuilder("https://2idvision.cognitiveservices.azure.com/vision/v3.0/ocr");
            URIBuilder builderUri = new URIBuilder("http://127.0.0.1:1880/test");
            //builderUri.setParameter("language", "en");
            //builderUri.setParameter("detectOrientation", "true");

            /*BufferedImage img = ImageIO.read(file); // load image
            BufferedImage scaledImg = Scalr.resize(img, Scalr.Method.QUALITY, 460, 200);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( scaledImg,"png", baos );
            baos.flush();
            byte[] imageInByte = baos.toByteArray();*/

            URI uri = builderUri.build();

            HttpPost post = new HttpPost(uri);
            //post.setHeader("Ocp-Apim-Subscription-Key", "b788af3c5d534be98dd587be71cf3ec0");

            //FileBody fileBody = new FileBody(file, ContentType.MULTIPART_FORM_DATA);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            //builder.addPart("image", fileBody);
            builder.addBinaryBody("image",fileContent);

            HttpEntity entity = builder.build();
//
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            String responseStringMultipart = new BasicResponseHandler().handleResponse(response);
            System.out.println("Multipart Response : " + responseStringMultipart);
            getListOfWords(responseStringMultipart);
        }catch(Exception ex){
            System.out.println("exception : ");
            ex.printStackTrace();
        }
    }

    private static void getListOfWords(String responseString){
        allWords.clear();
        if (responseString != null)
        {
            System.out.println("OCR API RESPONSE BODY"+responseString);
            Gson gson = new Gson();
            OcrApiModel ocrApiModel = gson.fromJson(responseString,OcrApiModel.class);
            System.out.println("OCR API language: "+ ocrApiModel.getLanguage());
            System.out.println("region size : "+ocrApiModel.getRegions());

            for(Regions region: ocrApiModel.getRegions()){
                System.out.println("region size : "+ocrApiModel.getRegions());
                for (Lines lines: region.getLines()){
                    System.out.println("lines size : "+region.getLines().size());
                    for (Words word: lines.getWords()) {
                        System.out.println("my words : "+ word.getText());
                        //newWord.setText(word.getText());
                        allWords.add(word);

                    }
                }
            }
            System.out.println("word list : "+allWords.size());
        }
    }

    public static List<Words> getAllWords(){
        System.out.println("all words"+ allWords.size());
        return allWords;
    }

}

