package br.com.faststore.lopestyle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.faststore.lopestyle.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.models.Employee;
import br.com.faststore.lopestyle.services.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("employeeId") int employeeId) {
        return ResponseEntity.ok().body(employeeService.getEmployee(employeeId));
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Page<Employee>> getEmployees(@RequestBody FilterDto employeesFilterDto) {
        Page<Employee> employees = employeeService.getEmployeesPageable(employeesFilterDto);
        return ResponseEntity.ok().body(employees);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestBody FilterDto employeesFilterDto) {
        List<Employee> employees = employeeService.getBySearch(employeesFilterDto);
        return ResponseEntity.ok().body(employees);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("employeeId") int employeeId,@RequestBody Employee employee) {
        employeeService.updateEmployee(employeeId, employee);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/eemployee/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("employeeId") int employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}
