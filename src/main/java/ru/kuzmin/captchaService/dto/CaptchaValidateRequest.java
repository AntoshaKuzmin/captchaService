package ru.kuzmin.captchaService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CaptchaValidateRequest {
    @JsonProperty(value = "request_id")
    private Long requestId;

    @JsonProperty(value = "captcha_string")
    private String captchaAnswer;
}
