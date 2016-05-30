package kikaha.urouting;

import java.io.IOException;
import java.util.*;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import kikaha.urouting.api.ConversionException;
import lombok.RequiredArgsConstructor;

/**
 * Represents an incoming request. Most of time, is just a very tiny layer above
 * Undertow's API providing to developers an easy to use API for their daily routines.
 */
@RequiredArgsConstructor(staticName = "wrap")
public class SimpleExchange {

	final HttpServerExchange exchange;
	final RoutingMethodParameterReader parameterReader;
	final RoutingMethodResponseWriter responseWriter;

	/**
	 * Return the host, and also the port if this request was sent to a non-standard port. In general
	 * this will just be the value of the Host header.
	 * <p>
	 * If this resolves to an IPv6 address it *will*  be enclosed by square brackets. The return
	 * value of this method is suitable for inclusion in a URL.
	 *
	 * @return The host and port part of the destination address
	 */
	public String getHostAndPort(){
		return exchange.getHostAndPort();
	}

	/**
	 * Get the request URI scheme.  Normally this is one of {@code http} or {@code https}.
	 *
	 * @return the request URI scheme
	 */
	public String getRequestScheme() {
		return exchange.getRequestScheme();
	}

	/**
	 * Get the HTTP request method.  Normally this is one of the strings listed in {@link io.undertow.util.Methods}.
	 *
	 * @return the HTTP request method
	 */
	public HttpString getHttpMethod() {
		return exchange.getRequestMethod();
	}

	/**
	 * Get the request relative path.  This is the path which should be evaluated by the current handler.
	 *
	 * @return the request relative path
	 */
	public String getRelativePath() {
		return exchange.getRelativePath();
	}

	/**
	 * Returns a mutable map of query parameters.
	 *
	 * @return The query parameters
	 */
	public Map<String, Deque<String>> getQueryParameters() {
		return exchange.getQueryParameters();
	}

	/**
	 * Get a simple query parameter, named {@code name}, converted to the type {@code type}.
	 *
	 * @param name
	 * @param type
	 * @param <T>
	 * @return
	 * @throws IOException if anything goes wrong during the conversion process
	 */
	public <T> T getQueryParameter(String name, Class<T> type) throws IOException  {
		try {
			return parameterReader.getQueryParam(exchange, name, type);
		} catch ( IllegalAccessException | InstantiationException e) {
			throw new IOException(e);
		}
	}

	/**
	 * Retrieve all path parameters related to this request.
	 *
	 * @return all path parameters.
	 */
	public Map<String, String> getPathParameters() {
		return parameterReader.getPathParams( exchange );
	}

	/**
	 * Get a path parameter from request converted to the {@code <T>} type as
	 * defined by {@code clazz} argument.
	 *
	 * @param name
	 * @param type
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	public <T> T getPathParameter(String name, Class<T> type) throws IOException {
		try {
			return parameterReader.getPathParam( exchange, name, type );
		} catch (ConversionException | InstantiationException | IllegalAccessException e) {
			throw new IOException(e);
		}
	}
}
