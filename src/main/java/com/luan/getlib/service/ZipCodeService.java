package com.luan.getlib.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.luan.getlib.models.Address;
import com.luan.getlib.utils.ClientHttp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author luanp
 */
public class ZipCodeService {
    private String url;
    private final ClientHttp client;
    private final Pattern REGEX_ITEMS = Pattern.compile(".*\\[(.+)\\].*");

    public ZipCodeService() {
        this.url = "https://app.zipcodebase.com/api/v1/search?apikey=" + "API_KEY";
        client = new ClientHttp();
    }
    
    public Address getAddressByZipCode(String zipCode){
        String response = client.getBody(url + "&codes=" + zipCode);
//        String response = "{\"query\":{\"codes\":[\"80520250\"],\"country\":null},\"results\":{\"80520250\":[{\"postal_code\":\"80520-250\",\"country_code\":\"BR\",\"latitude\":\"-25.50390000\",\"longitude\":\"-49.29080000\",\"city\":\"Bom Retiro\",\"state\":\"\",\"city_en\":\"Curitiba\",\"state_en\":\"Parana\",\"state_code\":\"18\",\"province\":\"Curitiba\",\"province_code\":\"4106902\"}]}}";
        if(response.equals("")){
            return null;
        }
        JsonObject jObj;
        if((jObj = formatApiResponse(response)) == null){
            return null;
        }
        
        return new Address(
            jObj.get("country_code").getAsString(),
            jObj.get("state_en").getAsString(),
            jObj.get("city_en").getAsString(),
            jObj.get("postal_code").getAsString()
        );

    }
    
    private JsonObject formatApiResponse(String response){      
        Matcher matcher = REGEX_ITEMS.matcher(response);
        if(!matcher.find()) return null;
        
        String items = matcher.group(1);
        
        if(items.charAt(0) == '"') return null;
        
        return JsonParser.parseString(items).getAsJsonObject();
    }
}
