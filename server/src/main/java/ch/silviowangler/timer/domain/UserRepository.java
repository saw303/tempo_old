package ch.silviowangler.timer.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * @author Silvio Wangler
 */
public interface UserRepository extends CrudRepository<User, UUID> {
}
