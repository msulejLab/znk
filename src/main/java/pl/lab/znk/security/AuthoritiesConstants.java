package pl.lab.znk.security;

import pl.lab.znk.domain.Authority;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String TEACHER = "ROLE_TEACHER";

    public static final String STUDENT = "ROLE_STUDENT";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final Authority ADMIN_AUTHORITY = new Authority(ADMIN);

    public static final Authority USER_AUTHORITY = new Authority(USER);

    public static final Authority TEACHER_AUTHORITY = new Authority(TEACHER);

    public static final Authority STUDENT_AUTHORITY = new Authority(STUDENT);

    public static final Authority ANONYMOUS_AUTHORITY = new Authority(ANONYMOUS);

    private AuthoritiesConstants() { }
}
