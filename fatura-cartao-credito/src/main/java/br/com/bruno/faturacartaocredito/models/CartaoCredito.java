package br.com.bruno.faturacartaocredito.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartaoCredito {
    private Long numeroCartao;
    private Cliente cliente;
}
