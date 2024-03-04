package br.com.fiap.tapago.model;

import java.util.Random;

public record Categoria(Long id, String nome, String icone) {
    public Categoria(Long id, String nome, String icone){
        this.id = Math.abs( new Random().nextLong() );
        this.nome = nome;
        this.icone = icone;
    }
}
// imutável

    