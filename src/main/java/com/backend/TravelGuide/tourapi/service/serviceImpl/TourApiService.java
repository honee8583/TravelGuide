package com.backend.TravelGuide.tourapi.service.serviceImpl;

import com.backend.TravelGuide.tourapi.DTO.TourApiDTO;
import com.backend.TravelGuide.tourapi.error.KeywordNotExistsException;
import com.backend.TravelGuide.tourapi.error.ResultNotExistsException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TourApiService {
    @Value("${api.key}")
    private String serviceKey;
    private final String baseUrl = "https://apis.data.go.kr/B551011/KorService1/";

    public List<TourApiDTO> keywordSearchApi(String keyword, String pageNo) {
        StringBuffer sb = new StringBuffer();
        String apiUrl;

        if (keyword == null || keyword.trim().equals("")) {
            log.warn("검색 키워드가 없습니다");
            throw new KeywordNotExistsException();
        }

        if (pageNo.trim().equals("") || pageNo == null) {
            pageNo = "1";
        }

        try {
            log.info(">>> 키워드 검색");
            String newKeyword = URLEncoder.encode(keyword, "UTF-8");    // 국문은 인코딩 필요!
            apiUrl = baseUrl + "searchKeyword1?serviceKey=" + serviceKey +
                    "&_type=json&pageNo=" + pageNo +
                    "&numOfRows=10&MobileOS=ETC&MobileApp=AppTest&arrange=A&contentTypeId=12"
                    + "&keyword=" + newKeyword;

            log.info("serviceKey: " + serviceKey);
            log.info("URL: " + apiUrl);

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader br;
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            } else {
                System.out.println("wrong");
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String returnLine;
            while((returnLine = br.readLine()) != null) {
                sb.append(returnLine + "\n");
            }
            br.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = sb.toString();
        log.info(result);
        Map<String, Object> resultMap = returnMap(result);
        List<TourApiDTO> tourDtoList = new ArrayList<>();

        org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) resultMap.get("item");
        for (int i = 0; i < jsonArray.size(); i++) {
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) jsonArray.get(i);
            TourApiDTO tourDto = TourApiDTO.builder()
                    .addr1(obj.get("addr1").toString())
                    .addr2(obj.get("addr2").toString())
                    .areacode(obj.get("areacode").toString())
                    .contentId(obj.get("contentid").toString())
                    .contentTypeId(obj.get("contenttypeid").toString())
                    .mapx(obj.get("mapx").toString())
                    .mapy(obj.get("mapy").toString())
                    .title(obj.get("title").toString())
                    .image(obj.get("firstimage").toString())
                    .build();
            tourDtoList.add(tourDto);
        }

        return tourDtoList;
    }

    public Map<String, Object> returnMap(String result) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try{
            jsonObject = (JSONObject) jsonParser.parse(result);
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();

        JSONObject data = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) data.get("body");    // pageNo, totalCount, items 포함
        if (body.get("items").toString().equals("")) {
            throw new ResultNotExistsException();
        } else {
            JSONObject items = (JSONObject) body.get("items");  // item 포함
            JSONArray item = (JSONArray) items.get("item"); // 관광지 리스트 포함
            resultMap.put("item", item);
        }

        return resultMap;
    }
}
