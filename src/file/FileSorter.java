package file;

import imdbRenamer.Renamer;

import java.io.File;

class FileSorter {

    static File[] sortFiles(File[] files)
    {
        File[] tempFiles = new File[files.length];

        for (File file : files) {
            int episode = getEpisodeNumber(Renamer.Template, file.getName());
            tempFiles[episode - 1] = file;
        }
        return tempFiles;
    }

    // get episode number
    private static int getEpisodeNumber(String template, String toFormat)
    {
        String search = "%d";
        String templateNoSpace = template.replaceAll(" ","");
        String toFormatNospace = toFormat.replaceAll(" ","");
        int index = templateNoSpace.lastIndexOf(search);
        char after;
        if(index + search.length() < templateNoSpace.length()) {
            after = templateNoSpace.charAt(index + search.length());
            String removeFirst = toFormatNospace.substring(index);
            String removeLast = removeFirst.substring(0, removeFirst.lastIndexOf(after));
            return Integer.parseInt(removeLast);
        }else {
            String removeFirst = toFormatNospace.substring(index);
            String removeExtention = removeFirst.substring(0, removeFirst.lastIndexOf("."));
            return Integer.parseInt(removeExtention);
        }
    }
}
