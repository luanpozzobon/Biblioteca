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
    private final int OK_CODE = 200;
    
    public String getBody(String url){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == OK_CODE){
                return response.body();
            } else {
                System.out.println("Erro ao consultar a URL!");
                return "";
            }
            
        } catch (IOException | InterruptedException e) {
            System.out.println("Ocorreu uma falha durante a consulta à URL: " + e);
            return "";
        }
    }
}
