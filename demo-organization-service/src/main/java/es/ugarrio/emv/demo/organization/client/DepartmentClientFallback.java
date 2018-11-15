package es.ugarrio.emv.demo.organization.client;


import es.ugarrio.emv.demo.organization.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentClientFallback implements DepartmentClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentClientFallback.class);

    @Override
    public List<Department> findByOrganization(Long organizationId) {
        LOGGER.info("DepartmentClientFallback -> findByOrganization -> ERROR Fallback hystrix");
        return new ArrayList<>();
    }

    @Override
    public List<Department> findByOrganizationWithEmployees(Long organizationId) {
        LOGGER.info("DepartmentClientFallback -> findByOrganizationWithEmployees -> ERROR Fallback hystrix");
        return new ArrayList<>();
    }
}
