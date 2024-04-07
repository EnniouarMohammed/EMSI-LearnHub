package ma.xproce.emsilearnhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EmsiLearnHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmsiLearnHubApplication.class, args);
    }

}
