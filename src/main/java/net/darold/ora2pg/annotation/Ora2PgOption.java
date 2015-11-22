package net.darold.ora2pg.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ora2Pg command line options
 * 
 * @author yang.dongdong
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Ora2PgOption {
    /** short representation of the option */
    public String opt() default "";

    /** the long name of the Option */
    public String longOpt();

    /** specifies whether the Option takes an argument or not */
    public boolean hasArg();

    /** Sets whether the Option can have an optional argument. */
    public boolean optionalArg() default false;

    /** the display name for the argument value */
    public String argName() default "";

    /** the description of the option */
    public String desc() default "";

    /** the type of the Option */
    public Class<?> type() default Object.class;

    /** the default value */
    public String defaultValue() default "";
}
