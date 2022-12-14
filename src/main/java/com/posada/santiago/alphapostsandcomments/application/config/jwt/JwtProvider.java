package com.posada.santiago.alphapostsandcomments.application.config.jwt;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtProvider {

    private String secretKey="Do-you-think-that-is-air-you-are-breathing-now?";

    //In milliseconds
    private long validTime= 3600000; //1h*
}