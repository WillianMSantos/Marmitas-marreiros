package com.delivery.marmitamarreiros.controller;

import com.delivery.marmitamarreiros.model.Produto;
import com.delivery.marmitamarreiros.repository.ProdutoRepository;
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
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("{id}")
    @ApiOperation("Consulta um Produto")
    public Produto getClienteById(@PathVariable Integer id){
        return produtoRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um Produto")
    public Produto save(@RequestBody @Valid Produto produto){
        return produtoRepository.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Exclui um Produto")
    public void delete(@PathVariable Integer id){
        produtoRepository.findById(id).
                map(produto -> {produtoRepository.delete(produto); return Void.TYPE;})
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado"));
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um Produto")
    public void update(@PathVariable Integer id, @RequestBody @Valid Produto produto){
        produtoRepository.findById(id)
                .map(p -> {
                    produto.setId(p.getId());
                    produtoRepository.save(produto);
                    return produto;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado"));
    }

    @GetMapping
    @ApiOperation("Consulta os Produtos com base em filtros")
    public List<Produto> find(Produto filtro){
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Produto> example = Example.of(filtro, exampleMatcher);
        return produtoRepository.findAll(example);
    }
}