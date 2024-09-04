package br.com.bruno.migracaodedados.steps;

import br.com.bruno.migracaodedados.models.DadosBancarios;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class MigrarDadosBancariosStepConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    Step migrarDadosBancariosStep(
        ItemReader<DadosBancarios> reader,
        ItemWriter<DadosBancarios> writer
    ) {
        return new StepBuilder("migrarDadosBancariosStep", jobRepository)
                .<DadosBancarios, DadosBancarios>chunk(10_000, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }
}
