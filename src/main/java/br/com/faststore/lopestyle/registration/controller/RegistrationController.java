package br.com.faststore.lopestyle.registration.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.faststore.lopestyle.models.Consumer;
import br.com.faststore.lopestyle.models.Employee;
import br.com.faststore.lopestyle.registration.service.RegistrationService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {
    
    private RegistrationService registrationService;

    @PostMapping("/registerConsumer")
    public ResponseEntity<String> register(@RequestBody Consumer consumer) {
        return ResponseEntity.ok().body(registrationService.register(consumer));
    }

    @PostMapping("dashboard/registerEmployee")
    public ResponseEntity<String> register(@RequestBody Employee employee) {
        return ResponseEntity.ok().body(registrationService.registerEmployee(employee));
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }


}
