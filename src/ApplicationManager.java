import java.util.ArrayList;

public class ApplicationManager {

    private ArrayList<JobApplication> applications;

    public ApplicationManager() {
        applications = new ArrayList<>();
    }

    public void addApplication(JobApplication application) {
        applications.add(application);
        System.out.println("Application added successfully.");
    }

    public void displayAllApplications() {
        if (applications.isEmpty()) {
            System.out.println("No applications found.");
            return;
        }

        for (int i = 0; i < applications.size(); i++) {
            System.out.println("\n--- Application " + (i + 1) + " ---");
            System.out.println(applications.get(i));
        }
    }

    public int getApplicationCount() {
        return applications.size();
    }
    public void updateApplicationStatus(
        int applicationNumber,
        ApplicationStatus newStatus) {

    int index = applicationNumber - 1;

    if (index < 0 || index >= applications.size()) {
        System.out.println("Invalid application number.");
        return;
    }

    JobApplication application = applications.get(index);
    application.setStatus(newStatus);

    System.out.println("Application status updated successfully.");
}
public void removeApplication(int applicationNumber) {

    int index = applicationNumber - 1;

    if (index < 0 || index >= applications.size()) {
        System.out.println("Invalid application number.");
        return;
    }

    JobApplication removedApplication = applications.remove(index);

    System.out.println(
            "Application for "
                    + removedApplication.getPosition()
                    + " at "
                    + removedApplication.getCompany()
                    + " was removed successfully."
    );
}
public void searchApplications(String searchTerm) {

    boolean found = false;
    String normalizedSearchTerm = searchTerm.toLowerCase();

    for (JobApplication application : applications) {

        String company = application.getCompany().toLowerCase();
        String position = application.getPosition().toLowerCase();

        if (company.contains(normalizedSearchTerm)
                || position.contains(normalizedSearchTerm)) {

            System.out.println("\n--- Matching Application ---");
            System.out.println(application);
            found = true;
        }
    }

    if (!found) {
        System.out.println("No matching applications found.");
    }
}
public void filterApplicationsByStatus(ApplicationStatus status) {

    boolean found = false;

    for (JobApplication application : applications) {

        if (application.getStatus() == status) {
            System.out.println("\n--- Matching Application ---");
            System.out.println(application);
            found = true;
        }
    }

    if (!found) {
        System.out.println(
                "No applications found with the status " + status + "."
        );
    }
}public ArrayList<JobApplication> getApplications() {
    return applications;
}

public void setApplications(
        ArrayList<JobApplication> applications) {

    this.applications = applications;
}
}