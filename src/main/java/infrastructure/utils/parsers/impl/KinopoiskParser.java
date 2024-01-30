package infrastructure.utils.parsers.impl;

import domain.model.entity.MovieEntity;
import infrastructure.utils.CookiesUtils2;
import infrastructure.utils.parsers.Parser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import infrastructure.utils.CookiesUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class KinopoiskParser implements Parser {
    
    @Override
    public MovieEntity parse(String reference) {
        
        var doc = getDocument(reference);
        
        String[] split = reference.split("/");
        String id = split[split.length - 1];
        
        // String title = "Назад в будущее, 1985 — описание, интересные факты — Кинопоиск";
        String title = doc.title();
        
        split = title.split(",");
        String movieTitle = split[0];
        String movieYear = split[1].split("\s")[1];
//        String picture_href = doc.select("body").get(0).select("div#__next > div:nth-child(2) > div:nth-child(2) > main > div:nth-child(1) > div:nth-child(2) > div").get(0).select(" > div:nth-child(1) > div > div").get(0).select(" > div:nth-child(1)").get(0).select("a > img").attr("src");
//        String src = doc.select("img[class^='film-poster']").get(0).attr("src");

//        byte[] picture = getPicture(src);
        return MovieEntity.builder()
                .id(Integer.parseInt(id))
                .ref(reference)
                .title(movieTitle)
                .year(Integer.parseInt(movieYear))
//                .picture(picture)
                .build();
    }

    private byte[] getPicture(String href) {

        byte[] result = null;

        HttpURLConnection urlcon;
        URL connection = null;
        try {
            connection = new URL(href);

            urlcon = (HttpURLConnection) connection.openConnection();
            urlcon.setRequestMethod("GET");

            urlcon.connect();
            InputStream inputStream = urlcon.getInputStream();
            result = inputStream.readAllBytes();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    private Document getDocument(String reference) {
        try {
            // var sexyCookies = new CookiesUtils("src/main/resources/updatedCookiesFile");
            // var sexyCookiesOld = new CookiesUtils("src/main/resources/updatedCookiesFileOld");
            var sexyCookiesNew = new CookiesUtils2("src/main/resources/updatedCookiesFile");

            var doc = Jsoup.connect(reference)
                    .headers(sexyCookiesNew.headers)
                    .method(Connection.Method.GET)
                    .get();
            
            return doc;
            
        } catch (IOException e) {
            throw new RuntimeException("Не удалось скачать инфу с сайта. Возможно, куки истекли. Подробности: " + e.getMessage());
        }
    }
}
