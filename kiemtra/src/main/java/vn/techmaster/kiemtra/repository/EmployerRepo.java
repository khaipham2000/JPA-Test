package vn.techmaster.kiemtra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.techmaster.kiemtra.model.Employer;

public interface EmployerRepo extends JpaRepository<Employer,Long>{
    List<Employer> findByEmailAddressAndLastname(String email,String lastName);
    List<Employer> findByFirstNameAndLastname(String firstName,String lastName);
    List<Employer> findByLastnameOrderByFirstnameAsc(String firstName,String lastName);
    List<Employer> findByFirstnameIgnoreCase(String firstName);
}
