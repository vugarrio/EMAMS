package es.ugarrio.emv.post.exception;

public class PostNotFoundException extends Exception {
    private String post;


    public PostNotFoundException(String post) {
        this.post = post;
    }
}
