package com.gandhre.demo.platform.event;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class EventRestControllerTest {

	@MockBean
	private EventPublisher publisher;

	// @Test
	void loadContext() {

	}

}
