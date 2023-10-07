package com.backend.TravelGuide.member.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "answers")
public class Member extends BaseEntity {
    @Id
    private String email;

    private String password;

    private String name;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean fromSocial;

    // 비밀번호 초기화 질문에 대한 대답
//    @ElementCollection(fetch = FetchType.LAZY)
//    Map<Integer, String> answers = new HashMap<>();
    private Long questionId;
    private String answer;

    public void updateInfo(MemberRequestDTO.UpdateInfoDTO updateInfoDTO) {
        this.nickname = updateInfoDTO.getNickname();
    }

    public void resetPwd(String newPassword) {
        this.password = newPassword;
    }
}
