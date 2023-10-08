package com.backend.TravelGuide.review.controller;

import com.backend.TravelGuide.review.domain.ReplyDTO;
import com.backend.TravelGuide.review.domain.ReplyRequestDTO;
import com.backend.TravelGuide.review.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Tag(name = "댓글 컨트롤러")
@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @Operation(summary = "댓글 작성")
    @PostMapping("/write")
    public ResponseEntity<Long> writeReply(Principal principal, @RequestBody ReplyRequestDTO.ReplyWriteDTO writeDTO) {
        writeDTO.setEmail(principal.getName());

        log.info(writeDTO.toString());

        Long id = replyService.writeReply(writeDTO);

        return ResponseEntity.ok(id);
    }

    @Operation(summary = "모든 댓글 조회")
    @GetMapping("/list/{reviewId}")
    public ResponseEntity<List<ReplyDTO>> getReplyList(@PathVariable Long reviewId) {
        log.info(">> " + reviewId + "번 리뷰 게시글의 댓글목록");

        List<ReplyDTO> list = replyService.getReplyList(reviewId);

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/update")
    public ResponseEntity<Long> updateReply(Principal principal, @RequestBody ReplyRequestDTO.ReplyUpdateDTO updateDTO) {
        updateDTO.setEmail(principal.getName());

        log.info(updateDTO.toString());

        Long id = replyService.updateReply(updateDTO);

        return ResponseEntity.ok(id);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<Long> deleteReply(Principal principal, @RequestParam Long id) {
        log.info(">> " + id + "번 댓글을 삭제합니다.");

        Long deletedReplyId = replyService.deleteReply(id, principal.getName());

        return ResponseEntity.ok(deletedReplyId);
    }
}
