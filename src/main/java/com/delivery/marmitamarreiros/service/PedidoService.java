package com.delivery.marmitamarreiros.service;

import com.delivery.marmitamarreiros.dto.request.PedidoRequestDto;
import com.delivery.marmitamarreiros.model.Pedido;
import com.delivery.marmitamarreiros.model.StatusPedido;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoRequestDto dto);

    Optional<Pedido> obterPedidoCompelto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
