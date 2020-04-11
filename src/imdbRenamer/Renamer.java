package imdbRenamer;

import file.*;
import file.interfaces.*;
import web.Jsoup.*;
import web.interfaces.*;
import java.io.File;

public class Renamer {

    private static final String BASE_URL = "https://www.imdb.com";

    private static String nameTemplate;
    private static final String seasonNumbersId = "title-episode-widget";

    // Give arguments <url> and <show name>
    public static void main(String[] args) {
        if(args.length == 1) {
            System.out.println("No Serie specified");
            System.out.println("No Name specified");
            return;
        }

        String urlId = args[0];
        String name = args[1];

        nameTemplate = name +" ~ Season %d ~ Episode %d ~ %s";
        // Setup dependencies
        ILoadUrl load = new LoadUrl();
        IParseElements find = new ParseElements();
        IFileHandler fileHandler = new FileHandler();
        IMetaData metaData = new MetaData();

        // get html from url
        String document = load.loadDocument(BASE_URL + "/title/tt4189022/");
        // get the html of the season and years section
        String data_episodes = find.findById(document,seasonNumbersId);
        // get the urls of the seasons and years section
        String links_episodes = find.findByTag(data_episodes,"a");
        // filter to get the href attr
        String[] href_episodes = reverse(find.getAttribute(links_episodes,"a","href"));

        int index = 0;
        for (String link:href_episodes) {
            // If link not contains season go to next
            if(!link.contains("season="))
                continue;
            index++;

            // Load the season link
            String doc = load.loadDocument(BASE_URL + link);
            // Load the episode data
            String content = find.findByClass(doc,"list detail eplist");
            // get the alt of the images
            String[] elements = find.getAttribute(content, "img","alt");

            File[] files = fileHandler.getSeriesFiles("Season " + index);
            for (int i = 0; i < files.length; i++) {
                System.out.println("Current file " + String.format(nameTemplate,  index,(i + 1),elements[i]));
                // Change the name of the file
                metaData.ChangeName(files[i], nameTemplate, index,(i + 1),elements[i]);
            }
            System.out.println("\n");
        }
    }

    private static String[] reverse(String[] a)
    {
        String[] b = new String[a.length];
        int j = a.length;
        for (String s : a) {
            b[j - 1] = s;
            j = j - 1;
        }
        return b;
    }
}
