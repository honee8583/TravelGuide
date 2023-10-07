package com.backend.TravelGuide.review.domain;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImageDTO implements Serializable {
    private Long id;
    private String fileName;
    private String uuid;
    private String folderPath;

    public static ReviewImageDTO entityToDTO(ReviewImage reviewImage) {
        return ReviewImageDTO.builder()
                .id(reviewImage.getId())
                .fileName(reviewImage.getImgName())
                .uuid(reviewImage.getUuid())
                .folderPath(reviewImage.getPath())
                .build();
    }

    public String getImageUrl() {
        try {
            return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();    // TODO
        }
        return "";
    }

    public String getThumbnailUrl() {
        try {
            return URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();    // TODO
        }
        return "";
    }
}
