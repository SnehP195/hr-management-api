package personio.hr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personio.hr.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
