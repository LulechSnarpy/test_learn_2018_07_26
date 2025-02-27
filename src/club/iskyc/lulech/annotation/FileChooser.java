package club.iskyc.lulech.annotation;

import javax.swing.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface FileChooser {
    int type() default JFileChooser.FILES_AND_DIRECTORIES;
    String fileTypes() default "";
}
