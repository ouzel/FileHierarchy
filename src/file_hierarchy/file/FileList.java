package file_hierarchy.file;

import file_hierarchy.exceptions.InvalidSortingException;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class FileList {

    // Unsorted list of FileInfo.
    private final List<FileInfo> filesInfo;

    private List<FileInfo> sortedFilesInfo;


    public FileList(List<FileInfo> filesInfo) {
        this.filesInfo = new ArrayList<>(filesInfo);
    }


    public List<FileInfo> getSortedFilesInfo() {
        if (sortedFilesInfo != null) {
            return new ArrayList<>(sortedFilesInfo);
        }
        return new ArrayList<>();
    }


    /*
    Sorting the files using the information about them.
    After using this method the filesInfo should be in such order that it forms a hierarchy.
     */
    public void sort() throws InvalidSortingException {
        if (!filesInfo.isEmpty()) {
            List<FileInfo> sorted = new ArrayList<>();
            List<FileInfo> visited = new ArrayList<>();

            while (visited.size() < filesInfo.size()) {
                for (FileInfo file : filesInfo) {
                    if (!visited.contains(file)) {
                        emplace(file, sorted, visited);
                    }
                }
            }

            sortedFilesInfo = new ArrayList<>(sorted);
        }
    }


    /*
    Emplace each FileInfo into its correct position according to the sorting algorithm.
     */
    public void emplace(FileInfo currentInfo, List<FileInfo> sorted, List<FileInfo> visited)
            throws InvalidSortingException {
        visited.add(currentInfo);

        for (Path dependencyPath : currentInfo.dependencies) {
            for (FileInfo fileInfo : filesInfo) {
                if (fileInfo.getPath().toString().equals(dependencyPath.toString())) {

                    if (!sorted.contains(fileInfo)) {
                        if (visited.contains(fileInfo)) {
                            throw new InvalidSortingException("There is a problem with file: " +
                                    currentInfo.getPath().toString());
                        }
                        emplace(fileInfo, sorted, visited);
                    }
                    break;

                }
            }
        }

        sorted.add(currentInfo);
    }
}
