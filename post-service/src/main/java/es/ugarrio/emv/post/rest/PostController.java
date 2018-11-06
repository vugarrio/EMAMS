package es.ugarrio.emv.post.rest;

import es.ugarrio.emv.post.domain.Post;
import es.ugarrio.emv.post.rest.util.ResponseUtil;
import es.ugarrio.emv.post.service.PostService;
import es.ugarrio.emv.post.service.dto.PostDTO;
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

    private final PostService postService;

    private final Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * GET /posts : get all posts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all post, or with status 204 (NO_CONTENT)
     */
    @GetMapping
    public ResponseEntity<Page<PostDTO>> getAllPosts(Pageable pageable) {
        log.debug("REST request to get a page of Post");

        // Usar esta forma
        //pageable:  page=0&size=3&sort=name&name.dir=desc
        HttpStatus status = HttpStatus.NO_CONTENT;
        Page<PostDTO> pagePosts = postService.findAll(pageable);
        if (pagePosts.hasContent()) {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(pagePosts, status);
    }

    /**
     * GET /posts/:id : get the post.
     *
     * @param id the id of the post to find
     * @return the ResponseEntity with status 200 (OK) and with body the post, or with status 404 (Not Found)
     */
    @GetMapping("/{id:.+}")
    public ResponseEntity<?> getPost(@PathVariable String id) {
    	return ResponseUtil.wrapOrNotFound(postService.find(id));
    }

    /**
     * POST  /posts  : Creates a new post.
     *
     * @param postDTO the post to create
     * @return the ResponseEntity with status 201 (Created) and with body the new post
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) throws URISyntaxException {
        log.debug("REST request to save postDTO : {}", postDTO);

        PostDTO newPost = postService.save(postDTO);
        return ResponseEntity.created(new URI("/api/post/" + newPost.getId())).body(newPost);
    }

    /**
     * PUT /posts : Updates an existing Post.
     *
     * @param postDTO the post to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated post, or with status 400 (Bad Request) if the post has already an ID
     */
    @PutMapping
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO) {
        log.debug("REST request to update postDTO : {}", postDTO);
        return ResponseUtil.wrapOrBadRequest(Optional.ofNullable(postService.save(postDTO)));
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

// http://websystique.com/spring-boot/spring-boot-rest-api-example/

//    Hacer con hateoas
//   https://github.com/jonyfs/credit-card-api/tree/master/src/main/java/br/com/jonyfs/credit/card/api



// Test a nivel de integracion:
//      https://github.com/ryanmccormick/spring-boot-rest-best-practices/blob/master/src/test/java/com/example/integration/contacts/UpdateContactsTest.java

// Ejemplos con jHipster
// - Hostel:  https://github.com/benoyprakash/java-hostel/tree/master/JHipster-hostel/src/main/java/com/hostel/web/rest
// - https://github.com/ivangsa/jhipster-mongodb-sample-projects/blob/master/jhipster-mongodb-with-dto-pagination/src/main/java/com/mycompany/myapp/web/rest/PaymentDetailsResource.java

// Pendiente:
//  - Testear las validaciones
//  - Crear interface de service