package es.ugarrio.emv.post.repository;

import es.ugarrio.emv.post.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRespository extends MongoRepository<Post, String> {

}

