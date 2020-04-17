package org.sadoke.util.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Handling all those mean Rest Services with static methods.
 * 
 * @author Saied Sadegh
 *
 */
public final class RestUtil {
	private RestUtil() {

	}

	private static final Logger log = LoggerFactory.getLogger(RestUtil.class);

	/**
	 * Takes an url to send a REST GET-request and returns the json as a string.
	 * This is fulfilled with a token.
	 * 
	 * @param authentication
	 *            token
	 * @param url
	 * 
	 * @return json string
	 * @throws IOException
	 */
	public static String callGetRestService(String urlString, String auth)
			throws IOException {
		final HttpUriRequest request = RestUtil.buildGetRequest(urlString);
		request.setHeader("Authorization", auth);

		return RestUtil.resolveRequest(request);
	}
	/**
	 * This function takes an url as a string and makes a simple get request
	 * with it. The response of this request will be returned as a string.
	 * 
	 * @param urlString
	 * 
	 * @return response as string
	 */
	public static String callGetRestService(String urlString) {
		try {
			final HttpUriRequest request = RestUtil.buildGetRequest(urlString);

			return RestUtil.resolveRequest(request);
		} catch (final IOException e) {
			RestUtil.log.error(e.getLocalizedMessage());
		}
		return null;
	}
	/**
	 * Takes an url and RESTParameters and then resolves a get-request. This is
	 * fullfilled with a token.
	 * 
	 * @param authentication
	 *            token
	 * @param url
	 * @param param
	 * 
	 * @return -json String
	 * @throws IOException
	 * @throws URISyntaxException
	 */

	public static String callGetRestService(String urlString, String auth,
			RESTParameter... params) throws IOException, URISyntaxException {
		final URIBuilder builder = RestUtil.buildURIRequest(urlString, params);
		return RestUtil.callGetRestService(builder.build().toString(), auth);

	}
	/**
	 * Takes an url and RESTParameters and then resolves a get-request
	 * 
	 * @param url
	 * @param param
	 * 
	 * @return -json String
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String callGetRestService(String urlString,
			RESTParameter... params) throws IOException, URISyntaxException {
		final URIBuilder builder = RestUtil.buildURIRequest(urlString, params);
		return RestUtil.callGetRestService(builder.build().toString());

	}

	/**
	 * Takes an url for the request, converting the parameter map to
	 * RESTParameters and then resolves the get-request.
	 * 
	 * @param url
	 * @param auth
	 * @param param
	 * @return -json String
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String callGetRestService(String url, String auth,
			Map<String, Object> param) throws IOException, URISyntaxException {
		final RESTParameter[] parameters = RestUtil
				.createRESTParameterFromMap(param);
		return RestUtil.callGetRestService(url, auth, parameters);

	}
	public static String callPostRestServiceWithPlainContent(String urlString,
			String... params) throws IOException {
		final HttpPost request = RestUtil.buildPostRequest(urlString);
		final StringBuilder b = new StringBuilder();
		Arrays.asList(params).stream().forEach(s -> {
			if (b.length() > 0)
				b.append(",");
			b.append(s);
		});
		request.setEntity(new StringEntity(b.toString(),
				ContentType.create("text/plain", "UTF-8")));
		RestUtil.log.info("Request towards {} with body {}", urlString, b);
		return RestUtil.resolveRequest(request);

	}
	/**
	 * Takes an url to send a post-request and returns the json as a string.
	 * 
	 * @param url
	 *            -url of REST request
	 * @return -json string
	 * @throws IOException
	 */
	public static String callPostRestService(String urlString, String auth)
			throws IOException {
		final HttpPost request = RestUtil.buildPostRequest(urlString);
		request.addHeader("Authorization", auth);
		return RestUtil.resolveRequest(request);

	}
	/**
	 * Takes an url and RESTParameters and then resolves a post-request
	 * 
	 * @param url
	 * @param param
	 * @return -json String
	 * @throws IOException
	 * @throws URISyntaxException
	 */

	public static String callPostRestService(String urlString, String auth,
			RESTParameter... params) throws IOException, URISyntaxException {
		final URIBuilder builder = RestUtil.buildURIRequest(urlString, params);
		return RestUtil.callPostRestService(builder.build().toString(), auth);

	}

	/**
	 * Takes an url for the request, converting the parameter map to
	 * RESTParameters and then resolves the post-request.
	 * 
	 * @param url
	 * @param param
	 * @return -json String
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String callPostRestService(String url, String auth,
			Map<String, Object> param) throws IOException, URISyntaxException {
		final RESTParameter[] parameters = RestUtil
				.createRESTParameterFromMap(param);
		return RestUtil.callPostRestService(url, auth, parameters);

	}

	private static HttpPost buildPostRequest(String urlString) {
		return new HttpPost(urlString);
	}

	private static HttpGet buildGetRequest(String urlString) {
		return new HttpGet(urlString);
	}

	private static URIBuilder buildURIRequest(String urlString,
			RESTParameter... params) throws URISyntaxException {
		RestUtil.log.info("REST Request towards {} with {} parameters.",
				urlString, params.length);
		final URIBuilder builder = new URIBuilder(urlString);
		Arrays.asList(params).stream().forEach(param -> {
			URIBuilder b = builder.setParameter(param.getKey(),
					param.getValue().toString());
			while (param.getChild() != null) {
				param = param.getChild();
				b = b.setParameter(param.getKey(), param.getValue().toString());
			}
		});

		return builder;
	}

	private static String resolveRequest(HttpUriRequest request)
			throws IOException {
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpResponse response = client.execute(request);
		return EntityUtils.toString(response.getEntity(),
				StandardCharsets.UTF_8);
	}

	private static RESTParameter[] createRESTParameterFromMap(
			Map<String, Object> param) {
		final RESTParameter[] parameters = new RESTParameter[param.size()];
		int i = 0;
		for (final Entry<String, Object> e : param.entrySet()) {
			parameters[i] = new RESTParameterImpl(e.getKey(), e.getValue());
			i++;
		}
		return parameters;
	}
}
