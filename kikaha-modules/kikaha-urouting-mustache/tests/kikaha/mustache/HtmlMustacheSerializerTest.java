package kikaha.mustache;

import com.github.mustachejava.MustacheNotFoundException;
import kikaha.core.NotFoundHandler;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HtmlMustacheSerializerTest {

	@Mock
	MustacheSerializerFactory factory;

	@Mock
	NotFoundHandler notFoundHandler;

	@Mock
	MustacheSerializer serializer;

	@Spy
	@InjectMocks
	HtmlMustacheSerializer htmlSerializer;

	@Test
	@SneakyThrows
	public void ensureThatHandleNotFoundException() {
		doReturn( serializer ).when( factory ).serializer();
		doThrow(MustacheNotFoundException.class).when(serializer).serialize( any() );
		htmlSerializer.serialize( new MustacheTemplate().templateName("any.mustache"), null, );
		verify( notFoundHandler ).handleRequest( any() );
	}
}
