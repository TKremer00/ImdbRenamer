package imdbRenamer;

import file.FileHandler;
import file.interfaces.IFileHandler;
import web.ParseElements;
import web.LoadUrl;
import web.interfaces.IParseElements;
import web.interfaces.ILoadUrl;
import java.io.File;

public class Renamer {

    private static final String BASE_URL = "https://www.imdb.com";

    public static void main(String[] args) {
        ILoadUrl load = new LoadUrl();
        IParseElements find = new ParseElements();
        IFileHandler fileHandler = new FileHandler();

        String document = load.loadDocument(BASE_URL + "/title/tt4189022/");

        String id = "title-episode-widget";

        String data_episodes = find.findById(document,id);

        String links_episodes = find.findByTag(data_episodes,"a");

        String[] href_episodes = find.getAttribute(links_episodes,"a","href");

        String[] elemtent = new String[0];
        for (String link:href_episodes) {
            // Get data
            if(!link.contains("season="))
                continue;

            String doc = load.loadDocument(BASE_URL + link);
            String content = find.findByClass(doc,"list detail eplist");
            elemtent = find.getAttribute(content, "img","alt");
        }

        for (String el :
                elemtent) {
            System.out.println(el);
        }

        File[] files = fileHandler.getSeriesFiles("Season 1");
        for (File file:files) {
            System.out.println(file.getName());
        }
    }
}
