package imdbRenamer;

import file.*;
import file.interfaces.*;
import web.Jsoup.*;
import web.interfaces.*;
import java.io.File;

public class Renamer {

    // Setup dependencies
    private static final ILoadUrl LOAD = new LoadUrl();
    private static final IParseElements FIND = new ParseElements();
    private static final IFileHandler FILE_HANDLER = new FileHandler();
    private static final IMetaData META_DATA = new MetaData();

    // Setup imdb search
    private static final String BASE_URL = "https://www.imdb.com";
    private static final String SEASON_NUMBERS_ID = "title-episode-widget";
    private static final String IMAGE_CONTAINER_CLASS = "list detail eplist";

    public static String Template = "";

    // Give arguments <url>, <template> and <show name>
    public static void main(String[] args) {
        String nameTemplate = "";

        if(args.length < 2) {
            System.out.println("No Serie specified");
            System.out.println("No Template specified");
            return;
        }else if (args.length < 3)
        {
            System.out.println("No Name specified");
            nameTemplate = "Season %d ~ Episode %d ~ %s";
        }

        Template = args[1];

        if(nameTemplate.equals(""))
        {
            nameTemplate = args[2] + " ~ Season %d ~ Episode %d ~ %s";
        }

        String[] href_episodes = getHrefs(args[0]);

        int index = 0;
        for (String link:href_episodes) {
            
            // If link not contains season go to next
            if(!link.contains("season="))
                continue;
            index++;

            String[] elements = getNames(link);
            File[] files = FILE_HANDLER.getSeriesFiles("Season " + index);

            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i].getName() + " => " + String.format(nameTemplate,  index,(i + 1),elements[i]));
                // Change the name of the file
                META_DATA.ChangeName(files[i], nameTemplate, index,(i + 1),elements[i]);
            }
        }
    }

    private static String[] getHrefs(String urlId)
    {
        // get html from url
        String document = LOAD.loadDocument(BASE_URL + urlId);

        // get the html of the season and years section
        String data_episodes = FIND.findById(document, SEASON_NUMBERS_ID);
        // get the urls of the seasons and years section
        String links_episodes = FIND.findByTag(data_episodes,"a");

        // filter to get the href attr
        return reverse(FIND.getAttribute(links_episodes,"a","href"));
    }

    private static String[] getNames(String link)
    {
        // Load the season link
        String doc = LOAD.loadDocument(BASE_URL + link);

        // Load the episode data
        String content = FIND.findByClass(doc,IMAGE_CONTAINER_CLASS);

        // get the alt of the images
        return FIND.getAttribute(content, "img","alt");
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
