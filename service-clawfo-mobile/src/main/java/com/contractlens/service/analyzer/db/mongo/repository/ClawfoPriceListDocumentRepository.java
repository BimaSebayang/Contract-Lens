package com.contractlens.service.analyzer.db.mongo.repository;

import com.contractlens.service.analyzer.db.mongo.dao.ClawfoPriceListDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClawfoPriceListDocumentRepository extends MongoRepository<ClawfoPriceListDocument, String> {

    List<ClawfoPriceListDocument> findAllByEmail(String email);
    Optional<ClawfoPriceListDocument> findByLaundryCode(String laundryCode);

    @Query(
            value = """
        {
            '_id': ?0
        }
        """,
            fields = """
        {
            'services': {
                '$filter': {
                    'input': '$services',
                    'as': 'service',
                    'cond': {
                        '$regexMatch': {
                            'input': '$$service.namaLayanan',
                            'regex': ?1,
                            'options': 'i'
                        }
                    }
                }
            }
        }
        """
    )
    Optional<ClawfoPriceListDocument> findServicesByLaundryCodeAndNamaLayanan(
            String laundryCode,
            String namaLayanan
    );


    @Query(
            value = """
        {
            'email': ?0
        }
        """,
            fields = """
        {
            'services': {
                '$filter': {
                    'input': '$services',
                    'as': 'service',
                    'cond': {
                        '$regexMatch': {
                            'input': '$$service.namaLayanan',
                            'regex': ?1,
                            'options': 'i'
                        }
                    }
                }
            }
        }
        """
    )
    List<ClawfoPriceListDocument> findServicesByEmailAndNamaLayanan(
            String email,
            String namaLayanan
    );

    @Query("""
    {
        'email': ?0,
        '_id': ?1
    }
    """)
    Optional<ClawfoPriceListDocument> findByEmailAndLaundryCode(
            String email,
            String laundryCode
    );

    @Query("""
    {
        'email': ?0
    }
    """)
    List<ClawfoPriceListDocument> findByEmail(
            String email
    );

}
