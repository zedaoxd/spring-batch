package br.com.bruno.envioemailsbatch.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IntereseProdutoCliente {
    private Cliente cliente;
    private Produto produto;
}
