package file;

import file.interfaces.IFileHandler;
import java.io.File;


public class FileHandler implements IFileHandler {


    @Override
    public File[] getSeriesFiles(String season) {
        File dir = new File(season);
        return dir.listFiles();
    }
}
