package es.ugarrio.emv.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication  {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceApplication.class);
	
		
		
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
		
//		String protocol = "http";
//        if (env.getProperty("server.ssl.key-store") != null) {
//            protocol = "https";
//        }
//        log.info("\n----------------------------------------------------------\n\t" +
//                "Application '{}' is running! Access URLs:\n\t" +
//                "Local: \t\t{}://localhost:{}\n\t" +
//                "External: \t{}://{}:{}\n\t" +
//                "Profile(s): \t{}\n----------------------------------------------------------",
//            env.getProperty("spring.application.name"),
//            protocol,
//            env.getProperty("server.port"),
//            protocol,
//            InetAddress.getLocalHost().getHostAddress(),
//            env.getProperty("server.port"),
//            env.getActiveProfiles());
        
	}
	
}
