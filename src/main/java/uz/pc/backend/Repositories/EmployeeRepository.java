package uz.pc.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import uz.pc.backend.Models.Employee;

import java.util.List;
@Service

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    @Override
    List<Employee> findAll();
    Employee findById(int id);
    Employee findByIdentification(String identification);
    Employee findByFirstNameStartsWithIgnoreCase(String lastName);
}
