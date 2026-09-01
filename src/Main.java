import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ApplicationManager manager =
            new ApplicationManager();

    public static void main(String[] args) {
        manager.setApplications(
            FileManager.loadApplications());
            
        boolean running = true;

        while (running) {
            displayMenu();

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addApplication();
                    break;

                case "2":
                    manager.displayAllApplications();
                    break;

                case "3":
                    updateApplicationStatus();
                    break;

                case "4":
                    removeApplication();
                    break;

                case "5":
                    searchApplications();
                    break;

                case "6":
                    filterApplicationsByStatus();
                    break;

                case "7":
                    FileManager.saveApplications(
                        manager.getApplications()
                    )
                    ;running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please choose from 1 to 7."
                    );
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n================================");
        System.out.println("  CO-OP APPLICATION TRACKER");
        System.out.println("================================");
        System.out.println("1. Add an application");
        System.out.println("2. View all applications");
        System.out.println("3. Update an application status");
        System.out.println("4. Remove an application");
        System.out.println("5. Search applications");
        System.out.println("6. Filter applications by status");
        System.out.println("7. Exit");
        System.out.println("================================");
    }

    private static void addApplication() {

        System.out.println("\n--- Add a New Application ---");

        System.out.print("Company: ");
        String company = scanner.nextLine();

        System.out.print("Position: ");
        String position = scanner.nextLine();

        LocalDate applicationDate =
            readDate("Application date (YYYY-MM-DD): ");

        ApplicationStatus status = readStatus();

        LocalDate deadline =
            readDate("Deadline (YYYY-MM-DD): ");

        System.out.print("Notes: ");
        String notes = scanner.nextLine();

        JobApplication application = new JobApplication(
                company,
                position,
                applicationDate,
                status,
                deadline,
                notes
        );

        manager.addApplication(application);
        saveApplications();
    }

    private static void updateApplicationStatus() {

        manager.displayAllApplications();

        int applicationNumber =
                readInteger("\nEnter the application number: ");

        ApplicationStatus newStatus = readStatus();

        manager.updateApplicationStatus(
                applicationNumber,
                newStatus
        );
        saveApplications();
    }

    private static void removeApplication() {

        manager.displayAllApplications();

       int applicationNumber = readInteger(
        "\nEnter the application number to remove: ");

        manager.removeApplication(applicationNumber);
        saveApplications();
    }

    private static void searchApplications() {

        System.out.print(
                "Enter a company name or position to search: "
        );

        String searchTerm = scanner.nextLine();
        manager.searchApplications(searchTerm);
    }

    private static void filterApplicationsByStatus() {

        ApplicationStatus status = readStatus();
        manager.filterApplicationsByStatus(status);
    }

    private static ApplicationStatus readStatus() {

    while (true) {
        System.out.println("\nAvailable statuses:");
        System.out.println(
                "SAVED, APPLIED, INTERVIEW, OFFER, REJECTED"
        );

        System.out.print("Enter status: ");
        String input = scanner.nextLine().trim().toUpperCase();

        try {
            return ApplicationStatus.valueOf(input);
        } catch (IllegalArgumentException exception) {
            System.out.println(
                    "Invalid status. Please enter one of the listed statuses."
            );
        }
    }
}private static LocalDate readDate(String prompt) {

    while (true) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException exception) {
            System.out.println(
                    "Invalid date. Please use the YYYY-MM-DD format."
            );
        }
    }
}

private static int readInteger(String prompt) {

    while (true) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            System.out.println(
                    "Invalid number. Please enter a whole number."
            );
        }
    }
}
private static void saveApplications() {
    FileManager.saveApplications(
            manager.getApplications()
    );
}
}