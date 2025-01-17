package com.generation.lojagamer_gen.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.lojagamer_gen.model.CategoriaModel;
import com.generation.lojagamer_gen.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@CrossOrigin( origins ="*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;


	@GetMapping
	public ResponseEntity<List<CategoriaModel>> getAll(){
		return ResponseEntity.ok(categoriaRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaModel> getById(@PathVariable Long id){
		return categoriaRepository.findById(id)
				.map(sc -> ResponseEntity.ok(sc))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}
	@PostMapping
    public ResponseEntity<CategoriaModel> post(@Valid @RequestBody CategoriaModel categoria) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaRepository.save(categoria));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete (@PathVariable Long id) {
		Optional<CategoriaModel> categoria = categoriaRepository.findById(id);

		if (categoria.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		categoriaRepository.deleteById(id);
	}
	
	 @PutMapping("/{id}")
	    public ResponseEntity<CategoriaModel> updateCategoria(@PathVariable Long id, @RequestBody CategoriaModel categorias) {
	        return categoriaRepository.findById(id)
	                .map(sc -> {
	                    sc.setNome(categorias.getNome());
	                    return ResponseEntity.ok(categoriaRepository.save(sc));
	                }).orElse(ResponseEntity.notFound().build());
	 }
}
