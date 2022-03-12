package personio.hr.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import personio.hr.domain.Employee;
import personio.hr.exception.InvalidValueException;
import personio.hr.exception.LoopHierarchyException;
import personio.hr.exception.MultipleRootFoundException;
import personio.hr.exception.NoEmployeeFoundException;
import personio.hr.repository.EmployeeRepository;
import personio.hr.service.implementation.EmployeeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void shouldGetCorrectExceptionMessageWhenCreateEmployeesWithInputEmpty() {
        // Arrange
        Map<String, String> inputTestEmployees = new HashMap<>();

        // Act
        InvalidValueException exception  = Assertions.assertThrows(InvalidValueException.class,
                () -> employeeService.createEmployees(inputTestEmployees));

        // Assert
        Assert.assertEquals(exception.getMessage(), new InvalidValueException().getMessage());
    }

    @Test
    public void shouldGetCorrectExceptionMessageWhenCreateEmployeesWithNullEmployeeValue() {
        // Arrange
        Map<String, String> inputTestEmployees = new HashMap<>();
        inputTestEmployees.put("X", "Y");
        inputTestEmployees.put("Z", null);

        // Act
        InvalidValueException exception  = Assertions.assertThrows(InvalidValueException.class,
                () -> employeeService.createEmployees(inputTestEmployees));

        // Assert
        Assert.assertEquals(exception.getMessage(), new InvalidValueException().getMessage());
    }

    @Test
    public void shouldGetCorrectExceptionMessageWhenCreateEmployeesWithEmptyEmployeeValue() {
        // Arrange
        Map<String, String> inputTestEmployees = new HashMap<>();
        inputTestEmployees.put("X", "Y");
        inputTestEmployees.put(" ", "Z");

        // Act
        InvalidValueException exception  = Assertions.assertThrows(InvalidValueException.class,
                () -> employeeService.createEmployees(inputTestEmployees));

        // Assert
        Assert.assertEquals(exception.getMessage(), new InvalidValueException().getMessage());
    }

    @Test
    public void shouldGetCorrectExceptionMessageWhenCreateEmployeesWithEmptySupervisorValue() {
        // Arrange
        Map<String, String> inputTestEmployees = new HashMap<>();
        inputTestEmployees.put("X", "Y");
        inputTestEmployees.put("Z", " ");

        // Act
        InvalidValueException exception  = Assertions.assertThrows(InvalidValueException.class,
                () -> employeeService.createEmployees(inputTestEmployees));

        // Assert
        Assert.assertEquals(exception.getMessage(), new InvalidValueException().getMessage());
    }

    @Test
    public void shouldGetCorrectExceptionMessageWhenCreateEmployeesWithSameEmployeeValue() {
        // Arrange
        Map<String, String> inputTestEmployees = new HashMap<>();
        inputTestEmployees.put("X", "Y");
        inputTestEmployees.put("Y", "Y");

        // Act
        InvalidValueException exception  = Assertions.assertThrows(InvalidValueException.class,
                () -> employeeService.createEmployees(inputTestEmployees));

        // Assert
        Assert.assertEquals(exception.getMessage(), new InvalidValueException("Y").getMessage());
    }

    @Test(expected = MultipleRootFoundException.class)
    public void shouldThrowMultipleRootFoundExceptionWhenCreateEmployees() {
        // Arrange
        Map<String, String> inputTestEmployees = new HashMap<>();
        inputTestEmployees.put("X", "Y");
        inputTestEmployees.put("Z", "O");

        // Act
        employeeService.createEmployees(inputTestEmployees);
    }

    @Test(expected = LoopHierarchyException.class)
    public void shouldThrowLoopHierarchyExceptionWhenCreateEmployees() {
        // Arrange
        Map<String, String> inputTestEmployees = new HashMap<>();
        inputTestEmployees.put("X", "Y");
        inputTestEmployees.put("Y", "X");

        // Act
        employeeService.createEmployees(inputTestEmployees);
    }

    @Test
    public void shouldCreateEmployeesHierarchySuccessfully() {
        // Arrange
        Map<String, String> inputTestEmployees = new HashMap<>();
        inputTestEmployees.put("X", "Y");
        inputTestEmployees.put("Y", "Z");

        // Act
        Map<String, Object> employeesHierarchy = employeeService.createEmployees(inputTestEmployees);

        // Assert
        Map<String, Object> secondSubHierarchy = new HashMap<>();
        secondSubHierarchy.put("X", new HashMap<>());

        Map<String, Object> firstSubHierarchy = new HashMap<>();
        firstSubHierarchy.put("Y", secondSubHierarchy);

        Map<String, Object> expectedEmployeesHierarchy = new HashMap<>();
        expectedEmployeesHierarchy.put("Z", firstSubHierarchy);

        Assert.assertEquals(employeesHierarchy, expectedEmployeesHierarchy);
    }

    @Test(expected = NoEmployeeFoundException.class)
    public void shouldThrowNoEmployeeFoundExceptionWhenGetEmployees() {
        // Arrange
        Mockito.when(employeeRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        employeeService.getEmployees();
    }

    @Test
    public void shouldGetEmployeesHierarchySuccessfully() {
        // Arrange
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("X", "Y"));
        employees.add(new Employee("Y", "Z"));

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        // Act
        Map<String, Object> employeesHierarchy = employeeService.getEmployees();

        // Assert
        Map<String, Object> secondSubHierarchy = new HashMap<>();
        secondSubHierarchy.put("X", new HashMap<>());

        Map<String, Object> firstSubHierarchy = new HashMap<>();
        firstSubHierarchy.put("Y", secondSubHierarchy);

        Map<String, Object> expectedEmployeesHierarchy = new HashMap<>();
        expectedEmployeesHierarchy.put("Z", firstSubHierarchy);

        Assert.assertEquals(employeesHierarchy, expectedEmployeesHierarchy);
    }

    @Test
    public void shouldGetSpecifiedEmployeeHierarchyCorrectly() {
        // Arrange
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("X", "Y"));
        employees.add(new Employee("Y", "Z"));
        employees.add(new Employee("Z", "O"));

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        // Act
        Map<String, Object> specifiedEmployeeHierarchy = employeeService.getSpecifiedEmployee("X");

        // Assert
        Map<String, Object> firstSubHierarchy = new HashMap<>();
        firstSubHierarchy.put("Z", new HashMap<>());

        Map<String, Object> expectedEmployeesHierarchy = new HashMap<>();
        expectedEmployeesHierarchy.put("Y", firstSubHierarchy);

        Assert.assertEquals(specifiedEmployeeHierarchy, expectedEmployeesHierarchy);
    }

    @Test(expected = NoEmployeeFoundException.class)
    public void shouldThrowNoEmployeeFoundExceptionWhenGetSpecifiedEmployee() {
        // Arrange
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("X", "Y"));
        employees.add(new Employee("Y", "Z"));

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        // Act
        employeeService.getSpecifiedEmployee("O");
    }

}
