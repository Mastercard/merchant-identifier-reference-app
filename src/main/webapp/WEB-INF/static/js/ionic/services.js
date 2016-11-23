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

angular.module('mcdapimid.services', [])

    .factory('Session', function ($http) {
        var Session = {
            data: {},
            set: function (key, value) {
                Session.data[key] = value;
            },
            get: function (key) {
                return Session.data[key];
            }
        };
        return Session;
    })
    .service('AccountService', ['MIDApi', function (MIDApi) {
        var accounts = [
            {
                id: 1,
                name: 'Recent Transactions',
                transactions: [
                    {
                        id: 6,
                        amt: 6.50,
                        category: 'Food',
                        merchant: 'Xiang Signature',
                        merchantId: 'XIANG SIGNATURE SINGAPORE SG',
                        icon: 'restaurant.svg'
                    },
                    {
                        id: 5,
                        amt: 10.99,
                        category: 'Shop',
                        merchant: '7-Eleven',
                        merchantId: '7-ELEVEN#22966D2VANCOUVERBC',
                        icon: 'shopping.svg'
                    },
                    {
                        id: 4,
                        amt: 4.50,
                        icon: 'coffee.svg',
                        merchant: 'Starbucks Cofee',
                        merchantId: 'STARBUCKS ANT A 10643',
                        category: 'Coffee'
                    },
                    {
                        id: 3,
                        amt: 84.63,
                        category: 'Store',
                        merchant: 'Takashimaya',
                        merchantId: 'TAKASHIMAYA ORCHARD SINGAPORE SG',
                        icon: 'cart.svg'
                    },
                    {
                        id: 2,
                        amt: 14.63,
                        category: 'Food',
                        merchant: 'CAFETERIA',
                        merchantId: 'CAFETERIA 30020523',
                        icon: 'fastfood.svg'
                    },
                    {
                        id: 1,
                        amt: 55.00,
                        category: 'Shop',
                        merchant: 'Toy\'s R Us',
                        merchantId: 'TOYSRUSNEWYORKNY',
                        icon: 'shopping.svg',
                    }
                ]
            }
        ];
        var ret = {
            get: function (id, callback) {
                setTimeout(function () {
                    callback(_.head(accounts.filter(function(account) {
                        return id == account.id
                    })));
                }, 1000);
            },
            transaction: function (account, id, callback) {
                ret.get(account, function (account) {
                    callback(account, _.head(account.transactions.filter(function(trans) {
                        return trans.id == id
                    })));
                })
            },
            query: function (query, callback) {
                MIDApi.query(query, function (merchantResp) {
                    callback(merchantResp)
                });
            }
        };
        return ret;

    }]);
