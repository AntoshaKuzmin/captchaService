package ru.kuzmin.captchaService.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "app.captcha")
@Configuration
public class CaptchaProperties {
    private Integer length;
    private Integer lifetime;
}
