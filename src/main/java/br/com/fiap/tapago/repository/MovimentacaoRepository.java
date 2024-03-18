package br.com.fiap.tapago.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.tapago.model.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    
}
