package br.com.bruno.faturacartaocredito.writers;

import br.com.bruno.faturacartaocredito.models.FaturaCartao;
import br.com.bruno.faturacartaocredito.utils.CustomFormater;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;

@Component
public class FooterWrite implements FlatFileFooterCallback {
    private BigDecimal total = BigDecimal.ZERO;

    @Override
    public void writeFooter(Writer writer) throws IOException {
        writer.append(String.format("\n%121s", "Total: " + CustomFormater.formatNumber(total)));
    }

    @BeforeWrite
    public void beforeWrite(Chunk<FaturaCartao> faturas) {
        for (FaturaCartao fatura : faturas) {
            total = total.add(fatura.getTotal());
        }
    }

    @BeforeRead
    public void beforeRead() {
        total = BigDecimal.ZERO;
    }
}
