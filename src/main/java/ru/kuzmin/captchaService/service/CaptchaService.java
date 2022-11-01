package ru.kuzmin.captchaService.service;

import cn.apiclub.captcha.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.kuzmin.captchaService.config.CaptchaProperties;
import ru.kuzmin.captchaService.dto.CaptchaDTO;
import ru.kuzmin.captchaService.dto.CaptchaValidateRequest;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CaptchaService {
    private final CaptchaProperties properties;
    private final CaptchaUtilService captchaUtilService;
    private final Map<Long, CaptchaDTO> captchas;
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    public ResponseEntity<byte[]> getCaptcha() {
        Long requestId = getRequestId();
        Captcha captcha = captchaUtilService.createCaptcha(100, 300);
        String captchaString = captcha.getAnswer();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("request_id", String.valueOf(requestId));
        responseHeaders.set("captcha_string", captchaString);
        responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        captchas.put(requestId, new CaptchaDTO()
                .setValidBefore(OffsetDateTime.now().plusSeconds(properties.getLifetime()))
                .setCaptchaAnswer(captcha.getAnswer()));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(captchaUtilService.encodeCaptcha(captcha));
    }

    public String validateCaptcha(CaptchaValidateRequest request) {
        CaptchaDTO captcha = captchas.get(request.getRequestId());
        if (Objects.isNull(captcha)
                || captcha.getValidBefore().isBefore(OffsetDateTime.now())
                || !captcha.getCaptchaAnswer().equals(request.getCaptchaAnswer())) {
            return ERROR;
        }
        captchas.remove(request.getRequestId());
        return SUCCESS;
    }

    private Long getRequestId() {
       return (long) (Math.random() * 100000000);
    }
}
