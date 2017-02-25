package ch.silviowangler.timer.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * @author Silvio Wangler
 */
public interface BookingRepository extends CrudRepository<Booking, UUID> {
}
