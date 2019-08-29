package com.bizislife.keycloak.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PROSPECTING_EMAIL_ENTITY")
@NamedQueries({ @NamedQuery(name = "findPermittedEmailByRealm", query = "from PermittedEmail where realmId = :realmId") })
@Getter
@Setter
@NoArgsConstructor
public class PermittedEmail {
    @Id
    @Column(name = "ID", length = 36)
    private String id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "REALM_ID", nullable = false)
    private String reamId;

    @Column(name = "CREATED_TIMESTAMP", nullable = false)
    private Long createdTimestamp;


}
