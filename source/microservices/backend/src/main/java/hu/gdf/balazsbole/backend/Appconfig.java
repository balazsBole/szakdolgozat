package hu.gdf.balazsbole.backend;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("hu.gdf.balazsbole.domain")
@EnableJpaRepositories("hu.gdf.balazsbole.domain.repository")
@ComponentScan(basePackages = {"hu.gdf.balazsbole"})
public class Appconfig {
}
