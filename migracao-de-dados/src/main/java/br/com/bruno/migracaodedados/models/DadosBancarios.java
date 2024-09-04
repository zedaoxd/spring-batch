package br.com.bruno.migracaodedados.models;

import lombok.Data;

@Data
public class DadosBancarios {
    private Long id;
    private Long pessoaId;
    private Long agencia;
    private Long conta;
    private Long banco;
}
