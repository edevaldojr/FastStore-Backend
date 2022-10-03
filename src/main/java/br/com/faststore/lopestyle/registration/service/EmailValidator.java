package br.com.faststore.lopestyle.registration.service;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class EmailValidator implements Predicate<String>{

    @Override
    public boolean test(String t) {
        // regex validator email
        return true;
    }
    
}
