package file_hierarchy.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileInfo {
    private final String path;
    private List<String> requirements;


    /*
    Constructing FileInfo with information about the path of the file and
    the files it requires.
     */
    public FileInfo(String path) {
        this.path = path;
        findRequirements();
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
            BufferedReader reader = new BufferedReader(new FileReader(path));
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
                //
            }
        } catch (FileNotFoundException e) {
            //
        }
    }


    public List<String> getRequirements() {
        return requirements;
    }
}
