package br.com.fiap.tapago.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tapago.model.Movimentacao;
import br.com.fiap.tapago.repository.MovimentacaoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("movimentacao")
public class MovimentacaoController {

    record TotalPorCategoria (String categoria, BigDecimal valor){}
    record TotalPorMes (String mes, BigDecimal receita, BigDecimal despesa){}

    @Autowired
    MovimentacaoRepository repository;

    @GetMapping
    public Page<Movimentacao> index(
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) Integer mes,
        @PageableDefault(sort = "data", direction = Direction.DESC) Pageable pageable

    ){

        if (mes != null && categoria != null){
            return repository.findByCategoriaNomeAndMes(categoria, mes, pageable);
        }

        if (mes != null){
            return repository.findByMes(mes, pageable);
        }

        if (categoria != null){
            return repository.findByCategoriaNomeIgnoreCase(categoria, pageable);
        }
        
        return repository.findAll(pageable);
    }

    @GetMapping("maior")
    public Movimentacao getMaior(
        @PageableDefault(sort = "valor", direction = Direction.DESC, size = 1) Pageable pageable
    ){
        return repository.findAll(pageable).getContent().get(0);
    }

    @GetMapping("ultima")
    public Movimentacao getUltima(){   
        var pageable = PageRequest.of(0, 1, Direction.DESC, "data");
        return repository.findAll(pageable).getContent().get(0);
    }

    @GetMapping("menor")
    public Movimentacao getMenor(){   
        return repository.findFirstByOrderByValor();
    }

    @GetMapping("totais-por-categoria")
    public List<TotalPorCategoria> getTotaisPorCategoria(){
        List<Movimentacao> movimentacoes = repository.findAll();
        Map<String, BigDecimal> collect = movimentacoes.stream()
            .collect(
                Collectors.groupingBy(
                    m -> m.getCategoria().getNome(),
                    Collectors.reducing(BigDecimal.ZERO, Movimentacao::getValor, BigDecimal::add)
                )
            );
        
        return collect.entrySet()
            .stream()
            .map( e -> new TotalPorCategoria(e.getKey(), e.getValue()) )
            .toList();

    }

    @GetMapping("totais-por-mes")
    public List<TotalPorMes> getTotaisPorMes(){
        var movimentacoes = repository.findAll(Sort.by("data"));


        Map<String, BigDecimal> totaisEntradas = movimentacoes.stream()
            .filter( m -> m.getTipo().equals("ENTRADA"))
            .collect(
                Collectors.groupingBy(
                    m -> m.getData().getMonth().toString(),
                    Collectors.reducing( BigDecimal.ZERO, Movimentacao::getValor, BigDecimal::add)
                )
            );

        Map<String, BigDecimal> totaisSaidas = movimentacoes.stream()
            .filter( m -> m.getTipo().equals("SAIDA"))
            .collect(
                Collectors.groupingBy(
                    m -> m.getData().getMonth().toString(),
                    Collectors.reducing( BigDecimal.ZERO, Movimentacao::getValor, BigDecimal::add)
                )
            );

        return totaisSaidas.keySet().stream()
            .map( mes -> new TotalPorMes(
                mes, 
                totaisEntradas.getOrDefault(mes, BigDecimal.ZERO), 
                totaisSaidas.getOrDefault(mes, BigDecimal.ZERO)
            ))
        .toList();
            
            

    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Movimentacao create(@RequestBody @Valid Movimentacao movimentacao){
        return repository.save(movimentacao);
    }
    
}
