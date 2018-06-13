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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    private final Logger log = LoggerFactory.getLogger(PostController.class);

    /**
     * GET /posts : get all posts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all post
     */
    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(Pageable pageable) {
        log.debug("REST request to get a page of Post");

        // Usar esta forma
        //pageable:  page=0&size=3&sort=name&name.dir=desc
        return new ResponseEntity<>(postService.findAll(pageable), HttpStatus.OK);
    }

    /**
     * GET /posts/:id : get the post.
     *
     * @param id the id of the post to find
     * @return the ResponseEntity with status 200 (OK) and with body the post, or with status 404 (Not Found)
     */
    @GetMapping("/{id:.+}")
    public ResponseEntity<?> getPost(@PathVariable String id) {
    	ResponseEntity<Post> data = ResponseUtil.wrapOrNotFound(postService.find(id));
        return ResponseUtil.wrapOrNotFound(postService.find(id));
    }

    /**
     * POST  /posts  : Creates a new post.
     *
     * @param post the post to create
     * @return the ResponseEntity with status 201 (Created) and with body the new post
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) throws URISyntaxException {
        log.debug("REST request to save Post : {}", post);

        Post newPost = postService.create(post);
        return ResponseEntity.created(new URI("/api/post/" + newPost.getId())).body(newPost);
    }

    /**
     * PUT /posts : Updates an existing Post.
     *
     * @param post the post to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated post
     */
    @PutMapping
    public ResponseEntity<Post> updatePost(@Valid @RequestBody Post post) {
        log.debug("REST request to update Post : {}", post);

        Optional<Post> updatedPost = postService.update(post);

        return ResponseUtil.wrapOrNotFound(updatedPost);
    }

    /**
     * DELETE /post/:id : delete the Post.
     *
     * @param id the id of the post to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id:.+}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        log.debug("REST request to delete Post: {}", id);
        postService.delete(id);
        return ResponseEntity.ok().build();
    }
}



// Test a nivel de integracion:
//      https://github.com/ryanmccormick/spring-boot-rest-best-practices/blob/master/src/test/java/com/example/integration/contacts/UpdateContactsTest.java
