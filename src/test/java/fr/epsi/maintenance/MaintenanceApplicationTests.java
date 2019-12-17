package fr.epsi.maintenance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MaintenanceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testHelloWorld(){
		String hello = "Hello world !";
		Assertions.assertEquals(hello,"Hello world !");
	}
}
