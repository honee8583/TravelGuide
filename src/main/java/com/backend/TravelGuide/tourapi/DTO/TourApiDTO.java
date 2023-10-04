package com.backend.TravelGuide.tourapi.DTO;

import lombok.*;

//한국 관광정보 API에서 Response한 XML 결과를 담는 DTO
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TourApiDTO {
    private String addr1;
    private String addr2;
    private String areacode;
    private String contentId;
    private String contentTypeId;
    private String mapx;
    private String mapy;
    private String title;
    private String image;
}
