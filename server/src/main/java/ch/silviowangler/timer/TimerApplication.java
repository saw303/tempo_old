package ch.silviowangler.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.silviowangler.timer.domain.UserRepository;

@SpringBootApplication
public class TimerApplication {


	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(TimerApplication.class, args);
	}
}
