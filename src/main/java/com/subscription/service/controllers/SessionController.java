package com.subscription.service.controllers;

import com.subscription.service.models.dtos.SessionRequestDto;
import com.subscription.service.models.entity.Session;
import com.subscription.service.services.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Session> createSession(@RequestBody SessionRequestDto requestDto) {
        Session session = sessionService.createSession(requestDto);
        //TODO:: BİR ÜYE AYNI PAKETTEN ART ARDA ALINCA NASIL BİR BUSINESS RULE İŞLEMELİ?
        return ResponseEntity.ok(session);
    }

}
