package org.sadoke.main;

import org.sadoke.expression.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * This reads the list of commands in a seperate thread and works through them.
 * Always removing them from the command list
 * 
 * @author Saied Sadegh
 *
 */
public class ExpressionRunner {

	Logger log = LoggerFactory.getLogger(this.getClass());

	public void run(Class<? extends Expression> key, Object[] args) {
		if (key != null)
			try {
				log.info("starting module '{}'", key.getName());
				key.newInstance().run(args);
			} catch (final Exception e) {
				log.error(e.getMessage());
			}
	}

}
