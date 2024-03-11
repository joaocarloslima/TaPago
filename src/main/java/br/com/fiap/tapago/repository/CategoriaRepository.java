package br.com.fiap.tapago.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.tapago.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
