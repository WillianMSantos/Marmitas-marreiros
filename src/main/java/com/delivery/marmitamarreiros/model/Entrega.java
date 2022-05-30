package com.delivery.marmitamarreiros.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "entrega")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome_entregador")
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nomeEntregador;

    @Column(name = "valor_entrega")
    @NotEmpty
    private BigDecimal taxaEntrega;

    @JsonIgnore
    @OneToOne
    private Pedido pedidos;

}
