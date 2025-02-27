package com.prk.commandline;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Repeatable(Commands.class)
public @interface Command {
    String value();

    String description() default "";

    int order() default 0;
}
