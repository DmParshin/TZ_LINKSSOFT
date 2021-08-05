package com.example.tz_linkssoft.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.example.tz_linkssoft.captcha.Captcha;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class CaptchaController {

    @RequestMapping(value = "/get-captcha", method = RequestMethod.GET, produces = "image/png")
    public ResponseEntity<byte[]> getImageAsResponseEntity(HttpSession session) {

        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(250, 100);
        byte[] media = captcha.getImageBytes();
        session.setAttribute("captcha_code", captcha.getCode());
        HttpHeaders headers = new HttpHeaders();
        headers.add("request_id", session.getId());
        headers.add("captcha_string", captcha.getCode());
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/post-captcha", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createProduct(@RequestBody Captcha captcha, HttpSession session) {

        if (session.getId().equals(captcha.getRequest_id())
                && session.getAttribute("captcha_code").equals(captcha.getCaptcha_string())
                && System.currentTimeMillis() - session.getCreationTime() < 300000) {
            session.invalidate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("success");
        } else {
            session.invalidate();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("error");
        }
    }
}
