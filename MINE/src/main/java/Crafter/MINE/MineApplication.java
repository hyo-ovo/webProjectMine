package Crafter.MINE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
public class MineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MineApplication.class, args);
		System.out.println("Hello World!");
	}
}
