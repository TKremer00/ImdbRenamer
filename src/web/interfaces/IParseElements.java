package web.interfaces;

public interface IParseElements {
    String findById(String html, String id);

    String findByTag(String html,String tag);

    String findByClass(String html, String name);

    String[] getAttribute(String html,String tag, String attr);
}
