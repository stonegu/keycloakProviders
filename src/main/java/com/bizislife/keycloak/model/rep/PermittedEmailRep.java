package com.bizislife.keycloak.model.rep;

import com.bizislife.keycloak.model.pojo.PermittedEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PermittedEmailRep {
    private String id;
    private String email;
    private String realmId;
    private Date createdTime;

    public PermittedEmailRep(PermittedEmail prospectingEmail) {
        this.id = prospectingEmail.getId();
        this.email = prospectingEmail.getEmail();
        this.realmId = prospectingEmail.getRealmId();
        this.createdTime = new Date(prospectingEmail.getCreatedTimestamp());
    }
}
