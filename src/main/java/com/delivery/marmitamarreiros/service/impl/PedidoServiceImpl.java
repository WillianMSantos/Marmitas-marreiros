package com.delivery.marmitamarreiros.service.impl;

import com.delivery.marmitamarreiros.dto.request.ItemPedidoDto;
import com.delivery.marmitamarreiros.dto.request.PedidoRequestDto;

import com.delivery.marmitamarreiros.exception.PedidoNaoEncontradoException;
import com.delivery.marmitamarreiros.exception.RegraNegocioException;
import com.delivery.marmitamarreiros.model.*;
import com.delivery.marmitamarreiros.repository.ClienteRepository;
import com.delivery.marmitamarreiros.repository.ItemPedidoRepository;
import com.delivery.marmitamarreiros.repository.PedidoRepository;
import com.delivery.marmitamarreiros.repository.ProdutoRepository;
import com.delivery.marmitamarreiros.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService{

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoRequestDto dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido: " + idCliente));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompelto(Integer id) {
        return pedidoRepository.findbyIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidoRepository.findById(id).map(pedido -> {
            pedido.setStatus(statusPedido);
            return pedidoRepository.save(pedido);
        }).orElseThrow(PedidoNaoEncontradoException::new);
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDto> itens) {
        if(itens.isEmpty()) {
            throw new RegraNegocioException("Não há itens adicionado ao pedido.");
        }

        return itens.stream()
                .map(dto -> {
                    Integer idProduto =  dto.getProduto();
                    Produto produto = produtoRepository.findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
