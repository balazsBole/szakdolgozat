package hu.gdf.balazsbole.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@EnableJpaRepositories("hu.gdf.balazsbole.domain.repository")
@EntityScan("hu.gdf.balazsbole.domain")
@SpringBootApplication(scanBasePackages = "hu.gdf.balazsbole")
public class EmailClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailClientApplication.class, args);
	}

}
