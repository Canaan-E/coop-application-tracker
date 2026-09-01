import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileManager {

    private static final String FILE_NAME = "applications.dat";

    public static void saveApplications(
            ArrayList<JobApplication> applications) {

        try (
                ObjectOutputStream output =
                        new ObjectOutputStream(
                                new FileOutputStream(FILE_NAME)
                        )
        ) {
            output.writeObject(applications);
            System.out.println("Applications saved successfully.");

        } catch (IOException exception) {
            System.out.println(
                    "Unable to save applications: "
                            + exception.getMessage()
            );
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<JobApplication> loadApplications() {

        try (
                ObjectInputStream input =
                        new ObjectInputStream(
                                new FileInputStream(FILE_NAME)
                        )
        ) {
            return (ArrayList<JobApplication>) input.readObject();

        } catch (IOException | ClassNotFoundException exception) {
            return new ArrayList<>();
        }
    }
}