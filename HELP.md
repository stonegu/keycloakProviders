#HELP
```
* mysql integration
* my.ini for mysql
* Issue during the integration
* modify xms and xmx in standalone.conf.bat
* SMTP Server
* Service Provider Interfaces (SPI)
   * Register a provider using Modules
      * jboss-cli.sh
      * manually register
* Keycloak configuration
   * Realm Setting
   * Client Setting
   * User Setting
      * customized attribute in token
         * By User attribute
         * By Group attribute
         * Aggregate & Multivalued Attribute
```
##1. mysql integration
Check this link: [Keycloak MySQL Setup](https://github.com/Codingpedia/codingmarks-api/wiki/Keycloak-MySQL-Setup) 
<br/>
Note: make sure that table create with CHARSET=utf8 COLLATE=utf8_unicode_ci
<br/>
UPDATE FOR KEYCLOAK-11.0.2: [Relational Database Setup](https://www.keycloak.org/docs/latest/server_installation/index.html#_database)

##2. my.ini for mysql
Check this link: [C:\ProgramData\MySQL\MySQL Server 5.7](C:\ProgramData\MySQL\MySQL Server 5.7)

##3. Issue during the integration
I manually update the keycloak table because some issues happened during the server startup.
   1. update below two properties in standalone.xml for manual tables update
      * ```<property name="initializeEmpty" value="false"/>```
      * ```<property name="migrationStrategy" value="manual"/>```
      * check the description here: [Keycloak MySQL Setup](ttps://github.com/Codingpedia/codingmarks-api/wiki/Keycloak-MySQL-Setup)
   1. run keycloak-database-update.sql
   1. make sure connection-url in standalone.xml for characterEncoding is 'UTF-8'
   
##4. modify xms and xmx in standalone.conf.bat
```set "JAVA_OPTS=-Xms512M -Xmx2048M -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m"``` 
<br/>
NOTE: initial admin account is : admin : admin

##5. SMTP Server
```bizislife2019@gmail.com : stone0331```

##6. Service Provider Interfaces (SPI)
Detailed document check this: [Service Provider Interfaces](https://www.keycloak.org/docs/latest/server_development/index.html#_providers)
<br/>
Note: If you are creating a custom SPI you will need to deploy it as a module, otherwise we recommend using the Keycloak deployer approach.
###6.1 Register a provider using Modules
####6.1.1 jboss-cli.sh
check the link [Service Provider Interfaces](https://www.keycloak.org/docs/latest/server_development/index.html#_providers) 
or check the example in C:\Projects\keycloak\examples\providers\domain-extension\README.md
####6.1.2 manually register
   1. create a folder like this : C:\Users\stone\Downloads\InstallApp\keycloak-4.8.3.Final\modules\com\bizislife\keycloak\main
   1. copy keycloak-provider.jar and module.xml in this folder
      * example for module.xml
      ```
         <?xml version='1.0' encoding='UTF-8'?>
         <module xmlns="urn:jboss:module:1.1" name="com.bizislife.keycloak">
            <resources>
               <resource-root path="keycloak-provider.jar"/>
            </resources>
            <dependencies>
               <module name="org.keycloak.keycloak-core"/>
               <module name="org.keycloak.keycloak-services"/>
               <module name="org.keycloak.keycloak-model-jpa"/>
               <module name="org.keycloak.keycloak-server-spi"/>
               <module name="org.keycloak.keycloak-server-spi-private"/>
               <module name="org.keycloak.keycloak-model-jpa"/>
               <module name="javax.ws.rs.api"/>
               <module name="javax.persistence.api"/>
               <module name="org.hibernate"/>
               <module name="org.javassist"/>
               <module name="org.slf4j"/>
               <module name="org.apache.commons.collections"/>
               <module name="org.apache.commons.lang3"/>
            </dependencies>
         </module>
      ```
      * How to determine the module name
         * any libraries used should be in modules folder, for example, you can use 
         org.apache.commons.collections.CollectionUtils, but not 
         org.apache.commons.collections4.CollectionUtils. This is because org.apache.commons.collections.CollectionUtils 
         is under modules\system\layers\base\org\apache\commons\collections\main.
         * why module name is org.apache.commons.collections? 
         <br/>
         because the module is under modules\system\layers\base\org\apache\commons\collections, and the module name in module.xml under 
         modules\system\layers\base\org\apache\commons\collections\main is org.apache.commons.collections
   1. Then registering the provider by editing standalone/configuration/standalone.xml and adding the module to the providers element:
      ```
         <providers>
            <provider>module:org.keycloak.examples.domain-extension-example</provider>
         </providers>
      ```
      
##7. Keycloak configuration 
###7.1 Realm Setting
1. Security Defenses
   1. Brute Force Detection
      1. Enabled: ON
1. Tokens
   1. Access Token Lifespan: Max time before an access token is expired.
   <br/>
   Note: This will affect *exp* & *iat* values inside token, and *expires_in* (seconds) in response.

###7.2 Client Setting
1. Access Type: confidential
1. Authorization Enabled: ON
1. Service Accounts Enabled: ON
   * With above settings, you call get client access token
   ```
      POST: http://{{keycloakHost}}:{{keycloakPort}}/auth/realms/demo/protocol/openid-connect/token
      Headers: 
      Authorization : Basic dmFuaWxsYTo3ZGZjODRhMC03ODAzLTRjMjgtODdkYS0wNTc0YmRjOTZhZmU=
      Body:
      x-www-form-urlencoded: 
      key: grant_type, value: client_credentials
   ```
   Note: Authorization in headers is calculated by client Id with client secret with Base64 encoding.
1. Service Account Roles
   <br/>
   Setup roles for selected client. Default include *Realm Roles*, client *account roles*, *selected client roles*. 
   All these select roles will be in client access token. 
   1. select client roles *realm-management -> view-users* to allow selected client can view users.
   1. select client roles *realm-management -> view-realm* to allow selected client can view realm information.
   1. You can select client roles *realm-management -> realm-admin* to allow all!!

###7.3 User Setting
####7.3.1 customized attribute in token
#####7.3.1.1 By User attribute
1. add customized attribute in selected user -> Attributes
   <br/>
   ```key: phone, value: 2222```
1. go to selected client -> Mappers -> create
   <br/>
   ```Name: phone, Mapper Type: User Attribute, User Attribute: phone, Token Claim Name: phone, Claim JSON Type: String```
#####7.3.1.2 By Group attribute
1. new group by Groups -> New
   <br/>
   ```Name: customized attribute```
1. add attributes in selected group Attributes tag
   <br/>
   ```key: locale, value: en```
1. selected user join group
#####~~7.3.1.3 Aggregate & Multivalued Attribute (???)~~
1. Aggregate attribute values: ON, Multivalued: ON
   <br/>
   attribute as list from both group and user
1. Aggregate attribute values: OFF, Multivalued: OFF
   <br/>
   only one value for the attribute, and user level attribute can override group level attribute.