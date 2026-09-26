package com.contractlens.service.analyzer.db.mongo.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clawfo_lapak_laundry")
public class ClawfoLaundryDocument {

    @Id
    private String laundryCode;

    @Indexed
    private String email;

    private String deviceId;

    @Indexed
    private String namaLaundry;

    private String deskripsiLaundry;

    private CurrentLocationProps currentLocation;

    private String fotoToko;

    private PaymentMethodProps paymentMethod;

    private DeliveryServiceProps deliveryService;

    private OperationalHourProps operationalHour;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrentLocationProps {

        private String address;

        private Double longitude;

        private Double latitude;

        @Override
        public String toString() {
            return "CurrentLocationProps{" +
                    "address='" + address + '\'' +
                    ", longitude=" + longitude +
                    ", latitude=" + latitude +
                    '}';
        }
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentMethodProps {

        private boolean isCash;

        private boolean isQris;

        private String fotoQris;

        private boolean isBankTransfer;

        private String bankCode;

        private String rekno;

        private String rekOwner;

        @Override
        public String toString() {
            return "PaymentMethodProps{" +
                    "isCash=" + isCash +
                    ", isQris=" + isQris +
                    ", isBankTransfer=" + isBankTransfer +
                    ", bankCode='" + bankCode + '\'' +
                    ", rekno='" + rekno + '\'' +
                    ", rekOwner='" + rekOwner + '\'' +
                    '}';
        }
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeliveryServiceProps {

        private boolean isPickup;

        private boolean isPickupFree;

        private BigDecimal pickupPayment;

        private boolean isDelivery;

        private boolean isDeliveryFree;

        private BigDecimal deliveryPayment;

        @Override
        public String toString() {
            return "DeliveryServiceProps{" +
                    "isPickup=" + isPickup +
                    ", isPickupFree=" + isPickupFree +
                    ", pickupPayment=" + pickupPayment +
                    ", isDelivery=" + isDelivery +
                    ", isDeliveryFree=" + isDeliveryFree +
                    ", deliveryPayment=" + deliveryPayment +
                    '}';
        }
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
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

        @Override
        public String toString() {
            return "OperationalHourProps{" +
                    "seninStartDay='" + seninStartDay + '\'' +
                    ", seninEndDay='" + seninEndDay + '\'' +
                    ", selasaStartDay='" + selasaStartDay + '\'' +
                    ", selasaEndDay='" + selasaEndDay + '\'' +
                    ", rabuStartDay='" + rabuStartDay + '\'' +
                    ", rabuEndDay='" + rabuEndDay + '\'' +
                    ", kamisStartDay='" + kamisStartDay + '\'' +
                    ", kamisEndDay='" + kamisEndDay + '\'' +
                    ", jumatStartDay='" + jumatStartDay + '\'' +
                    ", jumatEndDay='" + jumatEndDay + '\'' +
                    ", sabtuStartDay='" + sabtuStartDay + '\'' +
                    ", sabtuEndDay='" + sabtuEndDay + '\'' +
                    ", mingguStartDay='" + mingguStartDay + '\'' +
                    ", mingguEndDay='" + mingguEndDay + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "ClawfoLaundryDocument{" +
                "laundryCode='" + laundryCode + '\'' +
                ", email='" + email + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", namaLaundry='" + namaLaundry + '\'' +
                ", deskripsiLaundry='" + deskripsiLaundry + '\'' +
                ", currentLocation=" + currentLocation +
                ", paymentMethod=" + paymentMethod +
                ", deliveryService=" + deliveryService +
                ", operationalHour=" + operationalHour +
                '}';
    }
}