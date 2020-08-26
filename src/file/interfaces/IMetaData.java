package file.interfaces;
import java.io.File;
import java.nio.file.Path;

public interface IMetaData {

    boolean ChangeName(File file, String template, int season, int episode, String name);

    void ChangeAttr(Path path, String attr, String value);
}
