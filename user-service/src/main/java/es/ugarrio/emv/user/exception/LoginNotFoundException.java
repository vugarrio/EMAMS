package es.ugarrio.emv.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginNotFoundException extends Exception {
	 private String login;
}
