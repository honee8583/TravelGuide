package com.backend.TravelGuide.review.domain;

import com.backend.TravelGuide.member.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    private String email;
    private String content;

    public static Reply dtoToEntity(ReplyRequestDTO.ReplyWriteDTO writeDTO, Review review) {
        return Reply.builder()
                .review(review)
                .email(writeDTO.getEmail())
                .content(writeDTO.getContent())
                .build();
    }

    public void updateReply(ReplyRequestDTO.ReplyUpdateDTO updateDTO) {
        this.content = updateDTO.getContent();
    }
}
