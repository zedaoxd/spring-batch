package br.com.bruno.envioemailsbatch.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Produto {

    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
}
