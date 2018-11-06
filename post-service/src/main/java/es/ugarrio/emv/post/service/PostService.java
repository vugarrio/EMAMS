package es.ugarrio.emv.post.service;

import es.ugarrio.emv.post.domain.Post;
import es.ugarrio.emv.post.repository.PostRespository;
import es.ugarrio.emv.post.service.dto.PostDTO;
import es.ugarrio.emv.post.service.mapper.PostMapper;
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

    private PostRespository postRespository;

    private PostMapper postMapper;

    @Autowired
    public PostService(PostRespository postRespository, PostMapper postMapper) {
        this.postRespository = postRespository;
        this.postMapper = postMapper;
    }

    public Page<PostDTO> findAll(Pageable pageable) {
        return postRespository.findAll(pageable).map(
                post -> postMapper.postToPostDto(post)
        );
    }

    public Optional<PostDTO> find(String id) {
        return postRespository.findById(id).map(
                post -> postMapper.postToPostDto(post)
        );
    }

    public PostDTO save(PostDTO postDTO) {
        log.debug("Request to save Post : {}", postDTO);
        PostDTO postResult = null;
        if (null == postDTO.getId()) {
            postResult = postMapper.postToPostDto(postRespository.save(postMapper.postDTOToPost(postDTO)));
        } else {
            postResult = postMapper.postToPostDto(postRespository.findOneById(postDTO.getId()).map(
                    postBD -> {
                        postBD.setText(postDTO.getText());
                        postBD.setTitle(postDTO.getTitle());
                        //postBD.setLastModifiedBy("xxx");
                        postBD.setLastModifiedDate(Instant.now());
                        return postRespository.save(postBD);
                    }

            ).orElse(null));
        }
        return postResult;
    }




    public void delete(String id) {
        postRespository.findOneById(id).ifPresent(post -> {
            postRespository.delete(post);
            log.debug("Deleted Post: {}", post);
        });
    }
}


// Buen ejempl Jhister:  https://github.com/ivangsa/jhipster-mongodb-sample-projects/blob/master/jhipster-mongodb-with-dto-pagination/src/main/java/com/mycompany/myapp/service/impl/PaymentDetailsServiceImpl.java

// Hacer asi:  https://github.com/ryanmccormick/spring-boot-rest-best-practices/blob/master/src/main/java/com/example/service/ContactServiceImpl.java

// Control de errores:  https://github.com/ryanmccormick/spring-boot-rest-best-practices/blob/master/src/main/java/com/example/exceptions/ExceptionHandlers.java

