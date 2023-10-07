package com.backend.TravelGuide.tourapi.serviceImpl;

import com.backend.TravelGuide.tourapi.DTO.TourApiDTO;
import com.backend.TravelGuide.tourapi.service.serviceImpl.TourApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class TourApiServiceTest {

    @Autowired
    TourApiService tourAPIService = new TourApiService();

    String key = "gIY262VtYdeHjkVj0LjSSFlkA56X/H2B/WviOklAVEu6MHcP2YY1MO/sj4K30CfAmMCh/xeo7DCl8iyIQj3D6g==";

    @Test
    void keywordSearchApi() {
        List<TourApiDTO> tourAPIDTO = tourAPIService.keywordSearchApi(key, "서울", "1");

        tourAPIDTO.stream().forEach(a -> {System.out.println(a.toString());});

    }

    @Test
    void areaBasedSearchApi() {

        List<TourApiDTO> tourAPIDTO = tourAPIService.areaBasedSearchApi(key, "1", "1");

        tourAPIDTO.stream().forEach(a -> {System.out.println(a.toString());});
    }
}