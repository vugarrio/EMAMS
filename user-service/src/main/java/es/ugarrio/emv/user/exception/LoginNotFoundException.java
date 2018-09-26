package es.ugarrio.emv.user.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class LoginNotFoundException extends Exception {
	private static final long serialVersionUID = 4898733823625708916L;
	private String login;
}
