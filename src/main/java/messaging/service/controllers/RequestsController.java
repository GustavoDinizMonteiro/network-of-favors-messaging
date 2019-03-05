package messaging.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import messaging.service.model.Request;
import messaging.service.services.RequestsDispatcher;

@RestController
@RequestMapping("requests")
public class RequestsController {
	@Autowired
	private RequestsDispatcher dispatcher;
	
	@PostMapping
	public ResponseEntity<Boolean> receiveOrder(@RequestBody Request request) {
		dispatcher.dispatch(request);
		return ResponseEntity.ok(true);
	}

}
