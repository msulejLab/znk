package pl.lab.znk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.lab.znk.domain.Authority;
import pl.lab.znk.domain.Consultation;
import pl.lab.znk.domain.User;
import pl.lab.znk.repository.AuthorityRepository;
import pl.lab.znk.repository.ConsultationRepository;
import pl.lab.znk.repository.UserRepository;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static pl.lab.znk.security.AuthoritiesConstants.*;

@Configuration
public class DummyDataInitializer {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initApplicationData() {
        return args -> {
            initAuthorities();

            User anon = createUser("anonymoususer", "anonymoususer", "anonymoususer@localhost", ANONYMOUS_AUTHORITY);
            User system = createUser("system", "system", "system@localhost", ADMIN_AUTHORITY, USER_AUTHORITY);
            User admin = createUser("admin", "admin", "admin@localhost", ADMIN_AUTHORITY, USER_AUTHORITY);
            User user = createUser("user", "user", "user@localhost", USER_AUTHORITY);
            User teacher = createUser("teacher", "teacher", "teacher@p.lodz.pl", "Anna", "Kowalska", USER_AUTHORITY, TEACHER_AUTHORITY);
            teacher.setAddress("Weeia pok132");
            User teacher2 = createUser("teacher2", "teacher2", "pnowak@p.lodz.pl", "Piotr", "Nowak", USER_AUTHORITY, TEACHER_AUTHORITY);
            teacher2.setAddress("FTIMS A7 pok37");
            User teacher3 = createUser("teacher3", "teacher3", "mryniec@p.lodz.pl", "Marian", "Ryniec", USER_AUTHORITY, TEACHER_AUTHORITY);
            teacher3.setAddress("MECH A2 pok201");
            User student = createUser("student", "student", "student@edu.p.lodz.pl", "Agata", "Wygnaniec", USER_AUTHORITY, STUDENT_AUTHORITY);
            User student2 = createUser("student2", "student2", "tnowicki@edu.p.lodz.pl", "Tomasz", "Nowicki", USER_AUTHORITY, STUDENT_AUTHORITY);
            User student3 = createUser("student3", "student3", "hkowalik@edu.p.lodz.pl", "Halina", "Kowalik", USER_AUTHORITY, STUDENT_AUTHORITY);

            Arrays.asList(anon, system, admin, user, teacher, teacher2, teacher3, student, student2, student3)
                .forEach(u -> userRepository.save(u));

            Consultation consultation1 = createConsultation(teacher, "Przykladowy opis");
            consultation1.setAddress(teacher.getAddress());
            Consultation consultation2 = createConsultation(teacher2, "Inny przykladowy opis", 200, student, student2);
            consultation2.setAddress(teacher2.getAddress());
            Consultation consultation3 = createConsultation(teacher2, "Jeszcze inny przykladowy opis", 10, student2);
            consultation3.setAddress(teacher2.getAddress());
            Consultation consultation4 = createConsultation(teacher, "Informacja dla studentow", 321, student2, student3);
            consultation4.setAddress(teacher.getAddress());
            Consultation consultation5 = createConsultation(teacher3, "Calkiem inny opis", 241, student, student2, student3);
            consultation5.setAddress(teacher3.getAddress());

            Arrays.asList(consultation1, consultation2, consultation3, consultation4, consultation5)
                .forEach(c -> consultationRepository.save(c));
        };
    }

    private void initAuthorities() {
        Arrays.asList(ANONYMOUS_AUTHORITY, ADMIN_AUTHORITY, USER_AUTHORITY, TEACHER_AUTHORITY, STUDENT_AUTHORITY)
            .forEach(authority ->
                authorityRepository.save(authority)
        );
    }

    private User createUser(String login, String password, String email, String firstName, String lastName,
                            Authority ... authorities) {
        User user = new User();
        user.setAuthorities(new HashSet<>(Arrays.asList(authorities)));
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setActivated(true);
        user.setCreatedDate(ZonedDateTime.now());
        user.setCreatedBy("system");
        return user;
    }

    private User createUser(String login, String password, String email, Authority ... authorities) {
        return createUser(login, password, email, "", "", authorities);
    }

    private Consultation createConsultation(User teacher, String description, long hoursOffset, User ... students) {
        Consultation consultation = new Consultation();
        consultation.setTeacher(teacher);
        consultation.setDescription(description);
        consultation.setCancelled(false);
        consultation.setRegisteredStudents(new HashSet<>(Arrays.asList(students)));
        consultation.setDateTime(ZonedDateTime.now().plusHours(hoursOffset));
        return consultation;
    }

    private Consultation createConsultation(User teacher, String description) {
        return createConsultation(teacher, description, 0, new User[] {});
    }
}
