package vn.techmaster.kiemtra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.kiemtra.model.Employer;
import vn.techmaster.kiemtra.repository.EmployerRepo;

@RestController
public class EmployerController {
    @Autowired
    private EmployerRepo repo;

    @GetMapping
    public List<Employer> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{last_name}/{email}")
    public List<Employer> getAllByLastNameAndEmail(@PathVariable("last_name") String lastName,
            @PathVariable("email") String email) {
        return repo.findByEmailAddressAndLastname(lastName, email);
    }

    @GetMapping("/{first_name}/{last_name}")
    public List<Employer> getAllByLastNameAndFirstName(@PathVariable("first_name") String firstName,
            @PathVariable("last_name") String lastName) {
        return repo.findByEmailAddressAndLastname(firstName, lastName);
    }

    @GetMapping("/{first_name}/{last_name}")
    public List<Employer> getAllByLastNameAndFirstNameASC(@PathVariable("first_name") String firstName,
            @PathVariable("last_name") String lastName) {
        return repo.findByLastnameOrderByFirstnameAsc(firstName, lastName);
    }

    @GetMapping("/{first_name}")
    public List<Employer> getAllByLastNameAndFirstNameASC(@PathVariable("first_name") String firstName) {
        return repo.findByFirstnameIgnoreCase(firstName);
    }
}
