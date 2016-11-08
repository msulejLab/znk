package pl.lab.znk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import pl.lab.znk.config.Constants;
import pl.lab.znk.config.DefaultProfileUtil;
import pl.lab.znk.config.JHipsterProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import pl.lab.znk.domain.Authority;
import pl.lab.znk.domain.User;
import pl.lab.znk.repository.AuthorityRepository;
import pl.lab.znk.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static pl.lab.znk.security.AuthoritiesConstants.*;

@ComponentScan
@EnableAutoConfiguration(exclude = { MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class })
@EnableConfigurationProperties({ JHipsterProperties.class, LiquibaseProperties.class })
public class ZnkApp {

    private static final Logger log = LoggerFactory.getLogger(ZnkApp.class);

    @Inject
    private Environment env;

    /**
     * Initializes znk.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="http://jhipster.github.io/profiles/">http://jhipster.github.io/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not" +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    @Bean
    public CommandLineRunner initApplicationData(AuthorityRepository authorityRepository, UserRepository userRepository) {
        return (args -> {
            Authority anonAuth = new Authority(ANONYMOUS);
            Authority adminAuth = new Authority(ADMIN);
            Authority userAuth = new Authority(USER);
            Authority teacherAuth = new Authority(TEACHER);
            Authority studentAuth = new Authority(STUDENT);

            Arrays.asList(anonAuth, adminAuth, userAuth, teacherAuth, studentAuth).forEach(authority ->
                authorityRepository.save(authority)
            );

            User anon = new User();
            anon.setAuthorities(new HashSet<>(Arrays.asList(anonAuth)));
            anon.setLogin("anonymoususer");
            anon.setPassword("$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG");
            anon.setActivated(true);
            anon.setCreatedDate(ZonedDateTime.now());
            anon.setCreatedBy("system");
            userRepository.save(anon);

            User system = new User();
            system.setAuthorities(new HashSet<>(Arrays.asList(adminAuth, userAuth)));
            system.setLogin("system");
            system.setPassword("$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG");
            system.setActivated(true);
            system.setCreatedDate(ZonedDateTime.now());
            system.setCreatedBy("system");
            userRepository.save(system);

            User admin = new User();
            admin.setAuthorities(new HashSet<>(Arrays.asList(adminAuth, userAuth)));
            admin.setLogin("admin");
            admin.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
            admin.setActivated(true);
            admin.setCreatedDate(ZonedDateTime.now());
            admin.setCreatedBy("system");
            userRepository.save(admin);

            User user = new User();
            user.setAuthorities(new HashSet<>(Arrays.asList(userAuth)));
            user.setLogin("user");
            user.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
            user.setActivated(true);
            user.setCreatedDate(ZonedDateTime.now());
            user.setCreatedBy("system");
            userRepository.save(user);
        });
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(ZnkApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\thttp://127.0.0.1:{}\n\t" +
                "External: \thttp://{}:{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            env.getProperty("server.port"),
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"));

    }
}
