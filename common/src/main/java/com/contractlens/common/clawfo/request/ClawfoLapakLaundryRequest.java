package com.contractlens.common.clawfo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClawfoLapakLaundryRequest {

    @NotBlank
    @Size(max = 100)
    private String namaLaundry;

    @NotBlank
    @Size(max = 10000)
    private String deskripsiLaundry;

    private CurrentLocationProps currentLocation;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CurrentLocationProps{

        private String address;

        private Double longitude;

        private Double latitude;
    }


    private String fotoToko;

    private PaymentMethodProps paymentMethod;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PaymentMethodProps{
        private boolean isCash;
        private boolean isQris;
        private String fotoQris;
        private boolean isBankTransfer;
        private String bankCode;
        private String rekno;
        private String rekOwner;
    }

    private DeliveryServiceProps deliveryService;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DeliveryServiceProps{
        private boolean isPickup;
        private boolean isPickupFree;
        private BigDecimal pickupPayment;
        private boolean isDelivery;
        private boolean isDeliveryFree;
        private BigDecimal delivaryPayment;
    }

    private OperationalHourProps operationalHour;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class OperationalHourProps {

        private String seninStartDay;
        private String seninEndDay;

        private String selasaStartDay;
        private String selasaEndDay;

        private String rabuStartDay;
        private String rabuEndDay;

        private String kamisStartDay;
        private String kamisEndDay;

        private String jumatStartDay;
        private String jumatEndDay;

        private String sabtuStartDay;
        private String sabtuEndDay;

        private String mingguStartDay;
        private String mingguEndDay;
    }



}
