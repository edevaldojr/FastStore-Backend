package br.com.faststore.lopestyle.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.faststore.lopestyle.models.Consumer;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Integer> {
    
    Consumer findByEmail(String email);

    Page<Consumer> findByEnabledTrue(Pageable pageable);


}
