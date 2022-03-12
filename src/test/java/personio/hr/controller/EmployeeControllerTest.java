package personio.hr.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import personio.hr.service.IEmployeeService;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    @Mock
    private IEmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    public void shouldCreateEmployeesHierarchySuccessfully() {
        // Arrange
        Map<String, String> inputTestEmployees = new HashMap<>();
        inputTestEmployees.put("X", "Y");
        inputTestEmployees.put("Y", "Z");

        Map<String, Object> secondSubHierarchy = new HashMap<>();
        secondSubHierarchy.put("X", new HashMap<>());

        Map<String, Object> firstSubHierarchy = new HashMap<>();
        firstSubHierarchy.put("Y", secondSubHierarchy);

        Map<String, Object> expectedEmployeesHierarchy = new HashMap<>();
        expectedEmployeesHierarchy.put("Z", firstSubHierarchy);

        Mockito.when(employeeService.createEmployees(inputTestEmployees))
                .thenReturn(expectedEmployeesHierarchy);

        // Act
        ResponseEntity<Map<String, Object>> employeesHierarchyResponse = employeeController.createEmployees(inputTestEmployees);

        // Assert
        Assert.assertEquals(employeesHierarchyResponse.getStatusCode(), OK);
        Assert.assertEquals(employeesHierarchyResponse.getBody(), expectedEmployeesHierarchy);
    }

    @Test
    public void shouldGetEmployeesHierarchySuccessfully() {
        // Arrange
        Map<String, Object> secondSubHierarchy = new HashMap<>();
        secondSubHierarchy.put("X", new HashMap<>());

        Map<String, Object> firstSubHierarchy = new HashMap<>();
        firstSubHierarchy.put("Y", secondSubHierarchy);

        Map<String, Object> expectedEmployeesHierarchy = new HashMap<>();
        expectedEmployeesHierarchy.put("Z", firstSubHierarchy);

        Mockito.when(employeeService.getEmployees()).thenReturn(expectedEmployeesHierarchy);

        // Act
        ResponseEntity<Map<String, Object>> employeesHierarchyResponse = employeeController.getEmployees();

        // Assert
        Assert.assertEquals(employeesHierarchyResponse.getStatusCode(), OK);
        Assert.assertEquals(employeesHierarchyResponse.getBody(), expectedEmployeesHierarchy);
    }

    @Test
    public void shouldGetSpecifiedEmployeeHierarchyCorrectly() {
        // Arrange
        Map<String, Object> expectedEmployeesHierarchy = new HashMap<>();
        expectedEmployeesHierarchy.put("Z", new HashMap<>());

        Mockito.when(employeeService.getSpecifiedEmployee("Y"))
                .thenReturn(expectedEmployeesHierarchy);

        // Act
        ResponseEntity<Map<String, Object>> specifiedEmployeeHierarchyResponse = employeeController.getSpecifiedEmployee("Y");

        // Assert
        Assert.assertEquals(specifiedEmployeeHierarchyResponse.getStatusCode(), OK);
        Assert.assertEquals(specifiedEmployeeHierarchyResponse.getBody(), expectedEmployeesHierarchy);
    }

}
