package ru.kuzmin.captchaService.service;

import cn.apiclub.captcha.Captcha;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kuzmin.captchaService.config.CaptchaProperties;

@SpringBootTest
class CaptchaUtilServiceTest {
    @Autowired
    CaptchaUtilService captchaUtilService;

    @MockBean
    CaptchaProperties captchaProperties;

    @Test
    public void captchaNotNull() {
        Assert.assertNotNull(captchaUtilService.createCaptcha(100, 200));
    }

    @Test
    public void captchaLengthEqualsPropertiesLength() {
        Captcha captcha = captchaUtilService.createCaptcha(100, 200);
        int captchaLength = captcha.getAnswer().length();
        int propertiesLength = captchaProperties.getLength();
        Assert.assertEquals(propertiesLength, captchaLength);
    }
}