package com.backend.TravelGuide.member.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    private String email;
    private String password;
    private String name;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean fromSocial;
    private Long questionId;
    private String answer;

    public void updateInfo(MemberRequestDTO.UpdateInfoDTO updateInfoDTO) {
        if (updateInfoDTO.getNickname() != null && !updateInfoDTO.getNickname().trim().equals("")) {
            this.nickname = updateInfoDTO.getNickname();
        }
    }

    public void resetPwd(String newPassword) {
        this.password = newPassword;
    }
}
