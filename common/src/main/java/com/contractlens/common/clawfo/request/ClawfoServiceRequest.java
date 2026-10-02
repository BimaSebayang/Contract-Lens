package com.contractlens.common.clawfo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClawfoServiceRequest {

    List<ClawfoService> services;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ClawfoService{
            private Integer serviceId;
            private String fotoLayanan;
            private String namaLayanan;
            private String description;
            private String estimasiLayananHari;
            private BigDecimal price;
            private boolean showService;
            private boolean promotionService;
        }
}
