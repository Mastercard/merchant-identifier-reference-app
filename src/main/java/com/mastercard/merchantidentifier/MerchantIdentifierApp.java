package com.mastercard.merchantidentifier;
/*
 *  Copyright (c) 2022 Mastercard
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.mastercard.developer.interceptors.OkHttpOAuth1Interceptor;
import com.mastercard.developer.utils.AuthenticationUtils;
import okhttp3.logging.HttpLoggingInterceptor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.MerchantIdApi;
import org.openapitools.client.model.Merchant;
import org.openapitools.client.model.MerchantList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;
import java.util.ResourceBundle;


public class MerchantIdentifierApp {

    private static final Logger logger = LoggerFactory.getLogger(MerchantIdentifierApp.class);

    public static void main(String[] args){

        ResourceBundle rb = ResourceBundle.getBundle("application");
        String signingKeyFilePath  = rb.getString("mastercard.p12.path");
        String consumerKey  = rb.getString("mastercard.consumer.key");
        String signingKeyAlias  = rb.getString("mastercard.keystore.alias");
        String signingKeyPassword  = rb.getString("mastercard.keystore.pass");
        String basePath  = rb.getString("mastercard.basePath");

        logger.info("{} , {}, {}, {}, {}"
                ,signingKeyFilePath, consumerKey, signingKeyAlias, signingKeyPassword, basePath);

        try {
            PrivateKey signingKey = AuthenticationUtils.loadSigningKey(signingKeyFilePath, signingKeyAlias, signingKeyPassword);
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger::info);
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BASIC);

            ApiClient client = new ApiClient();
            client.setBasePath(basePath);
            client.setDebugging(true);
            client.setHttpClient(
                client.getHttpClient()
                        .newBuilder()
                        .addInterceptor(new OkHttpOAuth1Interceptor(consumerKey, signingKey))
                        .addInterceptor(loggingInterceptor)
                        .build()
            );

            MerchantIdApi merchantIdApi = new MerchantIdApi(client);

            //** example loop returning 1 merchant for exact match **/
            logger.info("****example loop returning 1 merchant for exact match****");
            MerchantList result = merchantIdApi.getMerchantIds("DOLIUMPTYLTDWELSHPOOLWA", "ExactMatch");
            printResult(result);

            //** example loop returning multiple merchants for fuzzy match **/
            logger.info("****example loop returning multiple merchants for fuzzy match****");
            result = merchantIdApi.getMerchantIds("KELLERWILLIAMS", "FuzzyMatch");
            printResult(result);
        } catch (Exception err) {
            logger.info("Error processing api call -> " + err.getMessage());;
        }
    }

    public static void printResult(MerchantList result){
        for(Merchant item : result.getMerchants()) {
            logger.info("----------------------------------------------------------");
            logger.info("MatchConfidenceScore -> " +  item.getMatchConfidenceScore());
            logger.info("MerchantCategory -> " +  item.getMerchantCategory());
            logger.info("MerchantDescriptor -> " +  item.getMerchantDescriptor());
            logger.info("PhoneNumber -> " +  item.getPhoneNumber());
            logger.info("LocationId -> " +  item.getLocationId());
            logger.info("Address Line 1-> " +  item.getAddress().getLine1());
            logger.info("Address Postal Code-> " +  item.getAddress().getPostalCode());
            logger.info("Address City-> " +  item.getAddress().getCity());
            logger.info("Address Country -> " +  item.getAddress().getCountry().getCode());
            logger.info("----------------------------------------------------------");
        }
    }

}
