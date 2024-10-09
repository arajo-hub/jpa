package com.judy.jpa.chapter2.controller;

import com.judy.jpa.chapter2.domain.Member;
import com.judy.jpa.chapter2.service.Chapter2Service;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Chapter2Controller {

    private final Chapter2Service chapter2Service;

    @PostMapping("/logic")
    public void logic() {
        this.chapter2Service.logic();
    }

}
