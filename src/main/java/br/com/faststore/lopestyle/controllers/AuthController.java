package br.com.faststore.lopestyle.controllers;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.faststore.lopestyle.controllers.dto.CredentialsDTO;
import br.com.faststore.lopestyle.controllers.dto.EmailDTO;
import br.com.faststore.lopestyle.security.JWTUtil;
import br.com.faststore.lopestyle.security.UserSS;
import br.com.faststore.lopestyle.services.AuthService;
import br.com.faststore.lopestyle.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @PostMapping("/refreshToken")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
        service.sendNewPassword(objDto.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Void> forgot(@RequestBody CredentialsDTO objDto) {
        service.changePassword(objDto.getEmail(), objDto.getPassword());
        return ResponseEntity.ok().build();
    }
}
