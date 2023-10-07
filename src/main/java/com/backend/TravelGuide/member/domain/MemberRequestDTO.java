package com.backend.TravelGuide.member.domain;

import lombok.*;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class MemberRequestDTO {

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoLoginDTO {
        private String email;
        private String nickname;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberLoginDTO {
        @Email(message = "아이디는 이메일 형식입니다")
        private String email;

        @Size(min = 8, message = "비밀번호는 최소 8자입니다")
        private String password;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberJoinDTO {
        @Email(message = "아이디는 이메일 형식입니다")
        private String email;

        @Size(min = 8, message = "비밀번호는 최소 8자입니다")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영문과 숫자만 가능합니다.")
        private String password;

        @NotBlank(message = "이름은 필수 입력란입니다")
        private String name;

        @NotBlank(message = "닉네임은 필수 입력란입니다")
        private String nickname;

//        @NotEmpty(message = "답을 입력해주셔야 합니다")
//        @Size(min = 3, message = "3개의 답을 모두 작성해주셔야 합니다")
//        private List<String> answers = new ArrayList<>();
        private Long questionId;
        private String answer;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateInfoDTO {
        @Email(message = "아이디는 이메일 형식입니다")
        private String email;

        @NotBlank(message = "이름은 필수 입력란입니다")
        private String name;

        @NotBlank(message = "이름은 필수 입력란입니다")
        private String nickname;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResetPwdDTO {
        @Email(message = "아이디는 이메일 형식입니다")
        private String email;

        @Size(min = 8, message = "비밀번호는 최소 8자입니다")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영문과 숫자만 가능합니다.")
        private String oldPassword;

        @Size(min = 8, message = "비밀번호는 최소 8자입니다")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영문과 숫자만 가능합니다.")
        private String newPassword;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class CheckAnswerDTO {
        @Email(message = "아이디는 이메일 형식입니다")
        private String email;

        @NotBlank(message = "이름은 필수 입력란입니다")
        private String name;

        private int questionId;
        private String answer;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class NewPasswordDTO {
        private String email;
        private String newPassword;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WithdrawDTO {
        @Email(message = "아이디는 이메일 형식입니다")
        private String email;

        @Size(min = 8, message = "비밀번호는 최소 8자입니다")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영문과 숫자만 가능합니다.")
        private String password;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckDuplicationDTO {
        @Email(message = "아이디는 이메일 형식입니다")
        private String email;
        private String nickname;
    }
}
