package es.ugarrio.emv.demo.employee.controller;

import es.ugarrio.emv.demo.employee.domain.HostFactory;
import es.ugarrio.emv.demo.employee.domain.HostInfo;
import es.ugarrio.emv.demo.employee.model.Employee;
import es.ugarrio.emv.demo.employee.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class EmployeeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
    EmployeeRepository repository;
	
	@PostMapping("/")
	public Employee add(@RequestBody Employee employee) {
		LOGGER.info("Employee add: {}", employee);
		return repository.add(employee);
	}

	@GetMapping("/{id}")
	public Employee findById(@PathVariable("id") Long id) {
		LOGGER.info("Employee find: id={}", id);
		return repository.findById(id);
	}
	
	@GetMapping("/")
	public List<Employee> findAll() {
		LOGGER.info("Employee find");
		return repository.findAll();
	}
	
	@GetMapping("/department/{departmentId}")
	public List<Employee> findByDepartment(@PathVariable("departmentId") Long departmentId, HttpServletRequest request) {
		LOGGER.info("Employee find: departmentId={}", departmentId);
		LOGGER.info("Employee Host: {}", HostFactory.create(request));
		return repository.findByDepartment(departmentId);
	}
	
	@GetMapping("/organization/{organizationId}")
	public List<Employee> findByOrganization(@PathVariable("organizationId") Long organizationId, HttpServletRequest request) {
//		int num = 1;
//		if (num == 1) {
//			throw new RuntimeException();
//		}
		LOGGER.info("Employee find: organizationId={}", organizationId);
		LOGGER.info("Employee Host: {}", HostFactory.create(request));
		return repository.findByOrganization(organizationId);
	}

	@RequestMapping(value = "/echo", method = RequestMethod.GET, produces = "application/json")
	public HostInfo getHost(HttpServletRequest request) {

		return HostFactory.create(request);

	}
	
}
