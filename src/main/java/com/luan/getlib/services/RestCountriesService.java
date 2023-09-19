package com.luan.getlib.services;

import com.luan.getlib.models.Result;
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
    private final String COUNTRY_NOT_FOUND = "País não encontrado!";
    private final String AMBIGUOUS_COUNTRY_NAME = "O nome do país é muito ambíguo";
    private final String SUCCESS = "País encontrado!";
    
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

    public Result<String>getCurrencyByCountry(String country){
        url = getApiUrl(country);
        String response = client.getBody(this.url);
        if(response.isEmpty())
            return new Result<>(false, COUNTRY_NOT_FOUND, response);
        Matcher m = REGEX_ITEMS.matcher(response);
        if(!m.find())
            return null;

        return new Result<>(true, SUCCESS, m.group(1));
    }
    
    private String getApiUrl(String country){
        return url.replace("{countryCode}", country).replace(" ", "%20");
    }
}
