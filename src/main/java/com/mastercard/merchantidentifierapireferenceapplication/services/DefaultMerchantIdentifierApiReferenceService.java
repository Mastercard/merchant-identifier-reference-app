package com.mastercard.merchantidentifierapireferenceapplication.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.MerchantsApi;
import org.openapitools.client.model.MerchantByCardAcceptorIdList;
import org.openapitools.client.model.MerchantList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class DefaultMerchantIdentifierApiReferenceService implements MerchantIdentifierApiReferenceService {


    private static final Logger logger = LoggerFactory.getLogger(DefaultMerchantIdentifierApiReferenceService.class);


    private MerchantsApi merchantsApi;


    @Autowired
    public DefaultMerchantIdentifierApiReferenceService(ApiClient apiClient) {
        logger.info("-->> INITIALIZING APIS");

        merchantsApi = new MerchantsApi(apiClient);

    }
    @Override
    public MerchantList getMerchants(String merchantDescriptor, String matchType)  throws ApiException {
            return merchantsApi.getMerchants(merchantDescriptor, matchType);
    }

    @Override
    public MerchantByCardAcceptorIdList getMerchantByCardAcceptorId(String cardAcceptorId) throws ApiException {
            return  merchantsApi.getMerchantByCardAcceptorId(cardAcceptorId);
    }

    public String getErrorAttributes(Exception e) {
        String errorDetails;
        if(e instanceof ApiException) {
            ApiException apiException = ((ApiException) e);

            //Attempt to parse as JSON
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                errorDetails = gson.toJson(JsonParser.parseString(apiException.getResponseBody()));

            } catch (Exception ignoredException) {

                //Print full string in case of not JSON
                errorDetails = apiException.getResponseBody();
            }

        } else {

            logger.error("Error occurred!",e);
            errorDetails = e.toString();

        }

        return errorDetails;
    }


}
