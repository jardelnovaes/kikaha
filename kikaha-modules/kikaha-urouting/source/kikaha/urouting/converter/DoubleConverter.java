package kikaha.urouting.converter;

import kikaha.urouting.api.AbstractConverter;
import kikaha.urouting.api.ConversionException;

import javax.enterprise.inject.Typed;
import javax.inject.Singleton;

@Singleton
@Typed( AbstractConverter.class )
public class DoubleConverter extends AbstractConverter<Double> {

	@Override
	public Double convert( String value ) throws ConversionException {
		return Double.valueOf( value );
	}

}
