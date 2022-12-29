package file_hierarchy.file;

import file_hierarchy.exceptions.InvalidSortingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class FileManager {
    // FileManager manages some folder.
    private final String folder;


    /*
    Constructor of a FileManager, which is used to work with a given path;
     */
    public FileManager(String folder) {
        this.folder = folder;
    }


    /*
    Walk through all the files in the given folder and its subfolders and
    create the list of paths.
     */
    public List<Path> getFilePaths() {
        try (Stream<Path> paths = Files.walk(Paths.get(folder))) {
            return paths
                    .filter(Files::isRegularFile)
                    .toList();
        } catch (IOException e) {
            System.out.println("There was an error while walking through the folder");
            return new ArrayList<>();
        }
    }


    public List<FileInfo> getFilesInfo(List<Path> paths) {
        List<FileInfo> filesInfo = new ArrayList<>();
        for (Path path : paths) {
            FileInfo currentFileInfo = new FileInfo(path);
            currentFileInfo.formDependencies(folder, paths);
            filesInfo.add(currentFileInfo);
        }
        return filesInfo;
    }


    /*
    Method for concatenating all the files if it's possible.
     */
    public void concatenateAll() {
        FileList fileList = new FileList(getFilesInfo(getFilePaths()));
        try {
            fileList.sort();
            for (FileInfo info : fileList.getSortedFilesInfo()) {
                System.out.println(info.getPath().toString());
            }
        } catch (InvalidSortingException e) {
            System.out.println("This folder cannot be turned into a file hierarchy.");
            System.out.println("Check the files for cyclic dependencies.");
        }
    }
}
