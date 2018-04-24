package com.synerzip.demo.controller;

import com.synerzip.demo.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeFilterController {

    @GetMapping("/employee-filer")
    public Employee filterEmployeeProperty() {
        return new Employee("vivek","vivekcs0114@gmail.com","vivek123");
    }
}
