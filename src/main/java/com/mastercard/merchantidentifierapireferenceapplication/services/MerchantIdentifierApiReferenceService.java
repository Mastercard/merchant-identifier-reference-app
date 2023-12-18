package com.mastercard.merchantidentifierapireferenceapplication.services;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.MerchantByCardAcceptorIdList;
import org.openapitools.client.model.MerchantList;
import org.springframework.stereotype.Service;

@Service
public interface MerchantIdentifierApiReferenceService {

    MerchantList getMerchants(String merchantDescriptor, String matchType) throws ApiException;

    MerchantByCardAcceptorIdList getMerchantByCardAcceptorId(String cardAcceptorId)  throws ApiException;

}
