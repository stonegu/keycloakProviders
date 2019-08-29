package com.bizislife.keycloak.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PROSPECTING_USER_ENTITY")
@NamedQueries({ @NamedQuery(name = "findProspectingByRealm", query = "from ProspectingUser where realmId = :realmId") })
@Getter
@Setter
@NoArgsConstructor
public class ProspectingUser {
    @Id
    @Column(name = "ID", length = 36)
    private String id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "REALM_ID", nullable = false)
    private String reamId;

    @Column(name = "CREATED_TIMESTAMP", nullable = false)
    private Long createdTimestamp;

    @Column(name = "STATUS", nullable = false)
    private String status;


}
