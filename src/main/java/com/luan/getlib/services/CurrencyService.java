package com.luan.getlib.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.luan.getlib.utils.ClientHttp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since v0.2.1
 * @author luanpozzobon
 */
public class CurrencyService {
    private String url;
    private final ClientHttp client;
    private final Pattern REGEX_ITEMS = Pattern.compile(".*:(\\{.+)\\}");
    private final String PROPERTIES = "config.properties";
    
    public CurrencyService(){
        Properties prop = new Properties();
        try(InputStream inputStream = ZipCodeService.class.getClassLoader().getResourceAsStream(PROPERTIES)){
            prop.load(inputStream);
            this.url = prop.getProperty("awesomeapi.url");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro durante a aquisição da URL: " + e);
        }
        client = new ClientHttp();
    }
    
    public double convertFromUSD(double value, String currency){
        String response = client.getBody(url + "USD-" + currency);
        
        if(!response.equals("")){
            JsonObject jObj;
            if((jObj = formatApiResponse(response)) != null){
                String bid = jObj.get("bid").getAsString();
                Matcher m = Pattern.compile("([0-9\\.]*)").matcher(bid);
                if(m.find()){
                    String item = m.group(1);
                    double dBid = Double.parseDouble(item);
                    return value * dBid;
                }
            }
        }
        
        return 0;
    }
    
    private JsonObject formatApiResponse(String response){
            Matcher m = REGEX_ITEMS.matcher(response);
            
            if(!m.find()) return null;
            String items = m.group(1);
            
            return JsonParser.parseString(items).getAsJsonObject();
    }
}
