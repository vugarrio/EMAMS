package es.ugarrio.emv.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserExistsException extends Exception {
    
    private String username;
}
