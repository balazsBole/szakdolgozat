package hu.gdf.balazsbole.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("hu.gdf.balazsbole.domain.repository")
@EntityScan("hu.gdf.balazsbole.domain")
@SpringBootApplication(scanBasePackages = "hu.gdf.balazsbole")
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
