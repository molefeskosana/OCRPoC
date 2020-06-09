package com.ocrapi.myapp.services;

import java.net.URI;
import com.google.gson.Gson;
import com.ocrapi.myapp.model.DomainSpecificContentModel;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class DomainSpecificContentService
{
    public static void domainSpecificContentCalls()
    {
        HttpClient httpclient = HttpClients.createDefault();
        String model = "celebrities";
        try
        {
            URIBuilder builder = new URIBuilder("https://2idvision.cognitiveservices.azure.com/vision/v3.0/models/"+model+"/analyze");

            builder.setParameter("language", "en");
            builder.setParameter("model","celebrities");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "b788af3c5d534be98dd587be71cf3ec0");

            // Request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("url","https://www.thenational.ae/image/policy:1.250975:1499351578/image/jpeg.jpg?f=16x9&w=1200&$p$f$w=dfa40e8");

            StringEntity reqEntity = new StringEntity(requestBody.toString());
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            String responseString = new BasicResponseHandler().handleResponse(response);
            //HttpEntity entity = response.getEntity();

            if (responseString != null)
            {
                System.out.println("Domain Specific response body : "+responseString);
                Gson gson = new Gson();
                DomainSpecificContentModel dscModel = gson.fromJson(responseString,DomainSpecificContentModel.class);
                System.out.println(" My celebrities : "+ dscModel.getRequestId());

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}

