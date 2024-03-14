package br.com.fiap.tapago.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.tapago.model.Categoria;
import br.com.fiap.tapago.repository.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("categoria")
@Slf4j
public class CategoriaController {

    @Autowired // Injeção de Dependência - Inversão de Controle
    CategoriaRepository repository;

    @GetMapping
    public List<Categoria> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
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

    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        log.info("apagando categoria {}", id);

        repository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                                        NOT_FOUND, 
                                        "Não existe categoria com o id informado"
                                    ));

        repository.deleteById(id);
        return ResponseEntity.noContent().build();

        // var categoriaEncontrada = repository.findById(id);

        // if (categoriaEncontrada.isEmpty())
        //     return ResponseEntity.notFound().build();

        // repository.delete(categoriaEncontrada.get());

        // return ResponseEntity.noContent().build();
    }


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
