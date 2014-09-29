package kikaha.core.url;

import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaceHolderForAnyStringUntilEndMatcher implements Matcher {

	final String placeholder;

	@Override
	public boolean matches( final StringCursor string , final Map<String, String> foundParameters  ) {
		string.mark();
		string.end();
		foundParameters.put( placeholder, string.substringFromLastMark() );
		return true;
	}
}
