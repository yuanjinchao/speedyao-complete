import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by speedyao on 2019/3/28.
 */
@SpringBootApplication(scanBasePackages = {"com.demo.**"})
@EntityScan(basePackages = "com.demo.po")
@EnableJpaRepositories(basePackages = "com.demo.dao")
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctf = SpringApplication.run(App.class, args);
    }
}
