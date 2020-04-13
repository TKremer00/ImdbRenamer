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
        String templateNoSpace = template.replaceAll(" ","");
        String toFormatNospace = toFormat.replaceAll(" ","");
        int index = templateNoSpace.lastIndexOf("%d");
        char after = templateNoSpace.charAt(index  + "%d".length());

        String removeFirst = toFormatNospace.substring(index);
        String removeLast = removeFirst.substring(0, removeFirst.lastIndexOf(after));
        return Integer.parseInt(removeLast);
    }
}
