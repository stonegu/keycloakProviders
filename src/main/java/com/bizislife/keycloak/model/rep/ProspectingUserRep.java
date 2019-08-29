package com.bizislife.keycloak.model.rep;

import com.bizislife.keycloak.model.common.UserStatus;
import com.bizislife.keycloak.model.pojo.ProspectingUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ProspectingUserRep {
    private String id;
    private String email;
    private String realmId;
    private Date createdTime;
    private UserStatus status;

    public ProspectingUserRep(ProspectingUser prospectingUser) {
        this.id = prospectingUser.getId();
        this.email = prospectingUser.getEmail();
        this.realmId = prospectingUser.getReamId();
        this.createdTime = new Date(prospectingUser.getCreatedTimestamp());
        this.status = UserStatus.fromCode(prospectingUser.getStatus());
    }
}
