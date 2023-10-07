package com.backend.TravelGuide.review.service.impl;

import com.backend.TravelGuide.member.error.exception.UserNotMatchException;
import com.backend.TravelGuide.review.domain.Reply;
import com.backend.TravelGuide.review.domain.ReplyDTO;
import com.backend.TravelGuide.review.domain.ReplyRequestDTO;
import com.backend.TravelGuide.review.domain.Review;
import com.backend.TravelGuide.review.error.ReplyNotExistsException;
import com.backend.TravelGuide.review.repository.ReplyRepository;
import com.backend.TravelGuide.review.repository.ReviewRepository;
import com.backend.TravelGuide.review.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;

    @Override
    @Transactional
    public Long writeReply(ReplyRequestDTO.ReplyWriteDTO writeDTO) {
        Review review = reviewRepository.findById(writeDTO.getReviewId())
                .orElseThrow(ReplyNotExistsException::new);

        Reply reply = Reply.dtoToEntity(writeDTO, review);
        Reply savedReply = replyRepository.save(reply);

        return savedReply.getId();
    }

    @Override
    @Transactional
    public Long updateReply(ReplyRequestDTO.ReplyUpdateDTO updateDTO) {
        Reply reply = replyRepository.findById(updateDTO.getId())
                .orElseThrow(ReplyNotExistsException::new);

        if (!reply.getEmail().equals(updateDTO.getEmail())) {
            throw new UserNotMatchException();
        }

        Review review = reviewRepository.findById(updateDTO.getReviewId())
                        .orElseThrow(ReplyNotExistsException::new);

        reply.updateReply(updateDTO);
        replyRepository.save(reply);

        return reply.getId();
    }

    @Override
    @Transactional
    public Long deleteReply(Long id, String email) {
        Reply reply = replyRepository.findById(id)
                .orElseThrow(ReplyNotExistsException::new);

        if (!reply.getEmail().equals(email)) {
            throw new UserNotMatchException();
        }

        replyRepository.delete(reply);

        return reply.getId();
    }

    @Override
    @Transactional
    public List<ReplyDTO> getReplyList(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReplyNotExistsException::new);

        List<Reply> replyList = replyRepository.findAllByReview(review);

        return replyList.stream().map(ReplyDTO::entityToDto).collect(Collectors.toList());
    }
}
