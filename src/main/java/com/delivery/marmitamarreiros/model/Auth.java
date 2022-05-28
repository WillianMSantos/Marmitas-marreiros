package com.delivery.marmitamarreiros.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USUARIO")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_USER")
    @SequenceGenerator(name = "ID_USER", sequenceName = "TB_USER", allocationSize = 1)
    @Column(name = "ID_USER")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany
    private List<Role> roles;
}
