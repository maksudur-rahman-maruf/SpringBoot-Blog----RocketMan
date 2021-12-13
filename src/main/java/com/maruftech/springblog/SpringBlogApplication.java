package com.maruftech.springblog;

import com.maruftech.springblog.config.CustomUserDetails;
import com.maruftech.springblog.entities.Role;
import com.maruftech.springblog.entities.User;
import com.maruftech.springblog.repositories.UserRepository;
import com.maruftech.springblog.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class SpringBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBlogApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner setupDefaultUser(UserService service, UserRepository repository) {
        return args -> {
            if(repository.count() == 0) {
                service.save(new User(
                        "user", //username
                        "user", //password
                        Arrays.asList(new Role("USER"), new Role("ACTUATOR"))//roles
                ));
                service.save(new User(
                        "admin", //username
                        "admin", //password
                        Arrays.asList(new Role("USER"), new Role("ADMIN"), new Role("ACTUATOR"))//roles
                ));
            }
        };
    }

// CommandLineRunner Details

//    @Component
//    public class CommandLineAppStartupRunner implements CommandLineRunner {
//        private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);
//
//        @Override
//        public void run(String...args) throws Exception {
//            logger.info("Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.", Arrays.toString(args));
//        }
//    }






}
