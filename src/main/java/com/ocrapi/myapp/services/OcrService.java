package com.ocrapi.myapp.services;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class OcrService
{
    private static   List<Words>  allWords = new ArrayList<>();
    private static List<Words> allMultiPartWords = new ArrayList<>();
    public static void ocrCall()
    {
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://2idvision.cognitiveservices.azure.com/vision/v3.0/ocr");

            builder.setParameter("language", "en");
            builder.setParameter("detectOrientation", "true");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "b788af3c5d534be98dd587be71cf3ec0");

            JSONObject requestBody = new JSONObject();
            requestBody.put("url","https://licenceandpermits.co.za/wp-content/uploads/2020/01/Screen-Shot-2020-01-12-at-2.54.49-PM.png");
            System.out.println("My request body :  "+requestBody.toString());
            // Request body
            StringEntity reqEntity = new StringEntity(requestBody.toString());
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            String responseString = new BasicResponseHandler().handleResponse(response);
            getListOfWords(responseString);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void multipartTest(String path){

        HttpClient client = HttpClients.createDefault();
        //String textFileName = "C:\\Users\\molef\\Documents\\OCRDEMO\\MyID.jpg";

        File file = new File(path);

        try {
            URIBuilder builderUri = new URIBuilder("https://2idvision.cognitiveservices.azure.com/vision/v3.0/ocr");

            builderUri.setParameter("language", "en");
            builderUri.setParameter("detectOrientation", "true");

            URI uri = builderUri.build();

            HttpPost post = new HttpPost(uri);
            //post.setHeader("Content-Type", "application/json");
            post.setHeader("Ocp-Apim-Subscription-Key", "b788af3c5d534be98dd587be71cf3ec0");

            FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("url", fileBody);

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
        if (responseString != null)
        {
            System.out.println("OCR API RESPONSE BODY"+responseString);
            Gson gson = new Gson();
            OcrApiModel ocrApiModel = gson.fromJson(responseString,OcrApiModel.class);
            System.out.println("OCR API language: "+ ocrApiModel.getLanguage());
            System.out.println("region size : "+ocrApiModel.getRegions());
            allWords.clear();
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

