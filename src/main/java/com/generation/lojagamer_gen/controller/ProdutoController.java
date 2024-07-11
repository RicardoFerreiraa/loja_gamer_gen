package com.generation.lojagamer_gen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.generation.lojagamer_gen.model.ProdutoModel;
import com.generation.lojagamer_gen.repository.CategoriaRepository;
import com.generation.lojagamer_gen.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<ProdutoModel> getAllProdutos() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModel> getProdutoById(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProdutoModel createProduto(@RequestBody ProdutoModel produto) {
        return produtoRepository.save(produto);
    }

    @PutMapping
    public ResponseEntity<ProdutoModel> updateProduto(@Valid @RequestBody ProdutoModel produto) {
        if (produtoRepository.existsById(produto.getId())) {
            if (categoriaRepository.existsById(produto.getCategoria().getId())) {
                return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produtoRepository.delete(produto);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
