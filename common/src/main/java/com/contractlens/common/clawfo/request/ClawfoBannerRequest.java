package com.contractlens.common.clawfo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClawfoBannerRequest {

    List<ClawfoBanner> banners;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ClawfoBanner{
        private Integer templateId;
        private String logo;
        private String judul;
        private String deskripsi;
        private String gambarProduk;
    }
}
