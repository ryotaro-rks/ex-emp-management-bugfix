package jp.co.sample.emp_management.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class AutoCompleteApiEmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/autocomplete", method = RequestMethod.GET)
	public List<String> autocomplete(String name) {
		List<Employee> employeeList = employeeService.showListByLikeName(name);
		List<String> employeeNameList = new ArrayList<>();
		for (Employee employee : employeeList) {
			employeeNameList.add(employee.getName());
		}
		return employeeNameList;
	}

}
