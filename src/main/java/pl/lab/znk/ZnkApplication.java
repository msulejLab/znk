package pl.lab.znk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.lab.znk.domain.User;
import pl.lab.znk.repository.UserRepository;

@SpringBootApplication
public class ZnkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZnkApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoUsers(UserRepository userRepository) {
		return (args) -> {
			userRepository.save(new User("admin", "ap", "a@x.com"));
			userRepository.save(new User("bbb", "bp", "b@x.com"));
		};
	}
}
