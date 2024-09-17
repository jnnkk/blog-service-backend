package com.blog_service.dto;

import com.blog_service.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = "username은 필수 입력 값입니다.")
    @Size(min = 3, max = 50, message = "username은 3자 이상 50자 이하로 입력해주세요.")
    private String username;

    @NotBlank(message = "email은 필수 입력 값입니다.")
    @Email(message = "email 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "password는 필수 입력 값입니다.")
    @Size(min = 8, max = 255, message = "password는 8자 이상 255자 이하로 입력해주세요.")
    private String password;
}
