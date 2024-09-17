package com.blog_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = "userId는 필수 입력 값입니다.")
    @Size(min = 3, max = 50, message = "userId는 3자 이상 50자 이하로 입력해주세요.")
    private String userId;

    @NotBlank(message = "username은 필수 입력 값입니다.")
    @Size(min = 1, max = 50, message = "username은 1자 이상 50자 이하로 입력해주세요.")
    private String username;

    @NotBlank(message = "email은 필수 입력 값입니다.")
    @Email(message = "email 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "password는 필수 입력 값입니다.")
    @Size(min = 8, max = 255, message = "password는 8자 이상 255자 이하로 입력해주세요.")
    private String password;
}
