package az.company.bestpractices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = "az.*")
public class SpringbootBestPracticesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBestPracticesApplication.class, args);
    }

}
