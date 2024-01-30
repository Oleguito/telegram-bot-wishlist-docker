package infrastructure.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CookiesUtils {
    
    public Map <String, String> headersBefore;
    public Map <String, String> headersAfter;
    public Map <String, String> cookies;
    
    public CookiesUtils(String filePath) {
        String updatedCookies = "";
        
        try {
            updatedCookies = Files.readString(Path.of(filePath));
        } catch (Exception e) {
            e.getMessage();
        }
        
        updateCookies(updatedCookies);
    }
    
    public List <Map <String, String>> updateCookies (String updatedCookies) {
        
        List <Map <String, String>> result = new ArrayList <>();
        
        String[] prepared = prepareUpdatedCookies(updatedCookies);
        
        String beforeString = prepared[0];
        String cookiesString = prepared[1];
        String afterString = prepared[2];
        
        this.headersBefore = getHeadersBefore(beforeString);
        this.headersAfter = getHeadersBefore(afterString);
        this.cookies = getCookies(cookiesString);
        
        result.add(headersBefore);
        result.add(headersAfter);
        result.add(cookies);
        
        return result;
    }
    
    private Map<String, String> getCookies(String cookiesString) {
        Map<String, String> result = new HashMap <>();
        
        cookiesString = cookiesString.substring(8);
        String[] cookiesStringSplit = cookiesString.split("\s");

        Arrays.stream(cookiesStringSplit).forEach(e -> {
            String[] split = e.split("=");
            String key = split[0].replaceAll(":","");
            String value = split[1];
            result.put(key, value);
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
    
    private String[] prepareUpdatedCookies(String updatedCookies) {
        updatedCookies = updatedCookies.replaceAll(":\r\n", ":\t");
        
        
        int cookiesBeginIndex = updatedCookies.indexOf("Cookie:");
        String before = updatedCookies.substring(0, cookiesBeginIndex).strip();
        
        updatedCookies = updatedCookies.substring(cookiesBeginIndex);
        
        int afterIndex = updatedCookies.indexOf("Sec-Ch-Ua:");
        
        String cookies = updatedCookies.substring(0, afterIndex).strip();
        
        String after = updatedCookies.substring(afterIndex).strip();
        
        return new String[] {before, cookies, after};
    }
}
