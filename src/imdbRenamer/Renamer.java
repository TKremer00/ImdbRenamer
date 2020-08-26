package imdbRenamer;

import file.*;
import file.interfaces.*;
import web.Jsoup.*;
import web.interfaces.*;
import java.io.File;
import java.util.ArrayList;

public class Renamer {

    // Setup dependencies
    private static final ILoadUrl LOAD = new LoadUrl();
    private static final IParseElements FIND = new ParseElements();
    private static final IFileHandler FILE_HANDLER = new FileHandler();
    private static final IMetaData META_DATA = new MetaData();

    // Setup imdb search
    private static final String BASE_URL = "https://www.imdb.com/";
    private static final String SEASON_NUMBERS_ID = "title-episode-widget";
    private static final String IMAGE_CONTAINER_CLASS = "list detail eplist";

    public static String templateOld = "";

    private static String templateNew = "Season %d ~ Episode %d ~ %s";
    public static String folderName = "Season %d";
    private static String urlId = "";

    public static void main(String[] args) {
        if(args.length == 1){
            System.out.println("no arguments given");
            return;
        }

        for (String arg : args) {
            System.out.println(arg);

            if(arg.contains("-url=")){
                int firstIs = arg.indexOf("=");
                urlId = arg.substring(firstIs + 1);
                continue;
            }

            if(arg.contains("-templateOld=")){
                int firstIs = arg.indexOf("=");
                templateOld = arg.substring(firstIs + 1);
                if(!templateOld.contains("%d")){
                    System.out.println("No %d found in the template => " + templateOld);
                    return;
                }else if(templateOld.matches(".*%d.*%d.*")){
                    System.out.println("Found %d 2 times in the template => " + templateOld);
                    return;
                }
                continue;
            }

            if(arg.contains("-templateNew=")){
                int firstIs = arg.indexOf("=");
                templateNew = arg.substring(firstIs + 1);
                if(!templateNew.matches(".*%d.*%d.*") || !templateNew.contains("%s")){
                    System.out.println("No %d or %s found in the template => " + templateNew);
                    return;
                }
                continue;
            }

            if(arg.contains("-foldername=")){
                int firstIs = arg.indexOf("=");
                folderName = arg.substring(firstIs + 1);
                if(!folderName.contains("%d")){
                    System.out.println("No %d found in the folder name => " + folderName);
                    return;
                }
            }
        }
        String[] href_episodes = getHrefs(urlId);

        int index = 0;
        for (String link:href_episodes) {
            // If link not contains season go to next
            if(!link.contains("season="))
                continue;
            index++;

            String[] elements = removeDoubles(getNames(link));

            File[] files;
            try{
                files = FILE_HANDLER.getSeriesFiles(String.format(folderName,index));
            } catch (AssertionError ignored){
                continue;
            }
            System.out.println("\n");
            System.out.printf((folderName) + "%n",index);

            if (files == null || files.length == 0)
                continue;

            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i].getName() + " => " + String.format(templateNew,  index,(i + 1),elements[i]));
                // Change the name of the file
                META_DATA.ChangeName(files[i], templateNew, index,(i + 1),elements[i]);
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
        return FIND.getAttribute(content, "a","title");
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

    private static String[] removeDoubles(String[] a)
    {
        ArrayList<String> b = new ArrayList<>();

        for (String item :  a) {
            if (!item.equals("") && !b.contains(item))
                b.add(item);
        }
        return b.toArray(new String[0]);
    }
}
