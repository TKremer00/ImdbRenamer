package file;

import file.interfaces.IMetaData;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MetaData implements IMetaData {

    private String getExtension(String name) {
        int i = name.lastIndexOf('.');
        return name.substring(i);
    }

    @Override
    public void ChangeName(File file,String template,int season, int episode, String name) {
        String title = String.format(template,season,episode,name);
        File new_file = new File("Season " + season + "\\" + title + getExtension(file.getName()));
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
