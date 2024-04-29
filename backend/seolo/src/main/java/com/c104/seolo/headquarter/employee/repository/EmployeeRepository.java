package com.c104.seolo.headquarter.employee.repository;


import com.c104.seolo.headquarter.employee.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Employee findByEmployeeNum(String employeeNum) {
        String jpql = "SELECT e FROM Employee e WHERE e.employeeNum = :employeeNum";
        TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
        query.setParameter("employeeNum", employeeNum);
        return query.getResultStream().findFirst().orElse(null); // Java 8 스트림 API 사용
    }
}
