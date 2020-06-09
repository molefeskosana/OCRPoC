package com.ocrapi.myapp.services;

import java.io.InputStream;
import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ocrapi.myapp.model.ImageTagModel;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ImageTagService
{
    public static void tagImageCalls()
    {
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://2idvision.cognitiveservices.azure.com/vision/v3.0/tag");

            builder.setParameter("language", "en");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "b788af3c5d534be98dd587be71cf3ec0");


            // Request body
            JSONObject requestBody =new JSONObject();
            requestBody.put("url", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Atomist_quote_from_Democritus.png/338px-Atomist_quote_from_Democritus.png");
            StringEntity reqEntity = new StringEntity(requestBody.toString());
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            String responseString = new BasicResponseHandler().handleResponse(response);

            if (responseString != null)
            {

                System.out.println("Tag Image Response body : "+ responseString);//EntityUtils.toString(entity));
                Gson gson = new Gson();
                ImageTagModel imageTagModel = gson.fromJson(responseString,ImageTagModel.class);
                System.out.println("image id: "+ imageTagModel.getRequestId());

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
