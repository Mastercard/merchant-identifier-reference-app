/*
 *  Copyright (c) 2023 Mastercard
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

package com.mastercard.merchantidentifierapireferenceapplication.controller;

import com.mastercard.merchantidentifierapireferenceapplication.services.MerchantIdentifierApiReferenceService;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.MerchantByCardAcceptorIdList;
import org.openapitools.client.model.MerchantList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/merchant-identifier")
public class MerchantIdentifierApiController {

    private static final Logger logger = LoggerFactory.getLogger(MerchantIdentifierApiController.class);
    @Autowired
    MerchantIdentifierApiReferenceService merchantIdentifierApiReferenceService;


    @GetMapping(value = {"/merchants"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MerchantList> getMerchants(@RequestParam(required = true, name = "merchant_descriptor") String merchantDescriptor,
                                                       @RequestParam(required = false, name = "match_type") String matchType) {
        try {
            logger.info("Request received for merchants by descriptor - merchant_descriptor : {}, match_type : {}", merchantDescriptor, matchType);
            return ResponseEntity.ok(merchantIdentifierApiReferenceService.getMerchants(merchantDescriptor, matchType));
        } catch (ApiException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.valueOf(exception.getCode()));
        }
    }

    @GetMapping(value = {"/merchants-by-card-acceptor-ids"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MerchantByCardAcceptorIdList> getMerchantByCardAcceptorId(@RequestParam(required = true, name = "card_acceptor_id") String cardAcceptorId) {
        try {
            logger.info("Request received for merchants by card acceptor Id  card_acceptor_id : {}", cardAcceptorId);
            return ResponseEntity.ok(merchantIdentifierApiReferenceService.getMerchantByCardAcceptorId(cardAcceptorId));
        } catch (ApiException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.valueOf(exception.getCode()));
        }
    }

}
