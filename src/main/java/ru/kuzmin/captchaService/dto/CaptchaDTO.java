package ru.kuzmin.captchaService.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
@Data
@Accessors(chain = true)
public class CaptchaDTO {
   private OffsetDateTime validBefore;
   private String captchaAnswer;
}
