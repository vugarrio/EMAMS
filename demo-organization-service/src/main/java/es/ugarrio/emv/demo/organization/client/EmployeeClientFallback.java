package es.ugarrio.emv.demo.organization.client;

import es.ugarrio.emv.demo.organization.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeClientFallback implements EmployeeClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeClientFallback.class);

    @Override
    public List<Employee> findByOrganization(Long organizationId) {
        LOGGER.info("EmployeeClientFallback -> findByOrganization -> ERROR Fallback hystrix");
        return new ArrayList<>();
    }
}
