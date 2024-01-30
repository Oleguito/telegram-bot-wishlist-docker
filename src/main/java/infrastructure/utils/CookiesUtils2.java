package infrastructure.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CookiesUtils2 {
    
    public Map <String, String> headers;
    
    public CookiesUtils2(String filePath) {
        String updatedCookies = "";
        
        try {
            updatedCookies = Files.readString(Path.of(filePath));
        } catch (Exception e) {
            e.getMessage();
        }
        
        updateCookies(updatedCookies);
    }
    
    public /* List <Map <String, String>> */ void updateCookies (String updatedCookies) {
        
        // List <Map <String, String>> result = new ArrayList <>();
        
        headers = prepareUpdatedCookies(updatedCookies);

        // return result;
    }
    
    private Map<String, String> getCookies(String cookiesString) {
        Map<String, String> result = new HashMap <>();
        
        cookiesString = cookiesString.substring(8);
        String[] cookiesStringSplit = cookiesString.split("\s");

        Arrays.stream(cookiesStringSplit).forEach(e -> {
            String[] split = e.split("=");
            result.put(split[0], split[1]);
        });
        
        return result;
    }
    
    private Map<String, String> getHeadersBefore(String before) {
        before = before.replaceAll(":", "");
        // before = before.replaceAll("\"", "\\\"");
        String[] beforeItems = before.split("\r\n");
        Map<String, String> headersBeforeMap = new HashMap <>();
        
        Arrays.stream(beforeItems).forEach(e -> {
            // System.out.println(e);
            // System.out.println("");
            String[] split = e.split("\t");
            headersBeforeMap.put(split[0], split[1]);
        });
        return headersBeforeMap;
    }
    
    private Map<String, String> prepareUpdatedCookies(String updatedCookies) {
        Map<String, String> result = new HashMap <>();
        
        updatedCookies = updatedCookies.replaceAll("\r", "");
        String[] split = updatedCookies.split("\n");
        
        for (int i = 0; i < split.length; i+=2) {
            String key = split[i + 0].replaceAll(":", "");
            String value = split[i + 1];
            result.put(key, value);
        }

        return result;
    }
}
