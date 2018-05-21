package es.ugarrio.emv.post.service;

import es.ugarrio.emv.post.domain.Post;
import es.ugarrio.emv.post.repository.PostRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PostService {

    private final Logger log = LoggerFactory.getLogger(PostService.class);

    @Autowired
    PostRespository postRespository;

    public Page<Post> findAll(Pageable pageable) {
        return postRespository.findAll(pageable);
    }

    public Optional<Post> find(String id) {
        return postRespository.findById(id);
    }

    public Post create (Post newPost) {
        return postRespository.save(newPost);
    }


    /**
     * Update all information for a specific post, and return the modified post.
     *
     * @param postUpdate post to update
     * @return updated post
     */
    public Optional<Post> update(Post postUpdate) {

        Optional<Post> post = postRespository.findOneById(postUpdate.getId());
        if (post.isPresent()) {
            post.get().setTitle(postUpdate.getTitle());
            post.get().setText(postUpdate.getText());
            post.get().setUserId(postUpdate.getUserId());
            postRespository.save(post.get());
            log.debug("Changed Information for Post: {}", post);
        }

        return post;


       /* return Optional.of(postRespository
                .findOneById(postUpdate.getId())
                .ifPresent(post -> {
                    post.setTitle(postUpdate.getTitle());
                    post.setText(postUpdate.getText());
                    post.setUserId(postUpdate.getUserId());
                    postRespository.save(post);
                    log.debug("Changed Information for Post: {}", post);
                   // //return new Optional<Post>(post);
                }));  */
    }


    public void delete(String id) {
        postRespository.findOneById(id).ifPresent(post -> {
            postRespository.delete(post);
            log.debug("Deleted Post: {}", post);
        });
    }
}
