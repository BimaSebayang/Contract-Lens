package com.contractlens.service.analyzer.module.pricelist.service;

import com.contractlens.service.analyzer.db.mongo.dao.ClawfoPriceListDocument;
import com.contractlens.service.analyzer.db.mongo.service.PriceListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClawfoPriceListService {

    private final PriceListService priceListService;

    public void postDetailPriceListBanner(String deviceId, String ticketId, String longitude, String latitude, String location, ClawfoPriceListDocument.ClawfoBannerDocument serviceBanner) {
        ClawfoPriceListDocument document = new ClawfoPriceListDocument();
        document.setBanners(Collections.singletonList(serviceBanner));
        priceListService.upsert(document);
    }

    public void postDetailPriceListService(String deviceId, String ticketId, String longitude, String latitude, String location, ClawfoPriceListDocument.ClawfoServiceDocument serviceDocument) {
        ClawfoPriceListDocument document = new ClawfoPriceListDocument();
        document.setServices(Collections.singletonList(serviceDocument));

        if(!CollectionUtils.isEmpty(serviceDocument.getFotos())){
            ClawfoPriceListDocument.ClawfoServiceDocument.MenuImageProps imageProps = serviceDocument.getFotos().get(0);
            if(imageProps.getSequence() == 1){
                serviceDocument.setMainFotoUrl(imageProps.getImageUrl());
            }
        }

        priceListService.upsert(document);
    }

    public ClawfoPriceListDocument getDetailPriceList(String deviceId, String ticketId) {
       return priceListService.getAllClawfoPriceListAndBannerByLaundryCode();
    }


    public void deleteBannerLaundry(String deviceId, String ticketId, String longitude, String latitude, String location, String bannerId) {
        priceListService.deleteLaundryByBannerId(bannerId);
    }

    public void deleteServiceLaundry(String deviceId, String ticketId, String longitude, String latitude, String location, String serviceId) {
        priceListService.deleteServiceLaundry(serviceId);
    }
}
