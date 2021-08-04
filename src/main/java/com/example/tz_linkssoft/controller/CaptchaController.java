package com.example.tz_linkssoft.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.example.tz_linkssoft.captcha.Captcha;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping
public class CaptchaController {

    @GetMapping("/captcha")
    public ResponseEntity getCaptcha(HttpSession session) {

        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(100, 30);

        String captcha_code = captcha.getCode();
        System.out.println("Captcha: " + captcha_code);

        session.getCreationTime();
        session.setAttribute("captcha_code", captcha_code);

        return ResponseEntity.status(HttpStatus.OK)
                .header("request_id", session.getId())
                .header("captcha_string", captcha_code)
                .body(captcha.getImageBase64());
    }

    @PostMapping("/captcha")
    @ResponseBody
    public ResponseEntity createProduct(@RequestBody Captcha captcha, HttpSession session) {

        if (session.getId().equals(captcha.getRequest_id())
                && session.getAttribute("captcha_code").equals(captcha.getCaptcha_string())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("success");
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("error");
    }
}
