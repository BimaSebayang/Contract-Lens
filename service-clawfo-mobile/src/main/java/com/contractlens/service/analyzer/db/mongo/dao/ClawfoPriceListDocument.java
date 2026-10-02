package com.contractlens.service.analyzer.db.mongo.dao;


import com.contractlens.common.clawfo.request.ClawfoBannerRequest;
import com.contractlens.common.clawfo.request.ClawfoServiceRequest;
import com.contractlens.service.analyzer.db.mongo.dao.component.ClawfoComponent;
import com.contractlens.service.analyzer.db.mongo.dao.component.ClawfoTimerComponent;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clawfo_pricelist_document")
public class ClawfoPriceListDocument extends ClawfoComponent {

    @Id
    private String laundryCode;

    List<ClawfoServiceDocument> services;

    List<ClawfoBannerDocument> banners;


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ClawfoServiceDocument extends ClawfoTimerComponent {
        private String  serviceId;
        private String mainFotoUrl;
        private List<MenuImageProps> fotos;
        private String namaLayanan;
        private String views;
        private String orders;
        private String description;
        private Integer estimationMin;
        private Integer estimationMax;
        private BigDecimal price;
        private boolean showService;
        private boolean promotionService;

        @Setter
        @Getter
        public static class MenuImageProps{
            private String fotoId;
            private Integer sequence;
            private String imageUrl;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ClawfoBannerDocument extends ClawfoTimerComponent{
        private Integer templateId;
        private String logo;
        @Indexed
        private String judul;
        private String deskripsi;
        private String gambarProduk;
    }

}
