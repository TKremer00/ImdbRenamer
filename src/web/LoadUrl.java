package web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import web.interfaces.ILoadUrl;

import java.io.IOException;

public class LoadUrl implements ILoadUrl {

    @Override
    public String loadDocument(String url) {
        try {
            Document  doc = Jsoup.connect(url).get();
            return doc.toString();
        }catch (IOException ex)  {
            System.out.println(ex.getMessage());
        }
        return "";
    }
}
