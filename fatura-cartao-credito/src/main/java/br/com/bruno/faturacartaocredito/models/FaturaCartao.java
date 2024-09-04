package br.com.bruno.faturacartaocredito.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class FaturaCartao {
    private Cliente cliente;
    private CartaoCredito cartaoCredito;
    private List<Transacao> transacoes = new ArrayList<>();

    public void addTransacao(Transacao t) {
        if (transacoes == null)
            transacoes = new ArrayList<>();

        transacoes.add(t);
    }

    public BigDecimal getTotal() {
        return transacoes.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
