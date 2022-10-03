package br.com.faststore.lopestyle.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.faststore.lopestyle.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
    List<User> findByFirstNameContaining(String infix);

    Optional<User> findByEmail(String email);
    
    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUser(String email);



}