package br.com.fiap.tapago.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.tapago.model.Categoria;
import br.com.fiap.tapago.model.Movimentacao;
import br.com.fiap.tapago.repository.CategoriaRepository;
import br.com.fiap.tapago.repository.MovimentacaoRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner{

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    MovimentacaoRepository movimentacaoRepository;

    @Override
    public void run(String... args) throws Exception {
        categoriaRepository.saveAll(List.of(
            Categoria.builder().id(1L).nome("transporte").icone("bus").build(),
            Categoria.builder().id(2L).nome("lazer").icone("apple").build(),
            Categoria.builder().id(3L).nome("educação").icone("book").build(),
            Categoria.builder().id(4L).nome("alimentação").icone("apple").build()
        ));

        movimentacaoRepository.saveAll(
            List.of(
                Movimentacao.builder()
                    .id(1L)
                    .descricao("Mc Donalds")
                    .valor(new BigDecimal(55))
                    .data(LocalDate.now())
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(4L).get())
                    .build(),
                Movimentacao.builder()
                    .id(2L)
                    .descricao("Livros")
                    .valor(new BigDecimal(100))
                    .data(LocalDate.now().minusMonths(1))
                    .tipo("ENTRADA")
                    .categoria(categoriaRepository.findById(3L).get())
                    .build(),
                Movimentacao.builder()
                    .id(3L)
                    .descricao("Cinema")
                    .valor(new BigDecimal(200))
                    .data(LocalDate.now().minusWeeks(1))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                Movimentacao.builder()
                    .id(4L)
                    .descricao("Futebol")
                    .valor(new BigDecimal(10))
                    .data(LocalDate.now().minusWeeks(1))
                    .tipo("ENTRADA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                Movimentacao.builder()
                    .id(5L)
                    .descricao("Agua")
                    .valor(new BigDecimal(200))
                    .data(LocalDate.now().minusWeeks(1))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                Movimentacao.builder()
                    .id(6L)
                    .descricao("Luz")
                    .valor(new BigDecimal(180))
                    .data(LocalDate.now().minusWeeks(1))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                Movimentacao.builder()
                    .id(7L)
                    .descricao("Netflix")
                    .valor(new BigDecimal(200))
                    .data(LocalDate.now().minusWeeks(1))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                Movimentacao.builder()
                    .id(8L)
                    .descricao("Aluguel")
                    .valor(new BigDecimal(200))
                    .data(LocalDate.now().minusWeeks(1))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                Movimentacao.builder()
                    .id(9L)
                    .descricao("Salario")
                    .valor(new BigDecimal(500))
                    .data(LocalDate.now().minusMonths(2))
                    .tipo("ENTRADA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                Movimentacao.builder()
                    .id(10L)
                    .descricao("Uber")
                    .valor(new BigDecimal(150))
                    .data(LocalDate.now().minusMonths(2))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                    Movimentacao.builder()
                    .id(11L)
                    .descricao("Salario")
                    .valor(new BigDecimal(400))
                    .data(LocalDate.now().minusMonths(3))
                    .tipo("ENTRADA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                Movimentacao.builder()
                    .id(12L)
                    .descricao("Uber")
                    .valor(new BigDecimal(25))
                    .data(LocalDate.now().minusMonths(3))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build()

            )
            
        );
        
    }
    
}
