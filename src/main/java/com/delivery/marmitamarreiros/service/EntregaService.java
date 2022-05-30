package com.delivery.marmitamarreiros.service;

import com.delivery.marmitamarreiros.dto.request.EntregaDto;
import com.delivery.marmitamarreiros.exception.RegraNegocioException;
import com.delivery.marmitamarreiros.model.Entrega;
import com.delivery.marmitamarreiros.model.Pedido;
import com.delivery.marmitamarreiros.repository.EntregaRepository;
import com.delivery.marmitamarreiros.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntregaService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EntregaRepository entregaRepository;


    @Transactional
    public Entrega salvarEntrega(EntregaDto entregaDto) {

        Integer idPedido = entregaDto.getPedido();
        Pedido pedido = pedidoRepository.findbyIdFetchItens(idPedido)
                .orElseThrow(()-> new RegraNegocioException("Codigo do pedido invalido" + idPedido));

        Entrega entrega = new Entrega();
        entrega.setNomeEntregador(entregaDto.getNomeEntregador());
        entrega.setPedidos(pedido);

        entregaRepository.save(entrega);

        return entrega;
    }

}
