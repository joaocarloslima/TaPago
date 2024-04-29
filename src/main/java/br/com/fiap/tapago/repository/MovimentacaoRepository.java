package br.com.fiap.tapago.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.tapago.model.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    Page<Movimentacao> findByCategoriaNomeIgnoreCase(String categoria, Pageable pageable);

    //SQL - JPQL - Java Persistence Query Language
    @Query("SELECT m FROM Movimentacao m WHERE MONTH(m.data) = :mes")
    Page<Movimentacao> findByMes(@Param("mes")  Integer mes, Pageable pageable);
    
    @Query("SELECT m FROM Movimentacao m WHERE MONTH(m.data) = :mes AND m.categoria.nome = :categoria")
    Page<Movimentacao> findByCategoriaNomeAndMes(@Param("categoria") String categoria, @Param("mes")  Integer mes, Pageable pageable);

    Movimentacao findFirstByOrderByValor();



    
}
