package com.delivery.marmitamarreiros.controller;

import com.delivery.marmitamarreiros.dto.request.PedidoRequestDto;
import com.delivery.marmitamarreiros.dto.request.StatusPedidoDto;
import com.delivery.marmitamarreiros.dto.response.InformacaoItemPedidoDto;
import com.delivery.marmitamarreiros.dto.response.InformacoesPedidoDto;
import com.delivery.marmitamarreiros.model.ItemPedido;
import com.delivery.marmitamarreiros.model.Pedido;
import com.delivery.marmitamarreiros.model.StatusPedido;
import com.delivery.marmitamarreiros.service.PedidoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um Pedido")
    public Integer save(@RequestBody @Valid PedidoRequestDto dto){
        Pedido pedido = pedidoService.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    @ApiOperation("Consulta um Pedido")
    public InformacoesPedidoDto getById(@PathVariable Integer id){
        return pedidoService.obterPedidoCompelto(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualiza o status de um Pedido")
    public void updateStatus(@PathVariable Integer id, @RequestBody StatusPedidoDto dto){
        String novoStatus = dto.getAtualizaStatus();
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(novoStatus));

    }

    private InformacoesPedidoDto converter(Pedido pedido){
        return InformacoesPedidoDto.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDto> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens.stream()
                .map(item -> InformacaoItemPedidoDto.builder()
                        .descricao(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()).collect(Collectors.toList());
    }
}