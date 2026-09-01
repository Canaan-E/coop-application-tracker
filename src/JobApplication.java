import java.time.LocalDate;
import java.io.Serializable;

public class JobApplication implements Serializable {

    private String company;
    private String position;
    private LocalDate applicationDate;
    private ApplicationStatus status;
    private LocalDate deadline;
    private String notes;

    public JobApplication(
            String company,
            String position,
            LocalDate applicationDate,
            ApplicationStatus status,
            LocalDate deadline,
            String notes) {

        this.company = company;
        this.position = position;
        this.applicationDate = applicationDate;
        this.status = status;
        this.deadline = deadline;
        this.notes = notes;
    }public String getCompany() {
    return company;
}

public void setCompany(String company) {
    this.company = company;
}

public String getPosition() {
    return position;
}

public void setPosition(String position) {
    this.position = position;
}

public LocalDate getApplicationDate() {
    return applicationDate;
}

public void setApplicationDate(LocalDate applicationDate) {
    this.applicationDate = applicationDate;
}

public ApplicationStatus getStatus() {
    return status;
}

public void setStatus(ApplicationStatus status) {
    this.status = status;
}

public LocalDate getDeadline() {
    return deadline;
}

public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
}

public String getNotes() {
    return notes;
}

public void setNotes(String notes) {
    this.notes = notes;
}
@Override
public String toString() {
    return "Company: " + company
            + "\nPosition: " + position
            + "\nApplication Date: " + applicationDate
            + "\nStatus: " + status
            + "\nDeadline: " + deadline
            + "\nNotes: " + notes;
}
}