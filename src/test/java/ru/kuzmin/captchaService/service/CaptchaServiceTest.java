package ru.kuzmin.captchaService.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kuzmin.captchaService.config.CaptchaProperties;
import ru.kuzmin.captchaService.dto.CaptchaDTO;
import ru.kuzmin.captchaService.dto.CaptchaValidateRequest;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CaptchaServiceTest {

    @Autowired
    CaptchaService captchaService;

    @MockBean
    CaptchaUtilService captchaUtilService;

    @MockBean
    CaptchaProperties properties;

    @Test
    void validateCaptchaReturnsSuccess() throws NoSuchFieldException, IllegalAccessException {
        Map<Long, CaptchaDTO> captchas = new HashMap<>();
        captchas.put(1L, new CaptchaDTO()
                .setValidBefore(OffsetDateTime.now().plusMinutes(3))
                .setCaptchaAnswer("12345"));

        Field field = captchaService.getClass().getDeclaredField("captchas");
        field.setAccessible(true);

        field.set(captchaService, captchas);
        CaptchaValidateRequest captchaValidateRequest = new CaptchaValidateRequest();
        captchaValidateRequest.setCaptchaAnswer("12345");
        captchaValidateRequest.setRequestId(1L);
        String answer = captchaService.validateCaptcha(captchaValidateRequest);
        assertThat(answer).isEqualTo("success");
    }

    @Test
    void validateExpiredCaptchaReturnsError() throws NoSuchFieldException, IllegalAccessException {
        Map<Long, CaptchaDTO> captchas = new HashMap<>();
        captchas.put(1L, new CaptchaDTO()
                .setValidBefore(OffsetDateTime.now().minusMinutes(3))
                .setCaptchaAnswer("12345"));

        Field field = captchaService.getClass().getDeclaredField("captchas");
        field.setAccessible(true);

        field.set(captchaService, captchas);
        CaptchaValidateRequest captchaValidateRequest = new CaptchaValidateRequest();
        captchaValidateRequest.setCaptchaAnswer("12345");
        captchaValidateRequest.setRequestId(1L);
        String answer = captchaService.validateCaptcha(captchaValidateRequest);
        assertThat(answer).isEqualTo("error");
    }

    @Test
    void validateCaptchaReturnsError() throws NoSuchFieldException, IllegalAccessException {

        Map<Long, CaptchaDTO> captchas = new HashMap<>();
        captchas.put(1L, new CaptchaDTO()
                .setValidBefore(OffsetDateTime.now().plusMinutes(3))
                .setCaptchaAnswer("12345"));

        Field field = captchaService.getClass().getDeclaredField("captchas");
        field.setAccessible(true);

        field.set(captchaService, captchas);
        CaptchaValidateRequest captchaValidateRequest = new CaptchaValidateRequest();
        captchaValidateRequest.setCaptchaAnswer("12");
        captchaValidateRequest.setRequestId(1L);
        String answer = captchaService.validateCaptcha(captchaValidateRequest);
        assertThat(answer).isEqualTo("error");
    }
}