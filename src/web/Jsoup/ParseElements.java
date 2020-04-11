package web.Jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import web.interfaces.IParseElements;

public class ParseElements implements IParseElements {

    private Document parse(String html)  {
        return Jsoup.parse(html);
    }

    @Override
    public String findById(String html, String id) {
        Document doc = parse(html);
        return doc.getElementById(id).html();
    }

    @Override
    public String findByTag(String html, String tag) {
        Document doc = parse(html);
        return doc.getElementsByTag(tag).toString();
    }

    @Override
    public String findByClass(String html, String name) {
        Document doc = parse(html);
        return doc.getElementsByClass(name).html();
    }

    @Override
    public String[] getAttribute(String html,String tag, String attr) {
        Document doc = parse(html);
        Elements elements = doc.getElementsByTag(tag);

        if(tag.equals("img"))
            elements.removeIf(element -> element.attr("alt").equals("loading"));

        String[] data = new String[elements.size()];

        for (int i = 0; i < elements.size(); i++)
            data[i] = elements.get(i).attr(attr);

        return data;
    }
}
