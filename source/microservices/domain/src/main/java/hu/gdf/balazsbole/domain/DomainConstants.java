package hu.gdf.balazsbole.domain;

import lombok.experimental.UtilityClass;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.TimeZone;

@UtilityClass
public final class DomainConstants {

    public static final TimeZone EUROPE_BERLIN_TIME_ZONE = TimeZone.getTimeZone("Europe/Berlin");
    public static final Charset UTF8 = StandardCharsets.UTF_8;
    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;


    @UtilityClass
    public static final class HttpStatus {

        //    /**
//     * @see org.springframework.http.HttpStatus#OK
//     */
        public static final int OK = 200;
        //    /**
//     * @see org.springframework.http.HttpStatus#CREATED
//     */
        public static final int CREATED = 201;
        //    /**
//     * @see org.springframework.http.HttpStatus#NO_CONTENT
//     */
        public static final int NO_CONTENT = 204;
        //    /**
//     * @see org.springframework.http.HttpStatus#CONFLICT
//     */
        public static final int CONFLICT = 409;
        //    /**
//     * @see org.springframework.http.HttpStatus#FORBIDDEN
//     */
        public static final int FORBIDDEN = 403;

        public static final int NOT_FOUND = 404;

    }
}
