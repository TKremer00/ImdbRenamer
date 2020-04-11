package file.interfaces;
import java.io.File;
import java.nio.file.Path;

public interface IMetaData {

    void ChangeName(File file,String template,int season, int episode, String name);

    void ChangeAttr(Path path, String attr, String value);
}
