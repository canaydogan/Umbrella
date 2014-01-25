package net.canaydogan.umbrella;

import org.junit.Test;
import static org.junit.Assert.*;

public class HttpResponseTest {
	
	@Test
	public void testValueOfForStatusWithValidValues() {
		for (HttpResponse.Status status : HttpResponse.Status.values()) {
			assertEquals(status, HttpResponse.Status.valueOf(status.getCode()));
		}		
	}
	
	@Test
	public void testValueOfForStatusWithInvalidValue() {
		assertNull(HttpResponse.Status.valueOf(2929));
	}

}
