package ru.kuzmin.captchaService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kuzmin.captchaService.service.CaptchaService;
import ru.kuzmin.captchaService.dto.CaptchaValidateRequest;

@RestController
@RequiredArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    @GetMapping("/get-captcha")
    public ResponseEntity<byte[]> getCaptcha() {
        return captchaService.getCaptcha();
    }

    @PostMapping("/post-captcha")
    public String validateCaptcha(@RequestBody CaptchaValidateRequest request) {
        return captchaService.validateCaptcha(request);
    }
}
