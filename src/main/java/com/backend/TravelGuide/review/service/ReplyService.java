package com.backend.TravelGuide.review.service;

import com.backend.TravelGuide.review.domain.ReplyDTO;
import com.backend.TravelGuide.review.domain.ReplyRequestDTO;

import java.util.List;

public interface ReplyService {
    // 댓글 작성
    Long writeReply(ReplyRequestDTO.ReplyWriteDTO writeDTO);
    // 댓글 수정
    Long updateReply(ReplyRequestDTO.ReplyUpdateDTO updateDTO);
    // 댓글 삭제
    Long deleteReply(Long id, String email);
    // 댓글 리스트 조회
    List<ReplyDTO> getReplyList(Long reviewId);
}
