package es.ugarrio.emv.post.repository;

import es.ugarrio.emv.post.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRespository extends MongoRepository<Post, String> {
    Optional<Post> findOneById(String id);
}

