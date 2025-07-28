package MINE;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import MINE.service.UserService;

@SpringBootApplication(scanBasePackages = {"MINE"})
@EnableJpaAuditing
public class MineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MineApplication.class, args);
		System.out.println("Hello World!");
	}

	@Bean
	public CommandLineRunner initAdmin(UserService userService) {
		return args -> {
			if (userService.findByUsername("admin") == null) {
				userService.register("admin", "1234");
				System.out.println("✅ 기본 관리자 계정 생성됨: admin / 1234");
			}
		};
	}
}