package file;

import file.interfaces.IFileHandler;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;


public class FileHandler implements IFileHandler {

    @Override
    public File[] getSeriesFiles(String season) {
        File dir = new File(season);
        File[] files = dir.listFiles();
        assert files != null;
        files = FileSorter.sortFiles(files);
        return files;
    }
}
