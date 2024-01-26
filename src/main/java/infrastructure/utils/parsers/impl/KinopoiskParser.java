package infrastructure.utils.parsers.impl;

import domain.model.entity.MovieEntity;
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
            
//            var idx = reference.indexOf("/film/");
//            var path = reference.substring(idx);
            
            var sexyCookies = new CookiesUtils();

            var doc = Jsoup.connect(reference)
                    .headers(sexyCookies.headersBefore)
                    .headers(sexyCookies.headersAfter)
                    .cookies(sexyCookies.cookies)
                    .method(Connection.Method.GET)
                    .get();
            
            // var doc = Jsoup.connect(reference)
            //         .header("authority", "www.kinopoisk.ru")
            //         .header("method", "GET")
            //         .header("path", path)
            //         .header("scheme", "https")
            //         .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
            //         .header("Accept-Encoding", "gzip, deflate, br")
            //         .header("Accept-Language", "en-US,en;q=0.9")
            //         .header("Cache-Control", "max-age=0")
            //         .header("Referer", "https://www.kinopoisk.ru/lists/movies/theme_action_comdey/?utm_referrer=www.google.com")
            //         .header("Sec-Ch-Ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"")
            //         .header("Sec-Ch-Ua-Mobile", "?0")
            //         .header("Sec-Ch-Ua-Platform", "\"Windows\"")
            //         .header("Sec-Fetch-Dest", "document")
            //         .header("Sec-Fetch-Mode", "navigate")
            //         .header("Sec-Fetch-Site", "same-origin")
            //         .header("Sec-Fetch-User", "?1")
            //         .header("Upgrade-Insecure-Requests", "1")
            //         .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
            //
            //
            //
            //         .cookie("yandex_login", "tzswagbucksemail@gmail.com; ")
            //         .cookie("i=JJjxU/VELBA3OOWz/qOBDH8ulXMHALui33Ixunz/LCsYnICrdBJnnzlQG4qftvJ4uX6aPTBk2iR4QpV6iFK9kxlOO3I", "; ")
            //         .cookie("yandexuid", "33954861699345492; ")
            //         .cookie("L=B1RdVFZ8B3ADXUdnTVNmY04ARnRIeGRXQioYEwMrCCRVIDQ2BQwNOCsTKxUDFnobCh4", ".1699691607.15523.392120.46c0dfee1595bf69285bc3fa1f0c8838; ")
            //         .cookie("gdpr", "0; ")
            //         .cookie("_ym_uid", "1700581534862868100; ")
            //         .cookie("yuidss", "33954861699345492; ")
            //         .cookie("location", "1; ")
            //         .cookie("coockoos", "4; ")
            //         .cookie("crookie=KXr0UhI2sMWGi7dHfTlxmm28Tm6XVr2zsxY3eEbB60r2sQomvr0NugOjhIihQ/Ap6kkjyAOk6ZivVInN61XMFGaOQNc", "; ")
            //         .cookie("cmtchd=MTcwMjk5OTg2NjUwNQ=", "; ")
            //         .cookie("mobile", "no; ")
            //         .cookie("mda_exp_enabled", "1; ")
            //         .cookie("_csrf", "L4eJABp25NiIgD2Z3-uIqG-b; ")
            //         .cookie("disable_server_sso_redirect", "1; ")
            //         .cookie("_yasc=fX3S4d8f8YvHJPLiPDSeQcb1AhC1kGHI/M2FJTI8j5//hIyNlX/Pj0yBENxqqS8", "; ")
            //         .cookie("ya_sess_id", "3:1703323901.5.0.1699691607765:-IogsA:c6.1.2:1|1913634698.-1.0.1:345314738.3:1699691607|30:10221312.336745.hFXZH64SWxrZQqAAzEBNFlLFs2c; ")
            //         .cookie("sessar", "1.1185.CiBPWBkyORVt3vuuCyMVw9TRV6KvbKOv4EjtETeljULOOw.hmyWX3cAQflVDWiulFR-ViA2zRd5waFeDOChFjSA0E8; ")
            //         .cookie("ys", "udn.cDp0enN3YWdidWNrc2VtYWlsQGdtYWlsLmNvbQ%3D%3D#c_chck.1252088434; ")
            //         .cookie("mda2_beacon", "1703323901885; ")
            //         .cookie("sso_status", "sso.passport.yandex.ru:synchronized; ")
            //         .cookie("no-re-reg-required", "1; ")
            //         .cookie("_ym_isad", "1; ")
            //         .cookie("desktop_session_key", "ea010b8e2cbc443dd47c0dc1628893c9db91994b2bcf524d569a1d3741bc811593f0c06ca67612031a4774ac9f0aa764cb5010f0fd1d13f19033ff8bbfd055ad3e98e804fb007b9866f191951cc14bed9a716bd7f6b752e7dd99b55960c0b5fc; ")
            //         .cookie("desktop_session_key.sig", "-wTMd376tGadi_uHji_eXYkTVXE; ")
            //         .cookie("yp", "1703410305.yu.33954861699345492; ")
            //         .cookie("ymex", "1705915905.oyu.33954861699345492; ")
            //         .cookie("PHPSESSID", "f2ca799f8f4faab63691c33e8893dd14; ")
            //         .cookie("user_country", "ru; ")
            //         .cookie("yandex_gid", "131671; ")
            //         .cookie("tc", "1; ")
            //         .cookie("uid", "159979495; ")
            //         .cookie("kdetect", "1; ")
            //         .cookie("_ym_d", "1703325501")
            //         .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
            //
            //         .method(Connection.Method.GET)
            //         .get();
            
            return doc;
            
        } catch (IOException e) {
            throw new RuntimeException("Не удалось скачать инфу с сайта. Возможно, куки истекли. Подробности: " + e.getMessage());
        }
    }
}
