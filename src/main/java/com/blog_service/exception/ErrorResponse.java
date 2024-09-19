package com.blog_service.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse { // 에러응답객체 (아직 사용 X 영원히 관갈수도)
    private HttpStatus status;
    private String message;
}
