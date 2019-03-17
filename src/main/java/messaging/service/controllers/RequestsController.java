package messaging.service.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import messaging.service.model.Request;
import messaging.service.services.RequestsDispatcher;

@RestController
@RequestMapping("requests")
public class RequestsController {
	@Autowired
	private RequestsDispatcher service;
	
	@PostMapping
	public ResponseEntity<Boolean> dispatchOrder(@RequestBody Request request) {
		service.dispatch(request);
		return ResponseEntity.ok(true);
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> getOrder(
			@RequestParam("id") Integer id,
			@RequestHeader("requester") String requester,
			@RequestHeader("token") String token,
			@RequestHeader("provider") String provider) {
		return ResponseEntity.ok(service.get(id, requester, token, provider));
	}

}
