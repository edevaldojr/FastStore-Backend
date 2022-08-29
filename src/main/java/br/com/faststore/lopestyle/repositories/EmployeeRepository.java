package br.com.faststore.lopestyle.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faststore.lopestyle.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    
    List<Employee> findByNameStartingWith(String prefix);

}