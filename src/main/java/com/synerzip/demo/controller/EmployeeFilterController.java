package com.synerzip.demo.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.synerzip.demo.model.Employee;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class EmployeeFilterController {

    //Requirement: Only Admin can see password
    @GetMapping("/employee-filter")
    public MappingJacksonValue filterEmployeeProperty() {
        Employee employee = new Employee("vivek","vivekcs0114@gmail.com","vivek123");
        Set<String> filterOut = new HashSet<String>();
        filterOut.add("name");
        filterOut.add("email");
        return filterResponse(employee,"EmployeeBeanFilter", filterOut);
    }

    @GetMapping("/admin/employee-filter")
    public MappingJacksonValue filterEmployeeForAdmin() {
        Employee employee = new Employee("vivek","vivekcs0114@gmail.com","vivek123");
        Set<String> filterOut = new HashSet<String>();
        filterOut.add("email");
        filterOut.add("password");
        return filterResponse(employee,"EmployeeBeanFilter", filterOut);
    }

    public MappingJacksonValue filterResponse(Employee employee, String filterId, Set<String> props) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(props);
        FilterProvider filters = new SimpleFilterProvider().addFilter(filterId, filter);
        MappingJacksonValue mapping = new MappingJacksonValue(employee);
        mapping.setFilters(filters);
        return mapping;
    }
}
