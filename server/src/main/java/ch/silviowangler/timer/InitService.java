package ch.silviowangler.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import ch.silviowangler.timer.domain.User;
import ch.silviowangler.timer.domain.UserRepository;

/**
 * @author Silvio Wangler
 */
@Component
public class InitService {

  private UserRepository userRepository;

  @Autowired
  public InitService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void init() {

    User angy = new User("angela");
    User silvio = new User("silvio");

    userRepository.save(angy);
    userRepository.save(silvio);

  }
}
