package com.gandhre.demo.platform.event;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gandhre.demo.platform.event.EventPublisher;

@SpringBootTest
public class EventRestControllerTest {

	@MockBean
	private EventPublisher publisher;
	
	@Test
	void loadContext() {
		
	}

}

