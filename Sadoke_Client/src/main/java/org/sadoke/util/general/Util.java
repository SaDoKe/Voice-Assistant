package org.sadoke.util.general;
/**
 * General tools to program faster.
 * @author Saied Sadegh
 *
 */
public class Util {

	/**
	 * Cast object into a class. If this would result in a ClassCastException it instead returns null.
	 * 
	 * @param obj to cast
	 * @param clazz to cast into
	 * @return casted object or null.
	 */
	public static <T> T trycast(Object obj, Class<T> clazz) {
		try {
			return clazz.cast(obj);
		}catch(ClassCastException e)
		{
			return null;
		}
		
		
	}
	
}
