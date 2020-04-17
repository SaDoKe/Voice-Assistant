package org.sadoke.localized.kernel.util.general;

import org.sadoke.localized.kernel.Datasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Accesses the database and is allowed to change the flow of the main thread.
 * 
 * @author Saied Sadegh
 *
 */
public class DaoUtil {
	private static final Logger log = LoggerFactory.getLogger(DaoUtil.class);

	/**
	 * Changes the name to react on and simply logs an error if the parameter
	 * was null.
	 * 
	 * @param name
	 */
	public static void changeName(String name) {
		if (name == null)
			DaoUtil.log.error("New name was null");
		else
			Datasource.name = name;
	}
}
