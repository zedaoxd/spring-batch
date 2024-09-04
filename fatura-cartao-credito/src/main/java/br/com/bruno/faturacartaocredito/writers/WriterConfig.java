package br.com.bruno.faturacartaocredito.writers;

import br.com.bruno.faturacartaocredito.models.FaturaCartao;
import br.com.bruno.faturacartaocredito.models.Transacao;
import br.com.bruno.faturacartaocredito.utils.CustomFormater;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class WriterConfig {

    @Bean
    MultiResourceItemWriter<FaturaCartao> faturaCartaoMultiResourceItemWriter(FooterWrite footerWrite) {
        return new MultiResourceItemWriterBuilder<FaturaCartao>()
                .name("faturaCartaoMultiResourceItemWriter")
                .resource(new FileSystemResource("files/fatura"))
                .itemCountLimitPerResource(1)
                .resourceSuffixCreator(index -> String.format("_%d.txt", index))
                .delegate(faturaCartao(footerWrite))
                .build();
    }

    private FlatFileItemWriter<FaturaCartao> faturaCartao(FooterWrite footerWrite) {
        return new FlatFileItemWriterBuilder<FaturaCartao>()
                .name("faturaCartao")
                .resource(new FileSystemResource("files/fatura.txt"))
                .headerCallback(writer -> {
                    writer.append(String.format("%121s\n", "Cartão XPTO"));
                    writer.append(String.format("%121s\n\n", "Rua xyz, 131"));
                })
                .lineAggregator(item -> {
                    StringBuilder strBuilder = new StringBuilder();
                    strBuilder.append(String.format("Nome: %s\n", item.getCliente().getNome()));
                    strBuilder.append(String.format("Endereço: %s\n\n\n", item.getCliente().getEndereco()));
                    strBuilder.append(String.format("Fatura completa do cartão %d\n", item.getCartaoCredito().getNumeroCartao()));
                    strBuilder.append("------------------------------------------------------------------------------------------------------------------------\n");
                    strBuilder.append("DATA DESCRIÇÃO VALOR\n");
                    strBuilder.append("------------------------------------------------------------------------------------------------------------------------\n");

                    for (Transacao transacao : item.getTransacoes()) {
                        strBuilder.append(String.format("\n[%10s] %-80s - %s",
                                CustomFormater.formatDate(transacao.getData()),
                                transacao.getDescricao(),
                                CustomFormater.formatNumber(transacao.getValor())));
                    }

                    return strBuilder.toString();
                })
                .footerCallback(footerWrite)
                .build();
    }
}
