package service;

import java.util.List;
import java.util.Optional;

import model.Employee;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long id);

    void deleteEmployee(Long id);
}
