package br.com.faststore.lopestyle.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faststore.lopestyle.models.Employee;
import br.com.faststore.lopestyle.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
    List<Employee> findByFirstNameContaining(String infix);

    User findByEmail(String email);


}