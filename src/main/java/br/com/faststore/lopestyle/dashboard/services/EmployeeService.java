package br.com.faststore.lopestyle.dashboard.services;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.faststore.lopestyle.dashboard.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.dashboard.services.Exceptions.ObjectNotFoundException;
import br.com.faststore.lopestyle.models.Employee;
import br.com.faststore.lopestyle.repositories.EmployeeRepository;
import br.com.faststore.lopestyle.repositories.UserRepository;

@Service
public class EmployeeService {
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeRepository repository;

    @Autowired(required = false)
    private UserRepository userRepository;

    public Employee getEmployee(int employeeId){
        Employee employee = repository.findById(employeeId).orElseThrow(() -> new ObjectNotFoundException(
                                "Objeto não encontrado! Id: " + employeeId + ", Tipo: " + Employee.class.getName()));
        return employee;
    }

    public Page<Employee> getEmployeesPageable(FilterDto employeesFilterDto) {
        PageRequest pageable = PageRequest.of(employeesFilterDto.getPage(), employeesFilterDto.getPageSize());
        Page<Employee> employees = repository.findAll(pageable);
        return employees;
    }

    public List<Employee> getBySearch(FilterDto employeesFilterDto) {
        List<Employee> employees = userRepository.findByFirstNameContaining(employeesFilterDto.getSearch());
        return employees;
    }

    public Employee insertEmployee(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return repository.save(employee);
    }

    public Employee updateEmployee(int employeeId, Employee employee) {
        Employee emp= repository.findById(employeeId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + employeeId + ", Tipo: " + Employee.class.getName()));
        Calendar dateNow = Calendar.getInstance();
        emp = Employee.builder()
                        .id(employeeId)
                        .email(employee.getEmail())
                        .password(employee.getPassword())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .active(employee.isActive())  
                        .admin(employee.isAdmin())
                        .createdAt(employee.getCreatedAt())
                        .updatedAt(dateNow)
                        .build();
                        
        return repository.save(emp);
    }

    public void deleteEmployee(int employeeId) {
        Employee employee = repository.findById(employeeId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + employeeId + ", Tipo: " + Employee.class.getName()));
        repository.delete(employee);
    }

}
