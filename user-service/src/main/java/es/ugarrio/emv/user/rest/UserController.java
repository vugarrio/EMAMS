package es.ugarrio.emv.user.rest;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ugarrio.emv.user.service.UserService;
import es.ugarrio.emv.user.service.dto.UserDTO;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	 /**
     * GET /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
           
    @GetMapping("/users")
    public Page<UserDTO> getAllUsers(Pageable pageable) {
    	// Usar esta forma
    	//pageable:  page=0&size=3&sort=name&name.dir=desc
        return userService.getAllManagedUsers(pageable);
    }
    
    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserDTO getUser(@PathParam("id") String id) {
    	return null; /************************/
    }
    
    
    
    // tutoriales de como hacerlo:
    
    // https://spring.io/guides/tutorials/bookmarks/
    // https://stackoverflow.com/questions/48215468/best-way-of-sending-rest-responses-in-spring-boot
    //
    
}
