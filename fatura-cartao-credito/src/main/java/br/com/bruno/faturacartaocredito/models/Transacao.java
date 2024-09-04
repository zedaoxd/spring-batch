package br.com.bruno.faturacartaocredito.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class Transacao {
    private Long id;
    private CartaoCredito cartaoCredito;
    private String descricao;
    private BigDecimal valor;
    private Date data;
}
