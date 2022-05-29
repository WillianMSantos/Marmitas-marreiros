package com.delivery.marmitamarreiros.controller;

import com.delivery.marmitamarreiros.model.Cliente;
import com.delivery.marmitamarreiros.repository.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/clientes")
@Api("Api Clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("{id")
    @ApiOperation("Consulta os dados de um Cliente")
    public Cliente getClienteById(@PathVariable Integer id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um Cliente")
    public Cliente save(@RequestBody @Valid Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Exclui um Cliente")
    public void delete(@PathVariable Integer id){
        clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualiza os dados de um Cliente")
    public void update(@PathVariable Integer id, @RequestBody @Valid Cliente cliente){
        clienteRepository.findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clienteRepository.save(cliente);
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }

    @GetMapping
    @ApiOperation("Consulta os Clientes com base em filtros")
    public List<Cliente> find(Cliente filtro){
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Cliente> example = Example.of(filtro, exampleMatcher);
        return clienteRepository.findAll(example);
    }

}