package com.delivery.marmitamarreiros.dto.request;

import com.delivery.marmitamarreiros.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntregaDto {

    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nomeEntregador;

    @NotEmpty
    private BigDecimal valorEntrega;

    @NotEmpty
    private Integer pedido;
}
