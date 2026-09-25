package com.canaan.cooptracker;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class ApplicationController {

    private final JobApplicationRepository repository;

    public ApplicationController(JobApplicationRepository repository) {
        this.repository = repository;
    }

    // READ
    @GetMapping("/applications")
    public List<JobApplication> getApplications() {
        return repository.findAll();
    }

    // CREATE
    @PostMapping("/applications")
    public JobApplication addApplication(
            @RequestBody JobApplication application) {

        application.setId(null);
        return repository.save(application);
    }

    // UPDATE
    @PutMapping("/applications/{id}")
    public JobApplication updateApplication(
            @PathVariable Long id,
            @RequestBody JobApplication updatedApplication) {

        JobApplication application =
                repository.findById(id).orElse(null);

        if (application == null) {
            return null;
        }

        application.setCompany(updatedApplication.getCompany());
        application.setPosition(updatedApplication.getPosition());
        application.setStatus(updatedApplication.getStatus());
        application.setDateApplied(updatedApplication.getDateApplied());
        application.setDeadline(updatedApplication.getDeadline());
        application.setLocation(updatedApplication.getLocation());
        application.setJobUrl(updatedApplication.getJobUrl());
        application.setNotes(updatedApplication.getNotes());

        return repository.save(application);
    }

    // DELETE
    @DeleteMapping("/applications/{id}")
    public String deleteApplication(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            return "Application not found";
        }

        repository.deleteById(id);

        return "Application deleted successfully";
    }
}