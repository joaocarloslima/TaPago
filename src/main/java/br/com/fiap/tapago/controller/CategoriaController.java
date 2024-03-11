package br.com.fiap.tapago.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tapago.model.Categoria;
import br.com.fiap.tapago.repository.CategoriaRepository;

@RestController
@RequestMapping("categoria")
public class CategoriaController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired // Injeção de Dependência - Inversão de Controle
    CategoriaRepository repository;

    @GetMapping
    public List<Categoria> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria create(@RequestBody Categoria categoria) {
        log.info("Cadastrando categoria {}", categoria);
        return repository.save(categoria);
    }

    @GetMapping("{id}")
    public ResponseEntity<Categoria> show(@PathVariable Long id) {
        log.info("buscando categoria com id {}", id);

        return repository
            .findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());

    }

    // @DeleteMapping("{id}")
    // public ResponseEntity<Object> destroy(@PathVariable Long id) {
    //     log.info("apagando categoria {}", id);

    //     var categoriaEncontrada = getCategoriaById(id);

    //     if (categoriaEncontrada.isEmpty())
    //         return ResponseEntity.notFound().build();

    //     repository.remove(categoriaEncontrada.get());

    //     return ResponseEntity.noContent().build();
    // }


    // @PutMapping("{id}")
    // public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria categoria){
    //     log.info("atualizar categoria {} para {}", id, categoria);

    //     // buscar a categoria antiga -> 404
    //     var categoriaEncontrada = getCategoriaById(id);

    //     if (categoriaEncontrada.isEmpty())
    //         return ResponseEntity.notFound().build();

    //     var categoriaAntiga = categoriaEncontrada.get();

    //     // criar a categoria nova com os dados atualizados
    //     var categoriaNova = new Categoria(id, categoria.nome(), categoria.icone());

    //     // apagar a categoria antiga
    //     repository.remove(categoriaAntiga);

    //     // add a categoria nova
    //     repository.add(categoriaNova);

    //     return ResponseEntity.ok(categoriaNova);
    // }





    // private Optional<Categoria> getCategoriaById(Long id) {
    //     var categoriaEncontrada = repository
    //             .stream()
    //             .filter(c -> c.id().equals(id))
    //             .findFirst();
    //     return categoriaEncontrada;
    // }

}
