package com.delivery.marmitamarreiros.service.impl;

import com.delivery.marmitamarreiros.dto.request.ItemPedidoDto;
import com.delivery.marmitamarreiros.dto.request.PedidoRequestDto;
import com.delivery.marmitamarreiros.exception.ClientInvalidException;
import com.delivery.marmitamarreiros.exception.IdProducInvalidException;
import com.delivery.marmitamarreiros.exception.NotFunoundItensException;
import com.delivery.marmitamarreiros.exception.PedidoNotFoundException;
import com.delivery.marmitamarreiros.model.ItemPedido;
import com.delivery.marmitamarreiros.model.Pedido;
import com.delivery.marmitamarreiros.model.StatusPedido;
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

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService{

    @Autowired
    private final PedidoRepository pedidoRepository;

    @Autowired
    private final ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private final ProdutoRepository produtoRepository;

    @Autowired
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoRequestDto pedidoRequest) {

        Integer idCliente = pedidoRequest.getCliente();
        val cliente = clienteRepository
                                          .findById(idCliente)
                 .orElseThrow(ClientInvalidException::new);

        val pedido = new Pedido().builder()
                .total(pedidoRequest.getTotal())
                .dataPedido(LocalDate.now())
                .cliente(cliente)
                .status(StatusPedido.REALIZADO)
                .build();

        val itensPedido = converterItens(pedido, pedidoRequest.getItens());
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
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {

        pedidoRepository.findById(id).map(pedido -> {
            pedido.setStatus(statusPedido);
            return pedidoRepository.save(pedido);
        }).orElseThrow(PedidoNotFoundException::new);
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDto> itens){
        if(itens.isEmpty()){
            throw new NotFunoundItensException();
        }

        return itens.stream()
                .map(itemPedidoDto -> {

                    val idProduto =  itemPedidoDto.getProduto();

                    val produto = produtoRepository.findById(idProduto)
                            .orElseThrow(IdProducInvalidException::new);

                    val itemPedido = new ItemPedido();

                    itemPedido.setQuantidade(itemPedidoDto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;

                }).collect(Collectors.toList());
    }
}
