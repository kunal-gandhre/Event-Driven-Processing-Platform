package com.gandhre.demo.platform.event;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.gandhre.demo.platform.event.EventController;
import com.gandhre.demo.platform.event.EventService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {
  @Autowired MockMvc mvc;
  @MockBean EventService service;
  @Test void acceptsValidEvent() throws Exception {
    mvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON).content("{\"eventType\":\"ORDER_CREATED\",\"aggregateId\":\"o-1\",\"payload\":\"{}\"}"))
      .andExpect(status().isAccepted());
    verify(service).create(any());
  }
}
