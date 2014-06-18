package io.skullabs.undertow.urouting.converter;



public class ShortConverter extends AbstractConverter<Short> {

	@Override
	public Short convert(String value) throws ConversionException {
		return Short.valueOf( value );
	}

}