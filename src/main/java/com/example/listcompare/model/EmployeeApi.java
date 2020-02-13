package com.example.listcompare.model;

public interface EmployeeApi {

    String BASE_PATH=Constants.BASE_PATH;
    String ADD_EMPLOYEE="/addEmployee";
    String UPDATE_EMPLOYEE="/updateEmployee";

    String SORT_BY_NAME="/sortByName";
    String SORT_BY_ID="/sortById";
    String SORT_BY_SALARY = "/sortBySalary";
    String SORT_BY_DEPARTMENT="/sortByDepartment";

}
