package org.sadoke.util.annotations;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"sadoke.util.annotations.Express"})
/**
 * Unfinished.
 * 
 * @author Saied Sadegh
 *
 */
public class ExpressProcessor extends AbstractProcessor {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		log.info("Express Processor starts processing");
		for (final Element annotatedElement : roundEnv
				.getElementsAnnotatedWith(Express.class))
			if (annotatedElement.getKind() == ElementKind.CLASS)
				System.err.println(annotatedElement.getClass());

		return true;
	}

}
