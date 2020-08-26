package file;

import file.interfaces.IFileHandler;
import java.io.File;


public class FileHandler implements IFileHandler {

    @Override
    public File[] getSeriesFiles(String season) {
        File dir = new File(season);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0){
            throw new AssertionError();
        }
        files = FileSorter.sortFiles(files);
        return files;
    }
}
