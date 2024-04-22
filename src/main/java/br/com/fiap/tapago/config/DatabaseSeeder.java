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
        categoriaRepository.saveAll(
            List.of(
                Categoria.builder().id(1L).nome("educação").icone("graduation-cap").build(),
                Categoria.builder().id(2L).nome("transporte").icone("bus").build(),
                Categoria.builder().id(3L).nome("alimentação").icone("apple").build()
            )
        );

        movimentacaoRepository.saveAll(
            List.of (
                Movimentacao.builder()
                    .id(1L)
                    .descricao("Mc Donalds")
                    .valor(new BigDecimal(55))
                    .data(LocalDate.now().minusWeeks(2))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(3L).get())
                    .build(),
                Movimentacao.builder()
                    .id(2L)
                    .descricao("Livro")
                    .valor(new BigDecimal(100))
                    .data(LocalDate.now().minusDays(1))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(1L).get())
                    .build(),
                Movimentacao.builder()
                    .id(3L)
                    .descricao("Bilhete Único")
                    .valor(new BigDecimal(150))
                    .data(LocalDate.now().minusMonths(1))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                Movimentacao.builder()
                    .id(4L)
                    .descricao("Bilhete Único")
                    .valor(new BigDecimal(150))
                    .data(LocalDate.now().minusMonths(1))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                Movimentacao.builder()
                    .id(5L)
                    .descricao("Mercado")
                    .valor(new BigDecimal(200))
                    .data(LocalDate.now())
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                Movimentacao.builder()
                    .id(6L)
                    .descricao("Bilhete Único")
                    .valor(new BigDecimal(150))
                    .data(LocalDate.now().minusMonths(1))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                Movimentacao.builder()
                    .id(7L)
                    .descricao("Bilhete Único")
                    .valor(new BigDecimal(150))
                    .data(LocalDate.now().minusMonths(1))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build(),
                Movimentacao.builder()
                    .id(8L)
                    .descricao("Bilhete Único")
                    .valor(new BigDecimal(150))
                    .data(LocalDate.now().minusMonths(1))
                    .tipo("SAIDA")
                    .categoria(categoriaRepository.findById(2L).get())
                    .build()

            )
        );
        
    }
    
}
