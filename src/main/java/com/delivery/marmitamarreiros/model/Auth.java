package com.delivery.marmitamarreiros.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_USUARIO")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @NotEmpty(message = "{campo.login.obrigatorio}")
    @Column
    private String email;

    @NotEmpty(message = "{campo.senha.obrigatorio}")
    @Column
    private String senha;

    @NotNull
    private boolean admin;

}
