package ch.silviowangler.timer.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Silvio Wangler
 */
@Entity
public class User implements Serializable {

  @Id
  @GeneratedValue
  private UUID id;
  @Column(nullable = false, unique = true)
  private String username;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Collection<Booking> bookings;

  public User() {
  }

  public User(String username) {
    this.username = username;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Collection<Booking> getBookings() {
    return bookings;
  }

  public void setBookings(Collection<Booking> bookings) {
    this.bookings = bookings;
  }
}
