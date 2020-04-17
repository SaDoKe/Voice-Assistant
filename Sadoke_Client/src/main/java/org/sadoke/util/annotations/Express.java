/**
 * 
 */
package org.sadoke.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
/**
 * @author Saied Sadegh
 *
 */
public @interface Express {

	@SuppressWarnings("rawtypes")
	Class type();
	String[] express();
	int arugmentSize() default 0;
}
