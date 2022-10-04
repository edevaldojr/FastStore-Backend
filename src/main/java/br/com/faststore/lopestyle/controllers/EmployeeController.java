package br.com.faststore.lopestyle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("employeeId") int employeeId) {
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }
    
    @GetMapping("/all")
    public ResponseEntity<Page<Employee>> getEmployees(@RequestBody FilterDto employeesFilterDto) {
        Page<Employee> employees = employeeService.getEmployeesPageable(employeesFilterDto);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestBody FilterDto employeesFilterDto) {
        List<Employee> employees = employeeService.getBySearch(employeesFilterDto);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/updateEmployee/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("employeeId") int employeeId,@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employee));
    }

    @DeleteMapping("/deleteEmployee/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("employeeId") int employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }
}
