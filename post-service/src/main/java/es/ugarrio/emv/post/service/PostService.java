package es.ugarrio.emv.post.service;

import es.ugarrio.emv.post.domain.Post;
import es.ugarrio.emv.post.repository.PostRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRespository postRespository;

    public Page<Post> findAll(Pageable pageable) {
        return postRespository.findAll(pageable);
    }

    public Optional<Post> find(String id) {
        return postRespository.findById(id);
    }


}
