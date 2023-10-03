package com.backend.TravelGuide.tourapi.controller;

import com.backend.TravelGuide.tourapi.DTO.TourAPIDTO;
import com.backend.TravelGuide.tourapi.service.TourAPIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final TourAPIService tourAPIService;

    @Value("${api.key}")
    String apiKey;

    @Operation(summary = "키워드 검색", description = "키워드와 페이지 번호를 전달하면 여행지 목록을 JSON으로 반환합니다.")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK!", content = @Content(schema = @Schema(implementation = TourAPIDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @Parameters(
            {
            @Parameter(name = "keyword", description = "키워드", example = "타워"),
            @Parameter(name = "pageNo", description = "페이지 번호", example = "1")
    }
    )
    @GetMapping("/api/tourapi/keywordSearch")
    public ResponseEntity<List<TourAPIDTO>> tourListApiByKeyword(
            @RequestParam String keyword,
            @RequestParam String pageNo
    ) {

        List<TourAPIDTO> tourApiDTOs = tourAPIService.keywordSearchApi(apiKey, keyword, pageNo);

        return ResponseEntity.ok(tourApiDTOs);
    }

    @Operation(summary = "지역 기반 검색", description = "선택한 지역의 여행지 목록을 가져옵니다.")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "OK!", content = @Content(schema = @Schema(implementation = TourAPIDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "500", description = "Server Error")
            }
    )
    @Parameters(
            {
                    @Parameter(name = "areaCode", description = "지역번호 : 1, 2, 31", example = "타워"),
                    @Parameter(name = "pageNo", description = "페이지 번호", example = "1")
            }
    )
    @GetMapping("/api/tourapi/areaBasedSearch")
    public ResponseEntity<List<TourAPIDTO>> tourListApiByArea(
            @RequestParam String areaCode,
            @RequestParam String pageNo
    ) {

        List<TourAPIDTO> tourAPIDTOs = tourAPIService.areaBasedSearchApi(apiKey, areaCode, pageNo);

        return ResponseEntity.ok(tourAPIDTOs);
    }

//    @ExceptionHandler({MissingServletRequestParameterException.class, HttpServerErrorException.class})
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public String handleMissingServletRequestParameterException() {
//        log.info("예외 발생!\nMissingServletRequestParameterException");
//        return "Bad Request";
//    }

//    @ExceptionHandler(JSONException.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleJsonException() {
//        log.info("예외 발생!\nJSONException");
//        return "Server Error";
//    }
}
