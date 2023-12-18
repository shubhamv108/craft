package code.shubham.craft;

import code.shubham.commons.annotations.SpringBootJpaApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("code.shubham")
@SpringBootJpaApplication
public class CraftApplication {

	public static void main(String[] args) {
		SpringApplication.run(CraftApplication.class, args);
	}

}
