package es.ugarrio.emv.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.ugarrio.emv.user.domain.Authority;
import es.ugarrio.emv.user.domain.User;

/**
 * Spring Data MongoDB repository for the Authority entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {
	Optional<Authority> findByName(String name);
}

