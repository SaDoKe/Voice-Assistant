package org.sadoke.util.general;

import org.slf4j.LoggerFactory;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;

/**
 * An util for handling jsons which is important for RESTFUl Services
 * 
 * @author Saied Sadegh
 *
 *
 */
public class JsonUtil {
	/**
	 * Takes a json string and a path of keys. It goes through this path of keys
	 * and returns the last key as a string
	 *
	 * @param json
	 *            - as a string
	 * @param keys
	 *            - a path to the key to be returned
	 * @return the string
	 */
	public static String getTagContent(String json, String... keys) {
		JsonValue value = Json.parse(json);
		for (final String key : keys)
			if (value.isObject())
				value = value.asObject().get(key);
			else if (value.isArray())
				value = value.asArray().get(0).asObject().get(key);
		if (value.isString())
			return value.asString();

		return value.toString();

	}

}
