package file_hierarchy.file;

import file_hierarchy.exceptions.InvalidRequirementException;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileInfo {
    private final Path path;
    private List<String> requirements;
    protected List<Path> dependencies;


    /*
    Constructing FileInfo with information about the path of the file and
    the files it requires.
     */
    public FileInfo(Path path) {
        this.path = path;
        findRequirements();
        dependencies = new ArrayList<>();
    }


    /*
    Finding the requirements in the text of the file.
    The requirements are listed on separate lines using the structure:
    require '<path_to_the_file_from_the_root_catalogue>'
     */
    private void findRequirements() {
        requirements = new ArrayList<>();
        Pattern pattern = Pattern.compile("require ‘.*’");
        Matcher matcher;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path.toString()));
            try {
                String input;
                while ((input = reader.readLine()) != null) {
                    matcher = pattern.matcher(input.trim());
                    if (matcher.matches()) {
                        requirements.add(input.trim().split("[‘’]")[1]);
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Exception when working with the file: " + path);
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file " + path + "cannot be found.");
        }
    }


    /*
    Getting the private requirements.
     */
    public List<String> getRequirements() {
        return new ArrayList<>(requirements);
    }

    public Path getPath() {
        return path;
    }

    /*
    Forming the dependencies.
    Checking if the requirement is an existing filePath in the given folder.
     */
    public void formDependencies(String folder, List<Path> paths) {
        for (String requirement : getRequirements()) {
            try {
                Path requirementPath = Path.of(folder + File.separator + requirement + ".txt");
                if (paths.contains(requirementPath)) {
                    dependencies.add(requirementPath);
                } else {
                    throw new InvalidRequirementException(requirement);
                }
            } catch (InvalidRequirementException e) {
                System.out.println("Invalid requirement: " + requirement);
                System.out.println();
            }
        }
    }
}
