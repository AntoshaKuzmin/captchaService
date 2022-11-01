package ru.kuzmin.captchaService.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CaptchaController.class)
class CaptchaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaptchaController captchaController;

    @Test
    public void assertContextLoad() {
        Assertions.assertNotNull(captchaController);
    }

    @Test
    public void getCaptcha() throws Exception {
        mockMvc.perform(get("/get-captcha"))
                .andExpect(status().isOk());
    }

    @Test
    public void validateCaptchaBadRequest() throws Exception {
        mockMvc.perform(post("/post-captcha")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void validateCaptchaOk() throws Exception {
        JSONObject request = new JSONObject();
        request.accumulate("request_id", 1L);
        request.accumulate("captcha_string", "12345");
        mockMvc.perform(
                post("/post-captcha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(request))
        )
                .andExpect(status().isOk());
    }
}
