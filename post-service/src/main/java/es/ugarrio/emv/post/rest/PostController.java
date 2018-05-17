package es.ugarrio.emv.post.rest;

import es.ugarrio.emv.post.domain.Post;
import es.ugarrio.emv.post.rest.util.ResponseUtil;
import es.ugarrio.emv.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    private final Logger log = LoggerFactory.getLogger(PostController.class);

    @GetMapping
    public ResponseEntity<Page<Post>> getAllUsers(Pageable pageable) {
        log.debug("REST request to get a page of Post");

        // Usar esta forma
        //pageable:  page=0&size=3&sort=name&name.dir=desc
        return new ResponseEntity<>(postService.findAll(pageable), HttpStatus.OK);
    }


    @GetMapping("/{id:.+}")
    public ResponseEntity<Post> get(@PathVariable String id) {
        return ResponseUtil.wrapOrNotFound(postService.find(id));
    }

}
