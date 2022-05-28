package com.delivery.marmitamarreiros.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name ="TB_ROLE")
public class Role {

    @Id
    @Column(name="ROLE_ID")
    private UUID roleId;

    @Column(name = "ROLE")
    private String role;

    public Role() {
    }

}
