package pl.nowak.producter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class}
)
@EnableJpaAuditing
@ComponentScan(basePackages = {"pl.nowak.producter", "springfox"})
public class ProducterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducterApplication.class, args);
    }
}

