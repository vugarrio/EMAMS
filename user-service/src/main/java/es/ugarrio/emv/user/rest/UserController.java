package es.ugarrio.emv.user.rest;

import javax.websocket.server.PathParam;

import es.ugarrio.emv.user.domain.User;
import es.ugarrio.emv.user.rest.util.PaginationUtil;
import es.ugarrio.emv.user.rest.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ugarrio.emv.user.service.UserService;
import es.ugarrio.emv.user.service.dto.UserDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;

    private final Logger log = LoggerFactory.getLogger(UserController.class);

	 /**
     * GET /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
           
    @GetMapping("/users")
    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
        log.debug("REST request to get a page of Users");

        // Usar esta forma
    	//pageable:  page=0&size=3&sort=name&name.dir=desc
        return new ResponseEntity<>(userService.getAllManagedUsers(pageable), HttpStatus.OK);
    }

    /* @GetMapping("/users/list")
    public ResponseEntity<List<UserDTO>> getAllUsers2(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    } */

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> getUser(@PathParam("id") String id) {
        return ResponseUtil.wrapOrNotFound(userService.getUserById(id).map(UserDTO::new));
    }
    
    
    
    // tutoriales de como hacerlo:
    
    // https://spring.io/guides/tutorials/bookmarks/
    // https://stackoverflow.com/questions/48215468/best-way-of-sending-rest-responses-in-spring-boot
    //
    
}
