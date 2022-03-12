package personio.hr.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionHandlerAdvisorTest {

    @InjectMocks
    private ExceptionHandlerAdvisor exceptionHandlerAdvisor;

    @Test
    public void shouldHandleEmployeeInputExceptionCorrectly() {
        // Arrange
        List<String> inputs = Arrays.asList("X", "Y");
        MultipleRootFoundException multipleRootFoundException = new MultipleRootFoundException(inputs);

        // Act
        ResponseEntity<?> responseEntity = exceptionHandlerAdvisor.handleEmployeeInputException(multipleRootFoundException);

        // Assert
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(responseEntity.getBody(), multipleRootFoundException.getMessage());
    }

    @Test
    public void shouldHandleEmployeeNotFoundExceptionCorrectly() {
        // Arrange
        NoEmployeeFoundException noEmployeeFoundException = new NoEmployeeFoundException();

        // Act
        ResponseEntity<?> responseEntity = exceptionHandlerAdvisor.handleEmployeeNotFoundException(noEmployeeFoundException);

        // Assert
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertEquals(responseEntity.getBody(), noEmployeeFoundException.getMessage());
    }
}
