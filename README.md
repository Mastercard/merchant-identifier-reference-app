# Merchant Identifier API Reference Application

[![](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/Mastercard/loyalty-user-management-reference/blob/master/LICENSE)

## Table of Contents

- [Overview](#overview)
    * [Compatibility](#compatibility)
    * [References](#references)
    * [Frameworks](#frameworks)
- [Setup](#setup)
    * [Prerequisites](#prerequisites)
    * [Application Configuration](#configuration)
    * [Build and Execute](#build-and-execute)
    * [Reference Application Usage](#reference-application-usage)
    * [Integrating with OpenAPI Generator](#integrating-with-openapi-generator)
- [Use Cases](#use-cases)
    * [Parameters](#parameters)
    * [Merchant Identifier](#merchant-identifier)
- [API Reference](#api-reference)
    - [Reference App API Reference](#ref-app-api-reference)
    - [Merchant Identifier API Reference](#merchant-identifier-api-reference)
- [Authentication](#authentication)
    * [Mastercard's OAuth Library](#oauth-library)
- [Support](#support)
- [License](#license)
    

## Overview <a name="overview"></a>
This project showcases retrieving merchant name and addresses using the [Merchant Identifier API](https://developer.mastercard.com/merchant-identifier/documentation/api-reference/).
This application illustrates connecting to the Merchant Identifier API using Mastercard's OAuth library, and an OpenAPI generated client.

### Compatibility <a name="compatibility"></a>
 * [Java 11](https://www.oracle.com/java/technologies/javase-jdk15-downloads.html) or later
 
### References <a name="references"></a>
* [Mastercard’s OAuth Signer library](https://github.com/Mastercard/oauth1-signer-java)
* [Using OAuth 1.0a to Access Mastercard APIs](https://developer.mastercard.com/platform/documentation/using-oauth-1a-to-access-mastercard-apis/)

## Frameworks <a name="frameworks"></a>
- OpenAPI Generator
- [Java 11+](https://www.oracle.com/java/technologies/downloads)
- [Maven](https://maven.apache.org/download.cgi)

## Setup <a name="frameworks-and-requirements"></a>

### Prerequisites <a name="prerequisites"></a>

* [Mastercard Developers Account](https://developer.mastercard.com/dashboard)
* A text editor or IDE
* [Java 11+](https://www.oracle.com/java/technologies/downloads)
* [Apache Maven 3.3+](https://maven.apache.org/download.cgi)
* Set up the `JAVA_HOME` environment variable to match the location of your Java installation.

### Application Configuration <a name="configuration"> </a>
1. Follow this [credentials quick guide](https://developer.mastercard.com/platform/documentation/getting-started-with-mastercard-apis/quick-start-guide/) to get the credentials needed to run this application
    - Be sure to add `Merchant Identifier` to your project.
    - A zip file will be downloaded automatically with your key.
2. Take note of the given **consumer key**, **keyalias**, and **keystore password** given upon the project creation.
3. Extract zip and place the `.p12` file in `/src/main/resources` of the project.
4. Update the properties found in `/src/main/resources/application.properties`.

#### application.properties

`mastercard.api.base-path=https://sandbox.api.mastercard.com/merchant-identifier`, This is the URL that the application will use to connect to Mastercard. For production usage, just remove `sandbox.`.
    
**Below properties will be required for authentication of API calls.**
    
`mastercard.p12.path=`, this refers to .p12 file found in the signing key. Place .p12 file at src\main\resources in the project folder then add the filename here.
`mastercard.consumer.key=`, this refers to your consumer key. Copy it from "Keys" section on your project page in [Mastercard Developers](https://developer.mastercard.com/dashboard)
`mastercard.keystore.alias=keyalias`, this is the default value of key alias. If it is modified, use the updated one from keys section in [Mastercard Developers](https://developer.mastercard.com/dashboard).
`mastercard.keystore.pass=keystorepassword`, this is the default value of key pass. If it is modified, use the updated one from keys section in [Mastercard Developers](https://developer.mastercard.com/dashboard).


### Build and Execute <a name="build-and-execute"> </a>
1. Run `mvn clean install` from the root of the project directory.
    * When install is run, the [OpenAPI Generator plugin](#integrating-with-openapi-generator) will generate the sources for connecting to the Merchant Identifier API.
2. run `java -jar target/merchantidentifier-api-reference-app-X.X.X.jar` to start the project.
    - **Notice**: Replace `X` with version of the reference app.
    - **Example**: `java -jar target/merchantidentifier-api-reference-app-1.0.0.jar`

### Reference Application Usage <a name="reference-application-usage"></a>
- Use the merchant identifier reference app by providing merchant descriptor to get the matches.
- click on a marker to open more information on the right panel.

### Integrating with OpenAPI Generator <a name="integrating-with-openapi-generator"></a>
[OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator) generates API client libraries from [OpenAPI Specs](https://github.com/OAI/OpenAPI-Specification). 
It provides generators and library templates for supporting multiple languages and frameworks.

See also:
* [OpenAPI Generator (maven Plugin)](https://mvnrepository.com/artifact/org.openapitools/openapi-generator-maven-plugin)
* [OpenAPI Generator (executable)](https://mvnrepository.com/artifact/org.openapitools/openapi-generator-cli)
* [CONFIG OPTIONS for java](https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/java.md)

#### OpenAPI Generator Plugin Configuration
 
 ```xml
 <plugin>
     <groupId>org.openapitools</groupId>
     <artifactId>openapi-generator-maven-plugin</artifactId>
     <version>4.3.0</version>
     <executions>
         <execution>
             <goals>
                 <goal>generate</goal>
             </goals>
             <configuration>
                 <inputSpec>${project.basedir}/src/main/resources/merchant-identifier-api-spec.yaml</inputSpec>
                 <generatorName>java</generatorName>
                 <library>okhttp-gson</library>
                 <configurationFile>${project.basedir}/src/main/resources/openapi-config.json</configurationFile>
                 <generateApiTests>false</generateApiTests>
                 <generateModelTests>false</generateModelTests>
                 <configOptions>
                     <sourceFolder>src/gen/java/main</sourceFolder>
                 </configOptions>
             </configuration>
         </execution>
     </executions>
 </plugin>
 ```

## Use Cases <a name="use-cases"></a>

#### Parameters <a name="parameters"></a>
These are the parameters used for the Merchant Identifier API.

| Name                  | Type      | Default Value      | Purpose       |
|-----------------------|-----------|--------------------|---------------|
| merchant_descriptor   | string    | 0                  | For finding merchant list with identifier.        |
| match_type            | string    | ExactMatch         | For type of the search to be performed with merchant identifier |


> Get information on a Merchant Identifier. 

| MerchantIdentfier API URL     | Method        | Parameters          | Request Model | Response model            |
|-------------------------------|---------------|---------------------|---------------|---------------------------|
| /merchants                    | GET           | merchant_descriptor | string        | merchant list with address|


## API Reference <a name="api-reference"></a>

### Reference Application API Reference <a name="ref-app-api-reference"></a>

| Reference App URL                 | Parameters          | Reference App Usage             | Merchant Identifier Endpoint Used  |
|-----------------------------------|---------------------|---------------------------------|-----------------------|
|**/merchant-identifier/merchants** | merchant_descriptor | Search for merchant list with merchant identifier by match type| /merchant-identifier/merchants |

Example Search Request in a rest client  of your choice: `https://sandbox.api.mastercard.com/merchant-identifier/merchants?merchant_descriptor=DOLIUMPTYLTDWELSHPOOLWA&match_type=ExactMatch`

### Merchant Identifier API Reference <a name="merchant-identifier-api-reference"></a>

See the [API Reference](https://developer.mastercard.com/merchant-identifier/documentation/api-reference) page in the documentation. 

| API Endpoint                  | Description                                                       |
| ----------------------------- | ----------------------------------------------------------------- |
| [Merchant Identifier](https://developer.mastercard.com/merchant-identifier/documentation/api-reference#api)                 | Search for merchant list with matching merchant identifier |

## Authentication <a name="authentication"></a>

### Mastercard oauth1 Signer Library <a name="oauth-library"></a>
This dependency is required to properly call the API.
```xml
<dependency>
    <groupId>com.mastercard.developer</groupId>
    <artifactId>oauth1-signer</artifactId>
    <version>1.2.3</version>
</dependency>
```
[Link to the oauth1 library's Github](https://github.com/Mastercard/oauth1-signer-java)

[Looking for other languages?](https://github.com/Mastercard?q=oauth&type=&language=)

See the code used in this application to utilize the library.
```Java
Found in /src/java/com.mastercard.midreferenceapplciation.config.ApiClientConfiguration

ApiClient client = new ApiClient();
HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger::info);
loggingInterceptor.level(HttpLoggingInterceptor.Level.BASIC);
try {
    client.setBasePath(basePath);
    client.setHttpClient(
            client.getHttpClient()
                    .newBuilder()
                    .addInterceptor(new OkHttpOAuth1Interceptor(consumerKey, getSigningKey()))
                    .addInterceptor(loggingInterceptor)
                    .build()
    );

    return client;
} catch (Exception e) {
    logger.error("Error occurred while configuring ApiClient", e);
}
return client;
```

## Support <a name="support"></a>
If you would like further information, please send an email to apisupport@mastercard.com

## License <a name="license"></a>
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

**Copyright © 1994-2022, All Rights Reserved by Mastercard.**
