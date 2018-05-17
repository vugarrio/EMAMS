package es.ugarrio.emv.user.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class UserNotFoundException extends Exception {
    
    private String id;


}