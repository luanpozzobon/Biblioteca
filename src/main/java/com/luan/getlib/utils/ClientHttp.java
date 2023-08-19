package com.luan.getlib.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @since v0.1.0
 * @author luanpozzobon
 */
public class ClientHttp {
    public String getBody(String url){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200){
                return response.body();
            } else {
                System.out.println("Erro ao consultar a URL!");
            }
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
}
