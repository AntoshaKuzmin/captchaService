package ru.kuzmin.captchaService.service;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuzmin.captchaService.config.CaptchaProperties;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class CaptchaUtilService {
    private final CaptchaProperties properties;

    private final char[] DEFAULT_CHARS = {'a','b','c','d','e','f','g','h','i','j','k','l','m', 'n',
            'o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};

    public Captcha createCaptcha(Integer width, Integer height) {
        return new Captcha.Builder(width, height)
                .addBackground(new GradiatedBackgroundProducer())
                .addText(new DefaultTextProducer(properties.getLength(), DEFAULT_CHARS), new DefaultWordRenderer())
                .addNoise(new CurvedLineNoiseProducer())
                .build();
    }

    public byte[] encodeCaptcha(Captcha captcha) {
        byte[] image = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(captcha.getImage(), "png", bos);
            image = Base64.getEncoder().encode(bos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
