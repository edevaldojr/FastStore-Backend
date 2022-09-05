package br.com.faststore.lopestyle.dashboard.services;


import br.com.faststore.lopestyle.security.UserSS;

import br.com.faststore.lopestyle.models.Consumer;
import br.com.faststore.lopestyle.models.Employee;
import br.com.faststore.lopestyle.models.User;
import br.com.faststore.lopestyle.repositories.UserRepository;

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

        User user = repo.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserSS(user.getId(), user.getEmail(), user.getPassword(), user.getPerfis());
    }

    

}
