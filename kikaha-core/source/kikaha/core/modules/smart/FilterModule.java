package kikaha.core.modules.smart;

import java.io.IOException;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Typed;
import javax.inject.*;
import io.undertow.Undertow;
import io.undertow.server.*;
import kikaha.core.DeploymentContext;
import kikaha.core.modules.Module;
import lombok.Getter;

/**
 * A {@link Module} that deploys {@link Filter}s.
 */
@Getter
@Singleton
public class FilterModule implements Module {

	final String name = "filter";

	@Inject
	@Typed( Filter.class )
	Collection<Filter> foundFilters;

	@PostConstruct
	public void printFoundFilters(){
		System.err.println( foundFilters );
	}

	@Override
	public void load( Undertow.Builder server, DeploymentContext context ) throws IOException {
		final List<Filter> filterList = new ArrayList<>(foundFilters);
		if ( filterList.isEmpty() )
			return;

		final FilterHttpHandler filterHttpHandler = createTheFilterHttpHandler(context, filterList);
		context.rootHandler( filterHttpHandler );
	}

	FilterHttpHandler createTheFilterHttpHandler( DeploymentContext context, final List<Filter> filterList ) {
		final HttpHandler httpHandler = context.rootHandler();
		filterList.add( new HttpHandlerRunnerFilter(httpHandler) );

		final FilterChainFactory chainFactory = new FilterChainFactory(filterList);
		return new FilterHttpHandler(chainFactory);
	}
}
