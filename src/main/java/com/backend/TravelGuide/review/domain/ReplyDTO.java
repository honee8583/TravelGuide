package com.backend.TravelGuide.review.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
    private Long id;
    private String email;   // TODO nickname
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ReplyDTO entityToDto(Reply reply) {
        return ReplyDTO.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .email(reply.getEmail())
                .createdAt(reply.getCreatedAt())
                .modifiedAt(reply.getModifiedAt())
                .build();
    }
}
