package com.luan.getlib.service;

import com.luan.getlib.utils.ClientHttp;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since v0.1.2
 * @author luanp
 */
public class RestCountriesService {
    private String url;
    private final ClientHttp client;
    private final String PROPERTIES = "config.properties";
    private final Pattern REGEX_ITEMS = Pattern.compile(":\\{\"(.{3})");
    
    public RestCountriesService(){
        Properties prop = new Properties();
        try(InputStream inputStream = RestCountriesService.class.getClassLoader().getResourceAsStream(PROPERTIES)){
            prop.load(inputStream);
            this.url = prop.getProperty("countries.url");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro durante a aquisição da URL: " + e);
        }
        
        client = new ClientHttp();
    }
    
    public String getCurrencyByCountry(String filter, String country){
        url = getApiUrl(country, filter);
        String response = client.getBody(this.url);
        if(response.equals("")){
            System.out.println("Não foi possível consultar a moeda local!");
            return null;
        }
        
        Matcher m = REGEX_ITEMS.matcher(response);
        if(!m.find()) return null;

        return m.group(1);
    }
    
    private String getApiUrl(String filter, String country){
        return url.replace("{filter}", filter).replace("{countryCode}", country);
    }
}
