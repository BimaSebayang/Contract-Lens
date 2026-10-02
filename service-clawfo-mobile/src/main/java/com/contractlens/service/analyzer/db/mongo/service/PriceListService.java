package com.contractlens.service.analyzer.db.mongo.service;

import com.contractlens.service.analyzer.db.mongo.dao.ClawfoPriceListDocument;

import java.util.List;

public interface PriceListService {

    List<ClawfoPriceListDocument.ClawfoServiceDocument> getLayananByLaundryCodeAndNamaLayanan(
            String namaLayanan
    );

    List<ClawfoPriceListDocument.ClawfoServiceDocument> getLayananByEmailAndNamaLayanan(
            String namaLayanan
    );

    List<ClawfoPriceListDocument.ClawfoBannerDocument> getBannerByLaundryCode();

    List<ClawfoPriceListDocument.ClawfoBannerDocument> getBannerByEmail();

    void upsert(
            ClawfoPriceListDocument document
    );

    ClawfoPriceListDocument getAllClawfoPriceListAndBannerByLaundryCode();

    List<ClawfoPriceListDocument> getAllClawfoPriceListAndBannerByEmail();

}
