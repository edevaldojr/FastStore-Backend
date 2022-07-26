package br.com.faststore.lopestyle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faststore.lopestyle.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{
    
}