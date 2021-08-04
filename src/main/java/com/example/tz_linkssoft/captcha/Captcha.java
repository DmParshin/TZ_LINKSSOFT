package com.example.tz_linkssoft.captcha;

public class Captcha {

    private String request_id;
    private String captcha_string;

    public Captcha() {
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getCaptcha_string() {
        return captcha_string;
    }

    public void setCaptcha_string(String captcha_string) {
        this.captcha_string = captcha_string;
    }
}
