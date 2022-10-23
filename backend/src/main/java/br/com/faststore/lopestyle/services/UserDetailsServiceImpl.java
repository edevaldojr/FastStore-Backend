package br.com.faststore.lopestyle.services;


import br.com.faststore.lopestyle.security.UserSS;
import br.com.faststore.lopestyle.services.Exceptions.UserEmailNotEnabledException;
import br.com.faststore.lopestyle.models.User;
import br.com.faststore.lopestyle.repositories.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired(required=true)
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = repo.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(email);
        } else if(!user.get().getEnabled()) {
            throw new UserEmailNotEnabledException("Email " + email + " não verificado!");
        }

        return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getPassword(), user.get().getPerfis());
    }

    public int enableUser(String email) {
        return repo.enableUser(email);
    }

}
