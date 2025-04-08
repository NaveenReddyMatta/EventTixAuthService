package com.eventtix.authservice.feignclient;

import com.eventtix.authservice.model.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@FeignClient(name = "EventTixEventService", url = "http://localhost:8083")
public interface EventClient {
    @GetMapping("/events/eventsList")
    List<Event> getEvents();
}
