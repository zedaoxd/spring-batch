package br.com.bruno.faturacartaocredito.readers;

import br.com.bruno.faturacartaocredito.models.FaturaCartao;
import br.com.bruno.faturacartaocredito.models.Transacao;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.*;

@RequiredArgsConstructor
public class FaturaCartaoReader implements ItemStreamReader<FaturaCartao> {
    private final ItemStreamReader<Transacao> delegate;
    private Transacao transacaoAtual;

    @Override
    public FaturaCartao read() throws Exception {
        if (transacaoAtual == null)
            transacaoAtual = delegate.read();

        FaturaCartao faturaCartao = null;
        Transacao transacao = transacaoAtual;
        transacaoAtual = null;

        if (transacao != null) {
            faturaCartao = FaturaCartao.builder()
                    .cartaoCredito(transacao.getCartaoCredito())
                    .cliente(transacao.getCartaoCredito().getCliente())
                    .build();
            faturaCartao.addTransacao(transacao);

            while (isTransacaoRelacionada(transacao))
                faturaCartao.addTransacao(transacaoAtual);
        }

        return faturaCartao;
    }

    private boolean isTransacaoRelacionada(Transacao transacao) throws Exception {
        return peek() != null && transacao.getCartaoCredito().getNumeroCartao().equals(transacaoAtual.getCartaoCredito().getNumeroCartao());
    }

    private Transacao peek() throws Exception {
        transacaoAtual = delegate.read();
        return transacaoAtual;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }
}
