/*
 * Copyright 2016 MasterCard International.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution.
 * Neither the name of the MasterCard International Incorporated nor the names of its
 * contributors may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */

angular.module('mcdapimid.controllers', ['angularMoment'])

    .controller('AppCtrl', function ($scope, $ionicModal, $timeout) {

        // With the new view caching in Ionic, Controllers are only called
        // when they are recreated or on app start, instead of every page change.
        // To listen for when this page is active (for example, to refresh data),
        // listen for the $ionicView.enter event:
        //$scope.$on('$ionicView.enter', function(e) {
        //});

    })
    .controller('AccountDetailsController', ['$scope', '$stateParams', 'Session', 'AccountService',
        function ($scope, $stateParams, Session, AccountService) {
            $scope.account = {};
            $scope.todayDate = new Date();

            AccountService.get(1, function (account) {
                $scope.$apply(function () {
                    $scope.account = account;
                });

            });
        }])

    .controller('TransactionDetailsController', ['$scope', '$stateParams', 'Session', 'AccountService', '$ionicLoading',
        function ($scope, $stateParams, Session, AccountService, $ionicLoading) {
            $scope.account = {};
            $scope.transaction = {};
            $scope.merchant = null;
            $scope.addressString = '';
            $scope.addressLine3 = '';
            $scope.markers = [];

            $ionicLoading.show({
                template: 'Loading...'
            });

            AccountService.transaction($stateParams.accountId, $stateParams.id, function (account, transaction, merchant) {
                $scope.$apply(function () {
                    if (null == $scope.merchant) {
                        AccountService.query(transaction.merchantId, function (data) {
                            if (null != data && 0 < data.MerchantIds.ReturnedMerchants.Merchant.length) {
                                $scope.account = account;
                                $scope.transaction = transaction;
                                $scope.merchant = merchant;
                                $scope.merchant = data.MerchantIds.ReturnedMerchants.Merchant[0];

                                var mAddr = $scope.merchant.Address;
                                $scope.addressLine3 += mAddr.City == mAddr.Country.Name ? '' : mAddr.City + ', ';
                                $scope.addressLine3 += mAddr.PostalCode + ' ' + mAddr.Country.Name;

                                $scope.addressString = $scope.merchant.Address.Line1 + ','
                                            + $scope.merchant.Address.Line2 + ','
                                            + $scope.addressLine3;

                                $scope.markers = [$scope.addressString];

                                $ionicLoading.hide();
                            }
                        });
                    }
                });

            });
        }]);
