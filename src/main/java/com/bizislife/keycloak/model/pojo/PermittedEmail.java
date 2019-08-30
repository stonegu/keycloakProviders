package com.bizislife.keycloak.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static com.bizislife.keycloak.model.pojo.PermittedEmail.FIND_PERMITTED_EMAIL_BY_REALM;

@Entity
@Table(name = "PROSPECTING_EMAIL_ENTITY")
@NamedQueries({ @NamedQuery(name = FIND_PERMITTED_EMAIL_BY_REALM, query = "from PermittedEmail where realmId = :realmId") })
@Getter
@Setter
@NoArgsConstructor
public class PermittedEmail {
    public static final String FIND_PERMITTED_EMAIL_BY_REALM = "findPermittedEmailByRealm";

    @Id
    @Column(name = "ID", length = 36)
    private String id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "REALM_ID", nullable = false)
    private String realmId;

    @Column(name = "CREATED_TIMESTAMP", nullable = false)
    private Long createdTimestamp;


}
