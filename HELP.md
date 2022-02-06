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
      * customized attribute in access token
         * By Client Scopes
         * By User customized attribute
         * By Group customized attribute
         * Aggregate & Multivalued Attribute
   * keycloak.json
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
      * check the description here: [Keycloak MySQL Setup](https://github.com/Codingpedia/codingmarks-api/wiki/Keycloak-MySQL-Setup)
   1. run keycloak-database-update.sql
   1. make sure connection-url in standalone.xml for characterEncoding is 'UTF-8'
   
##4. modify xms and xmx in standalone.conf.bat
```set "JAVA_OPTS=-Xms512M -Xmx2048M -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m"``` 
<br/>
NOTE: initial admin account is : admin : admin

##5. SMTP Server
```bizislife2019@gmail.com : stone0331```
###5.1 Intro
Keycloak requires email configuration to
* verify email address of user
* allow user to set own password
###5.2 Assign email address to admin account
Use Keycloak Account Management to add email address in Personal Info

The below steps work for Keycloak 13 but UI may change with time
* Login to Keycloak Security Admin Console using admin credentials
* Click admin name shown in the top right corner
* Click Manage account
* Click Personal Info
* Enter email address
###5.3 Configure Email Settings
1. Open a realm
2. Under Realm Settings > Email the following details will work for a Gmail account
   * Host: smtp.gmail.com
   * Port: 587 (for SSL, use 465)
   * From: admin-email-address 
   * Enable StartTLS: On (for SSL, use Enable SSL)
   * Enable Authentication: On 
   * Username: username 
   * Password: password
###5.4 Configure Gmail
If the admin account is a Gmail account, the below steps are required

* Login to the Gmail account in a browser 
* Visit https://www.google.com/settings/security/lesssecureapps
  * Change the setting to On
* Visit https://accounts.google.com/DisplayUnlockCaptcha
  * Follow on-screen instructions, if any

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
1. Authorization Enabled: OFF
   * OFF: will close Authorization tab on the top, and will remove ```"policy-enforcer":{}``` from *keycloak.json*
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
1. Service Account Roles Tab
   <br/>
   Setup roles for selected client. Default include *Realm Roles*, client *account roles*, *selected client roles*. 
   All these select roles will be in client access token. 
   1. select client roles *realm-management -> view-users* to allow selected client can view users.
   1. select client roles *realm-management -> view-realm* to allow selected client can view realm information.
   1. You can select client roles *realm-management -> realm-admin* to allow all!!

####7.2.1 Audience
With recent keycloak version 4.6.0 the client id is apparently no longer automatically added to the audience field 'aud' 
of the access token. Therefore even though the login succeeds the client rejects the user. To fix this you need to 
configure the audience for your clients.

Next example for Keycloak version 11.0.2

#####7.2.1.1 add from Client Scopes
1. create a new scope like *customized_attr*
1. create a mapper in *customized_attr*
   <br/>
   ```Name: audience, Mapper Type: Audience, Included Client Audience: your_client, Add to access token: ON```
1. goto selected client, click Client Scopes tab, and add *customized_attr* in to Assigned Default Client Scopes.

#####7.2.1.2 add from selected client *Mappers* tab
1. goto selected client, click Mappers, create new mapper 
<br/>
```Name: audience, Mapper Type: Audience, Included Client Audience: selected_client```
<br/>
Note: I prefer this way for audience setup, because this map is specific for the selected client.

####7.2.2 Keycloak client mapper to show the realm name
* Select your realm;
* Go to clients;
* Select the client that you are going to request the token against;
* Go to Mappers;
* Click Create
* In Mapper type select Hardcoded claim;
* Token Claim Name : realm name
* Claim value : the name of the realm.
* Fill up the rest accordingly.

###7.3 User Setting
####7.3.1 customized attribute in access token
#####7.3.1.1 By Client Scopes
1. Select Client Scopes in left menu, and you find / create client scope, for example you select *profile*.
1. You will find predefined *locale* in Mappers tab.
1. Goto selected client, and Client Scopes tab, make sure *profile* is assigned.
1. You now can goto selected user, and add *locale* in Attributes tab, and set value for this attribute.
#####7.3.1.2 By User customized attribute
1. add customized attribute in selected user -> Attributes
   <br/>
   ```key: locale1, value: fr```
1. go to selected client -> Mappers -> create
   <br/>
   ```Name: locale1, Mapper Type: User Attribute, User Attribute: locale1, Token Claim Name: locale1, Claim JSON Type: String```
#####7.3.1.3 By Group customized attribute
1. new group by Groups -> New
   <br/>
   ```Name: customized attribute```
1. add attributes in selected group Attributes tab
   <br/>
   ```key: locale1, value: en```
1. selected user join group
#####7.3.1.4 Aggregate & Multivalued Attribute
1. Aggregate attribute values: ON, Multivalued: ON
   <br/>
   attribute as list from both group and user
1. Aggregate attribute values: OFF, Multivalued: OFF
   <br/>
   only one value for the attribute, and user level attribute can override group level attribute.
   
###7.4 keycloak.json
1. use-resource-role-mappings
   <br/>
   If set to true, the adapter will look inside the token for application level role mappings for the user. If false, it 
   will look at the realm level for user role mappings. This is OPTIONAL. The default value is false.
   <br/>
   Example:
   <br/>
   If you have ```antMatchers("/realm/**").hasRole("USER")```, and your keycloak.json has 
   ```"use-resource-role-mappings": true,``` the adapter will look inside the token for application(client) level role 
   mappings for the user. Which means that the user's client roles has *user* role.
   <br/>
   your access token:
   <br/>
   ```
   "resource_access": {
     "vanilla": {
       "roles": [
         "user"
       ]
     }
   },
   ```
   But if ```"use-resource-role-mappings": false,``` the adapter will look inside the token for realm level role 
   mappings for the user. Which means that the user's realm roles has *user* role.
   <br/>
   your access token:
   <br/>
   ```
   "realm_access": {
     "roles": [
       "user"
     ]
   },      
   ```