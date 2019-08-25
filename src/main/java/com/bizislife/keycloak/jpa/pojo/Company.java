package com.bizislife.keycloak.jpa.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "COMPANY")
@NamedQueries({ @NamedQuery(name = "findByRealm", query = "from Company where realmId = :realmId") })
@Getter
@Setter
public class Company {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "REALM_ID", nullable = false)
    private String realmId;
    
}
