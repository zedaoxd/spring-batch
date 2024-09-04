package br.com.bruno.migracaodedados.steps;

import br.com.bruno.migracaodedados.models.Pessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class MigrarPessoasStepConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    Step migrarPessoasStep(
            ItemReader<Pessoa> reader,
            ClassifierCompositeItemWriter<Pessoa> writer,
            FlatFileItemWriter<Pessoa> pessoaInvalidaWriter
    ) {
        return new StepBuilder("migrarPessoasStep", jobRepository)
                .<Pessoa, Pessoa>chunk(10_000, transactionManager)
                .reader(reader)
                .writer(writer)
                .stream(pessoaInvalidaWriter)
                .build();
    }
}
