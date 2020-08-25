package file;

import file.interfaces.IMetaData;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class MetaData implements IMetaData {

    private String getExtension(String name) {
        int i = name.lastIndexOf('.');
        return name.substring(i).toLowerCase();
    }

    @Override
    public void ChangeName(File file,String template,int season, int episode, String name) {
        String sanitizedName = name.replaceAll("[^a-zA-Z0-9.\\-]", " ");

        String title = String.format(template,season,episode,sanitizedName);
        File new_file = new File("Season " + season + File.separator + title + getExtension(file.getName()));

        if(new_file.exists())
            return;

        boolean success = file.renameTo(new_file);
    }

    @Override
    public void ChangeAttr(Path path, String attr, String value) {
        try {
            Files.setAttribute(path, attr,value);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
