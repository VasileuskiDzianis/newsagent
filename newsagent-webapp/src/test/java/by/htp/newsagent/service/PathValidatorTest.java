package by.htp.newsagent.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class PathValidatorTest {
	private String pathVariable;
	private boolean expected;
	
	@Parameters
	public static Collection<Object[]> pathVariables() {
		
		return Arrays.asList(new Object[][] {
			{"1", true},
			{"02", true},
			{"024", true},
			{"", false},
			{" ", false},
			{"a", false},
			{"1a", false},
			{null, false}
		});
	}

	public PathValidatorTest(String pathVariable, boolean expected) {
		this.pathVariable = pathVariable;
		this.expected = expected;
	}
	
	@Test
	public void isPathVariableValidTest() {
		
		assertEquals(expected, PathValidator.isPathVariableValid(pathVariable));
	}
}
