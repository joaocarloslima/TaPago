package br.com.fiap.tapago.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tapago.model.Categoria;

@RestController
@RequestMapping("categoria")
public class CategoriaController {

    Logger log = LoggerFactory.getLogger(getClass());

    List<Categoria> repository = new ArrayList<>();
    
    @GetMapping
    public List<Categoria> index(){
        return repository;
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@RequestBody Categoria categoria){
        log.info("Cadastrando categoria {}", categoria);
        repository.add(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @GetMapping("{id}")
    public ResponseEntity<Categoria> show(@PathVariable Long id){
        log.info("buscando categoria com id {}", id);

        for(Categoria categoria: repository){
            if (categoria.id().equals(id))
                return ResponseEntity.status(HttpStatus.OK).body(categoria);
        }

        //TODO refatorar com stream

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
}
