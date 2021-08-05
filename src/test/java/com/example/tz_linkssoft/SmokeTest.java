package com.example.tz_linkssoft;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.tz_linkssoft.controller.CaptchaController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//Test controller

@SpringBootTest
public class SmokeTest {

    @Autowired
    private CaptchaController controller;

    @Test
    public void controllerLoad() throws Exception {
        assertThat(controller).isNotNull();
    }
}
