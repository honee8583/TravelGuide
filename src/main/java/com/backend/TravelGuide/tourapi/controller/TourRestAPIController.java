package com.backend.TravelGuide.tourapi.controller;

import com.backend.TravelGuide.tourapi.DTO.TourApiDTO;
import com.backend.TravelGuide.tourapi.service.serviceImpl.TourApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "TourAPI 컨트롤러")
@Slf4j
@RestController
@RequiredArgsConstructor
public class TourRestAPIController {
    private final TourApiService tourAPIService;

    @Operation(summary = "키워드 검색", description = "키워드와 페이지 번호를 전달하면 여행지 목록을 JSON으로 반환합니다.")
    @Parameters({
            @Parameter(name = "keyword", description = "키워드", example = "타워"),
            @Parameter(name = "pageNo", description = "페이지 번호", example = "1")})
    @GetMapping("/api/tourapi/keywordSearch")
    public ResponseEntity<List<TourApiDTO>> tourListApiByKeyword(
            @RequestParam String keyword,
            @RequestParam String pageNo
    ) {
        log.info("keyword: " + keyword +", pageNo: " + pageNo);

        List<TourApiDTO> tourApiDTOList = tourAPIService.keywordSearchApi(keyword, pageNo);

        return ResponseEntity.ok(tourApiDTOList);
    }
}
