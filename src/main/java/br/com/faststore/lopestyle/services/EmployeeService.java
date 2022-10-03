package br.com.faststore.lopestyle.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.faststore.lopestyle.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.models.Employee;
import br.com.faststore.lopestyle.models.User;
import br.com.faststore.lopestyle.models.enums.Perfil;
import br.com.faststore.lopestyle.repositories.EmployeeRepository;
import br.com.faststore.lopestyle.repositories.UserRepository;
import br.com.faststore.lopestyle.services.Exceptions.ObjectNotFoundException;

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
        List<User> user = userRepository.findByFirstNameContaining(employeesFilterDto.getSearch());
        List<Employee> employees = new ArrayList<>();
        user.stream().forEach(u->{
            Optional<Employee> employee = repository.findById(u.getId());
            if(employee.isPresent()){
                employees.add(employee.get());
            }
        } );
        return employees;
    }

    public Employee insertEmployee(Employee employee) {
        employee.addPerfil(Perfil.EMPLOYEE);
        if(employee.isAdmin())employee.addPerfil(Perfil.ADMIN);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return repository.save(employee);
    }

    public Employee updateEmployee(int employeeId, Employee employee) {
        Employee emp= repository.findById(employeeId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + employeeId + ", Tipo: " + Employee.class.getName()));
        emp = updateFields(emp, employee);
                        
        return repository.save(emp);
    }

    public void deleteEmployee(int employeeId) {
        Employee employee = repository.findById(employeeId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + employeeId + ", Tipo: " + Employee.class.getName()));
        repository.delete(employee);
    }

    public Employee updateFields(Employee emp, Employee newEmp){
        Calendar dateNow = Calendar.getInstance();
        emp.setFirstName(newEmp.getFirstName());
        emp.setLastName(newEmp.getLastName());
        emp.setAdmin(emp.checkAdmin(newEmp.isAdmin()));
        emp.setUpdatedAt(dateNow);
        return emp;
    }

}
