package es.ugarrio.emv.user.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import es.ugarrio.emv.user.config.Constants;
import es.ugarrio.emv.user.domain.Authority;
import es.ugarrio.emv.user.domain.User;
import es.ugarrio.emv.user.exception.LoginNotFoundException;
import es.ugarrio.emv.user.exception.UserNotFoundException;
import es.ugarrio.emv.user.repository.AuthorityRepository;
import es.ugarrio.emv.user.repository.UserRepository;
import es.ugarrio.emv.user.service.dto.UserDTO;
import es.ugarrio.emv.user.service.util.RandomUtil;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AuthorityRepository authorityRepository;
        
    
    public void existOrFail(String... ids) throws UserNotFoundException {
        for (String id : ids) {
        	get(id);
        }
    }
    
    public User get(String id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
    
    public Page<UserDTO> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::new);
    }
    
    public Optional<User> findByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }
    
    public User readByLogin(String login) throws LoginNotFoundException {
        return userRepository.findOneByLogin(login).orElseThrow(() -> new LoginNotFoundException(login));
    }
    


//    /**
//     * Update basic information (first name, last name, email, language) for the current user.
//     *
//     * @param firstName first name of user
//     * @param lastName last name of user
//     * @param email email id of user
//     * @param langKey language key
//     * @param imageUrl image URL of user
//     */
//    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
//        SecurityUtils.getCurrentUserLogin()
//            .flatMap(userRepository::findOneByLogin)
//            .ifPresent(user -> {
//                user.setFirstName(firstName);
//                user.setLastName(lastName);
//                user.setEmail(email);
//                user.setLangKey(langKey);
//                user.setImageUrl(imageUrl);
//                userRepository.save(user);
//                log.debug("Changed Information for User: {}", user);
//            });
//    }
    
    
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                .map(authorityRepository::findByName)
            	.filter(Optional::isPresent)
            	.map(Optional::get)	
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        //String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        String encryptedPassword = "xxx";
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }
    

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
            .findById(userDTO.getId()))
        	.filter(Optional::isPresent)
            .map(Optional::get)		
            .map(user -> {
                user.setLogin(userDTO.getLogin());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.setEmail(userDTO.getEmail());
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO.getAuthorities().stream()
                    .map(authorityRepository::findByName)
                    .filter(Optional::isPresent)
                	.map(Optional::get)	
                    .forEach(managedAuthorities::add);
                userRepository.save(user);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    public Optional<User> getUserWithAuthorities(String id) {
    	
    	return userRepository.findById(id);
    }
    
    public Optional<User> getUserById(String id) {
    	
    	return userRepository.findById(id);
    }

    
    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }
    
    
    /**
    * Not activated users should be automatically deleted after 3 days.
    * <p>
    * This is scheduled to get fired everyday, at 01:00 (am).
    */
   @Scheduled(cron = "0 0 1 * * ?")
   public void removeNotActivatedUsers() {
       List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS));
       for (User user : users) {
           log.debug("Deleting not activated user {}", user.getLogin());
           userRepository.delete(user);
       }
   }
    

}
