package com.delivery.marmitamarreiros.controller;

import com.delivery.marmitamarreiros.dto.request.EntregaDto;
import com.delivery.marmitamarreiros.model.Cliente;
import com.delivery.marmitamarreiros.model.Entrega;
import com.delivery.marmitamarreiros.repository.EntregaRepository;
import com.delivery.marmitamarreiros.service.EntregaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/entregas")
public class EntregaController {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private EntregaService entregaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva uma Entrega")
    public Integer salvaEntrega(@RequestBody @Valid EntregaDto entregaDto){
        Entrega entrega = entregaService.salvarEntrega(entregaDto);

        return entrega.getId();
    }

    @GetMapping
    @ApiOperation("Consulta os dados de um Cliente")
    public Entrega getClienteById(@PathVariable Integer id) {
        return entregaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Entrega não localizada"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Exclui uma entrega")
    public void delete(@PathVariable Integer id){
        entregaRepository.findById(id)
                .map(entrega -> {
                    entregaRepository.delete(entrega);
                    return entrega;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Entrega não encontrada"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualiza os dados de uma entrega")
    public void update(@PathVariable Integer id, @RequestBody @Valid Entrega entrega) {
        entregaRepository.findById(id)
                .map(clienteExistente -> {
                    entrega.setId(clienteExistente.getId());
                    entregaRepository.save(entrega);
                    return entrega;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Entrega não encontrada"));
    }

    @GetMapping
    @ApiOperation("Consulta as entregas com base em filtros")
    public List<Entrega> find(Entrega filtro) {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Entrega> example = Example.of(filtro, exampleMatcher);
        return entregaRepository.findAll(example);
    }
}
