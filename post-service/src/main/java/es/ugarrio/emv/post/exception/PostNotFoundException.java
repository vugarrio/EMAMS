package es.ugarrio.emv.post.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException  {
	private static final long serialVersionUID = 3200431700836048434L;

	public PostNotFoundException(String exception) {
		super(exception);
	}
}
