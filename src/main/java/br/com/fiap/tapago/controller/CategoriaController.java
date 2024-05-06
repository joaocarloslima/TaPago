package br.com.fiap.tapago.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
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

import br.com.fiap.tapago.model.Categoria;
import br.com.fiap.tapago.repository.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("categoria")
@Slf4j
@CacheConfig(cacheNames = "categorias")
@Tag(name = "categorias")
public class CategoriaController {

    @Autowired // Injeção de Dependência - Inversão de Controle
    CategoriaRepository repository;

    @GetMapping
    @Cacheable
    @Operation(
        summary = "Listar todas as categorias",
        description = "Retorna um array com todas as categorias no formato objeto"
    )
    public List<Categoria> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Cadastrar categoria",
        description = "Cria uma nova categoria com os dados enviados no corpo da requisição."
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201",description = "Categoria cadastrada com sucesso"),
            @ApiResponse(responseCode = "400",description = "Dados enviados são inválidos. Verifique o corpo da requisição", useReturnTypeSchema = false)
        }
    )
    public Categoria create(@RequestBody @Valid Categoria categoria) {
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
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void destroy(@PathVariable Long id) {
        log.info("apagando categoria {}", id);
        verificarSeCategoriaExiste(id);
        repository.deleteById(id);
    }
    
    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    public Categoria update(@PathVariable Long id, @RequestBody Categoria categoria) {
        log.info("atualizar categoria {} para {}", id, categoria);

        verificarSeCategoriaExiste(id);
        categoria.setId(id);
        return repository.save(categoria);
    }

    private void verificarSeCategoriaExiste(Long id) {
        repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "Não existe categoria com o id informado"));
    }

}
