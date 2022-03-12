# Personio-hr-management-api

Application helps HR manager Personia get a grasp of her ever-changing company’s hierarchy! Everyweek Personio receives a JSON of employees and their supervisors from her demanding CEO Chris,
who keeps changing his mind about how to structure his company. Personio wants a tool to help
her better understand the employee hierarchy and respond to employee’s queries about who
their boss is.

## Getting Started 

### Prerequisites

**Please install below (if not have already):**
```
1. Docker for desktop - for building and running applications 
2. Postman tool.
```

### 1.How to install

**At the project directory we need to run below command to build the docker image file:**
```
$ docker build -t personio-hr-management-app .
```

### 2.How to Run

**After build successful we will run our application by cmd:**
```
$ docker run -d -p 8282:8282 personio-hr-management-app:latest
```

### 3.How to test

**Postman to test our API's:**

```
Post: http://localhost:8282/employees (for creating employee hierarchy)
Get: http://localhost:8282/employees (for fetching the employee hierarchy structure)
Get: http://localhost:8282/employees/emp_name (for fetching employee's supervisor)
```

**Please set Basic Authentication type with user/pass when request to API's:**

```
Username: administrator
Password: administrator 
```
