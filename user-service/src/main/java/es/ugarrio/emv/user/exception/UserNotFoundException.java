package es.ugarrio.emv.user.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserNotFoundException extends Exception {
    
    private static final long serialVersionUID = 8073629781213964638L;
	private String id;


}