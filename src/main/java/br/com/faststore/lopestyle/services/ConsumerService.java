package br.com.faststore.lopestyle.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;

import br.com.faststore.lopestyle.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.models.Consumer;
import br.com.faststore.lopestyle.models.User;
import br.com.faststore.lopestyle.repositories.ConsumerRepository;
import br.com.faststore.lopestyle.repositories.UserRepository;
import br.com.faststore.lopestyle.services.Exceptions.ObjectNotFoundException;

@Service
public class ConsumerService {

    @Autowired
    private ConsumerRepository repository;

    @Autowired(required = false)
    private UserRepository userRepository;

    public Consumer getConsumer(int consumerId){
        Consumer consumer = repository.findById(consumerId).orElseThrow(() -> new ObjectNotFoundException(
                                "Objeto não encontrado! Id: " + consumerId + ", Tipo: " + Consumer.class.getName()));
        return consumer;
    }

    public Page<Consumer> getConsumersPageable(FilterDto consumersFilterDto) {
        PageRequest pageable = PageRequest.of(consumersFilterDto.getPage(), consumersFilterDto.getPageSize());
        Page<Consumer> consumers = repository.findByEnabledTrue(pageable);
        return consumers;
    }

    public List<Consumer> getBySearch(FilterDto consumersFilterDto) {
        List<User> user = userRepository.findByActiveTrueAndFirstNameContaining(consumersFilterDto.getSearch());
        List<Consumer> consumers = new ArrayList<>();
        user.stream().forEach(u->{
            Optional<Consumer> consumer = repository.findById(u.getId());
            if(consumer.isPresent()){
                consumers.add(consumer.get());
            }
        } );
         
        return consumers;
    }

    public Consumer updateConsumer(int consumerId, Consumer Consumer) {
        Consumer emp= repository.findById(consumerId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + consumerId + ", Tipo: " + Consumer.class.getName()));
        emp = updateFields(emp, Consumer);
                        
        return repository.save(emp);
    }

    public void deleteConsumer(int consumerId) {
        Consumer Consumer = repository.findById(consumerId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + consumerId + ", Tipo: " + Consumer.class.getName()));
        repository.delete(Consumer);
    }

    public Consumer updateFields(Consumer emp, Consumer newEmp){
        Calendar dateNow = Calendar.getInstance();
        emp.setFirstName(newEmp.getFirstName());
        emp.setLastName(newEmp.getLastName());
        emp.setUpdatedAt(dateNow);
        return emp;
    }
}
