package es.ugarrio.emv.post.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;

import javax.annotation.PostConstruct;

@Configuration
public class PropertiesLogger {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertiesLogger.class);
	
	@Autowired
    private AbstractEnvironment environment;
	
	@PostConstruct
    public void printProperties() {
		
		logger.info("******************************************");
        logger.info("*********** PROFILES: {} ", String.join(", ", environment.getActiveProfiles()));
        logger.info("*********** SERVER PORT {} ", environment.getProperty("server.port"));
        logger.info("*********** APPLICATION NAME: {} ", environment.getProperty("spring.application.name"));
        logger.info("*********** INFO TEST: {} ", environment.getProperty("app.info"));
        logger.info("******************************************");
        
        
        logger.debug("----> debug: This is a debug message");
        logger.info("----> info: This is an info message");
        logger.warn("----> warn: This is a warn message");
        logger.error("----> error: This is an error message");

	}
}
