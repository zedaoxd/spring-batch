package br.com.bruno.faturacartaocredito.steps;

import br.com.bruno.faturacartaocredito.models.FaturaCartao;
import br.com.bruno.faturacartaocredito.models.Transacao;
import br.com.bruno.faturacartaocredito.readers.FaturaCartaoReader;
import br.com.bruno.faturacartaocredito.writers.FooterWrite;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class StepConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    Step step(
            ItemStreamReader<Transacao> reader,
            ItemProcessor<FaturaCartao, FaturaCartao> processor,
            ItemWriter<FaturaCartao> writer,
            FooterWrite footerWrite
    ) {
        return new StepBuilder("step", jobRepository)
                .<FaturaCartao, FaturaCartao>chunk(1, transactionManager)
                .reader(new FaturaCartaoReader(reader))
                .processor(processor)
                .writer(writer)
                .listener(footerWrite)
                .build();
    }
}
