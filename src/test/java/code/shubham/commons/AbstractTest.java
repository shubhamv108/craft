package code.shubham.commons;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AbstractTest {

	protected static PrintStream sysOut;

	protected static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@BeforeAll
	protected static void setupStreams() {
		sysOut = System.out;
		System.setOut(new PrintStream(outContent));
	}

	@BeforeEach
	protected void setUp() {
		outContent.reset();
	}

}
