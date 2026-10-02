package com.contractlens.service.analyzer.db.mongo.service.impl;

import com.contractlens.common.enums.WordingClawfo;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoPriceListDocument;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoRegistrationDocument;
import com.contractlens.service.analyzer.db.mongo.repository.ClawfoPriceListDocumentRepository;
import com.contractlens.service.analyzer.db.mongo.repository.ClawfoRegistrationRepository;
import com.contractlens.service.analyzer.db.mongo.service.PriceListService;
import com.contractlens.service.analyzer.infrastructure.ClawfoException;
import com.contractlens.service.analyzer.infrastructure.ClawfoJwtService;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClawfoPriceListServiceImpl implements PriceListService {

    private final ClawfoPriceListDocumentRepository clawfoPriceListDocumentRepository;
    private final ClawfoRegistrationRepository clawfoRegistrationRepository;
    private final ClawfoJwtService clawfoJwtService;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<ClawfoPriceListDocument.ClawfoServiceDocument> getLayananByLaundryCodeAndNamaLayanan(String namaLayanan) {
        ClawfoRegistrationDocument selfDocument = selfDocument();
        String laundryCode = selfDocument.getLaundryCode();

        ClawfoPriceListDocument documents = clawfoPriceListDocumentRepository.findServicesByLaundryCodeAndNamaLayanan(laundryCode,namaLayanan).orElse(null);

        if(Objects.isNull(documents)){
            return new ArrayList<>();
        }

        return documents.getServices();
    }

    @Override
    public List<ClawfoPriceListDocument.ClawfoServiceDocument> getLayananByEmailAndNamaLayanan(String namaLayanan) {
        ClawfoRegistrationDocument selfDocument = selfDocument();
        String email = selfDocument.getEmail();

        List<ClawfoPriceListDocument> priceListDocuments = clawfoPriceListDocumentRepository.findServicesByEmailAndNamaLayanan(email,namaLayanan);
        List<ClawfoPriceListDocument.ClawfoServiceDocument> serviceDocuments = new ArrayList<>();

        for (ClawfoPriceListDocument doc : priceListDocuments){
            serviceDocuments.addAll(doc.getServices());
        }

        return serviceDocuments;
    }

    @Override
    public List<ClawfoPriceListDocument.ClawfoBannerDocument> getBannerByLaundryCode() {
        ClawfoRegistrationDocument selfDocument = selfDocument();
        String laundryCode = selfDocument.getLaundryCode();
        Optional<ClawfoPriceListDocument> listDocument = clawfoPriceListDocumentRepository.findByLaundryCode(laundryCode);
        if(listDocument.isEmpty()){
            return new ArrayList<>();
        }
        return listDocument.get().getBanners();
    }

    @Override
    public List<ClawfoPriceListDocument.ClawfoBannerDocument> getBannerByEmail() {
        ClawfoRegistrationDocument selfDocument = selfDocument();
        String email = selfDocument.getEmail();
        List<ClawfoPriceListDocument> listDocument = clawfoPriceListDocumentRepository.findByEmail(email);
        List<ClawfoPriceListDocument.ClawfoBannerDocument> bannerDocuments = new ArrayList<>();

        for (ClawfoPriceListDocument document : listDocument){
            bannerDocuments.addAll(document.getBanners());
        }

        return bannerDocuments;
    }


    @Override
    public void upsert(ClawfoPriceListDocument document) {

        ClawfoRegistrationDocument selfDocument = selfDocument();

        String email = selfDocument.getEmail();
        String laundryCode = selfDocument.getLaundryCode();


        LocalDateTime now = LocalDateTime.now();

        ClawfoPriceListDocument realData =
                clawfoPriceListDocumentRepository
                        .findByLaundryCode(laundryCode)
                        .orElse(null);


        Query query = Query.query(
                Criteria.where("_id").is(laundryCode)
        );

        Update update = new Update()
                .set("email", email)
                .set("updatedBy", email)
                .set("updatedDate", now)
                .setOnInsert("_id",laundryCode)
                .setOnInsert("createdBy", email)
                .setOnInsert("createdDate", now);

        if (document.getServices() != null) {

            boolean isServiceExists =
                    realData != null &&
                            realData.getServices() != null &&
                            !realData.getServices().isEmpty();

            if(isServiceExists) {
                isServiceExists = realData.getServices()
                        .stream()
                        .anyMatch(serv -> Objects.equals(document.getServices().get(0).getServiceId(), serv.getServiceId()));
            }

            upsertServices(
                    update,
                    email,
                    document.getServices(),
                    now,
                    isServiceExists
            );
        }

        if (document.getBanners() != null) {


            boolean isBannerExists =
                    realData != null &&
                            realData.getBanners() != null &&
                            !realData.getBanners().isEmpty();

            if(isBannerExists) {
                isBannerExists = realData.getBanners()
                        .stream()
                        .anyMatch(banner -> Objects.equals(document.getBanners().get(0).getBannerId(), banner.getBannerId()));
            }

            upsertBanners(
                    update,
                    email,
                    document.getBanners(),
                    now,
                    isBannerExists
            );
        }

         mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options()
                        .upsert(true)
                        .returnNew(true),
                ClawfoPriceListDocument.class
        );
    }

    @Override
    public ClawfoPriceListDocument getAllClawfoPriceListAndBannerByLaundryCode() {
        ClawfoRegistrationDocument selfDocument = selfDocument();
        String laundryCode = selfDocument.getLaundryCode();
        return clawfoPriceListDocumentRepository
                .findByLaundryCode(laundryCode)
                .orElseThrow(() -> new ClawfoException(
                        WordingClawfo.PRICE_LIST_NOT_FOUND
                ));
    }

    @Override
    public List<ClawfoPriceListDocument> getAllClawfoPriceListAndBannerByEmail() {
        ClawfoRegistrationDocument selfDocument = selfDocument();
        String email = selfDocument.getEmail();
        return clawfoPriceListDocumentRepository
                .findAllByEmail(email);
    }

    @Override
    public void deleteLaundryByBannerId(String bannerId) {
        ClawfoRegistrationDocument selfDocument = selfDocument();
        String laundryCode = selfDocument.getLaundryCode();
        ClawfoPriceListDocument realData =
                clawfoPriceListDocumentRepository
                        .findByLaundryCode(laundryCode)
                        .orElse(null);

        if(Objects.isNull(realData)){
            log.info("deleteLaundryByBannerId for banner Id {} not find data for realData {}", bannerId, null);
            return;
        }

        boolean nonExistsBannerId = realData.getBanners().stream().noneMatch(banner-> Objects.equals(banner.getBannerId(),bannerId));

        if(nonExistsBannerId){
            log.info("deleteLaundryByBannerId for banner Id {} required nonExistsBannerId : {}", bannerId, true);
            return;
        }

        Query query = new Query(
                Criteria.where("_id").is(laundryCode)
                        .and("banners.bannerId").is(bannerId)
        );

        Update update = new Update()
                .pull(
                        "banners",
                        Query.query(
                                Criteria.where("bannerId").is(bannerId)
                        )
                );

        mongoTemplate.updateFirst(
                query,
                update,
                ClawfoPriceListDocument.class
        );
    }

    @Override
    public void deleteServiceLaundry(String serviceId) {
        ClawfoRegistrationDocument selfDocument = selfDocument();
        String laundryCode = selfDocument.getLaundryCode();
        ClawfoPriceListDocument realData =
                clawfoPriceListDocumentRepository
                        .findByLaundryCode(laundryCode)
                        .orElse(null);

        if(Objects.isNull(realData)){
            log.info("deleteServiceLaundryId for service Id {} not find data for realData {}", serviceId, null);
            return;
        }

        boolean nonExistsServiceId = realData.getServices().stream().noneMatch(banner-> Objects.equals(banner.getServiceId(),serviceId));

        if(nonExistsServiceId){
            log.info("deleteServiceLaundryId for service Id {} required nonExistsBannerId : {}", serviceId, true);
            return;
        }

        Query query = new Query(
                Criteria.where("_id").is(laundryCode)
                        .and("services.serviceId").is(serviceId)
        );

        Update update = new Update()
                .pull(
                        "services",
                        Query.query(
                                Criteria.where("serviceId").is(serviceId)
                        )
                );

        mongoTemplate.updateFirst(
                query,
                update,
                ClawfoPriceListDocument.class
        );
    }

    private void upsertServices(
            Update update,
            String email,
            List<ClawfoPriceListDocument.ClawfoServiceDocument> services,
            LocalDateTime now,
            boolean isServicesExits
    ) {

        for (ClawfoPriceListDocument.ClawfoServiceDocument service : services) {

            /*
             * INSERT
             */
            if (!Strings.hasText(service.getServiceId())) {

                service.setServiceId(UUID.randomUUID().toString());

                service.setCreatedBy(email);
                service.setCreatedDate(now);
                service.setUpdatedBy(email);
                service.setUpdatedDate(now);

                update.push("services", service);

                continue;
            }

            /*
             * UPDATE
             */
            service.setUpdatedBy(email);
            service.setUpdatedDate(now);

            update.set(
                    "services.$[service].mainFotoUrl",
                    service.getMainFotoUrl()
            );

            update.set(
                    "services.$[service].namaLayanan",
                    service.getNamaLayanan()
            );

            update.set(
                    "services.$[service].fotos",
                    service.getFotos()
            );

            update.set(
                    "services.$[service].description",
                    service.getDescription()
            );

            update.set(
                    "services.$[service].estimationMax",
                    service.getEstimationMax()
            );

            update.set(
                    "services.$[service].estimationMin",
                    service.getEstimationMin()
            );

            update.set(
                    "services.$[service].price",
                    service.getPrice()
            );

            update.set(
                    "services.$[service].showService",
                    service.isShowService()
            );

            update.set(
                    "services.$[service].promotionService",
                    service.isPromotionService()
            );

            update.set(
                    "services.$[service].updatedBy",
                    service.getUpdatedBy()
            );

            update.set(
                    "services.$[service].updatedDate",
                    service.getUpdatedDate()
            );

            update.filterArray(
                    Criteria.where("service.serviceId")
                            .is(service.getServiceId())
            );
        }
    }

    private void upsertBanners(
            Update update,
            String email,
            List<ClawfoPriceListDocument.ClawfoBannerDocument> banners,
            LocalDateTime now,
            boolean isBannerExists
    ) {

        for (ClawfoPriceListDocument.ClawfoBannerDocument banner : banners) {

            if (banner.getTemplateId() == null) {
                continue;
            }

            banner.setUpdatedBy(email);
            banner.setUpdatedDate(now);


            if (isBannerExists) {

                /*
                 * UPDATE existing banner
                 */
                update.set(
                        "banners.$[banner]",
                        banner
                );

                update.filterArray(
                        Criteria.where("banner.bannerId")
                                .is(banner.getBannerId())
                );

            } else {

                banner.setCreatedBy(email);
                banner.setCreatedDate(LocalDateTime.now());
                update.push(
                        "banners",
                        banner
                );
            }
        }
    }

    private ClawfoRegistrationDocument selfDocument(){
        String email = clawfoJwtService
                .getCurrentUser()
                .getEmail();

        return clawfoRegistrationRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ClawfoException(
                                WordingClawfo.USER_NOT_FOUND
                        )
                );
    }

}
