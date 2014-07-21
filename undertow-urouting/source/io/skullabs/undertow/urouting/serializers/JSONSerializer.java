package io.skullabs.undertow.urouting.serializers;

import io.skullabs.undertow.urouting.api.Mimes;
import io.skullabs.undertow.urouting.api.RoutingException;
import io.skullabs.undertow.urouting.api.Serializer;

import java.io.IOException;
import java.io.Writer;

import trip.spi.Name;
import trip.spi.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

@Singleton
@Name( Mimes.JSON )
public class JSONSerializer implements Serializer {
	
	final ObjectMapper mapper = new ObjectMapper();

	@Override
	public <T> void serialize(T object, Writer output) throws RoutingException {
		try {
			mapper.writeValue(output, object);
		} catch (IOException cause) {
			throw new RoutingException(cause);
		}
	}

}
