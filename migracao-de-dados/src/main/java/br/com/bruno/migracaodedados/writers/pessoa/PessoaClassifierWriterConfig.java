package br.com.bruno.migracaodedados.writers.pessoa;

import br.com.bruno.migracaodedados.models.Pessoa;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PessoaClassifierWriterConfig {

    @Bean
    ClassifierCompositeItemWriter<Pessoa> pessoaClassifierCompositeItemWriter(
            JdbcBatchItemWriter<Pessoa> pessoaJdbcBatchItemWriter,
            FlatFileItemWriter<Pessoa> pessoaFlatFileItemWriter
    ) {
        return new ClassifierCompositeItemWriterBuilder<Pessoa>()
                .classifier(pessoa -> pessoa.idValid() ? pessoaJdbcBatchItemWriter : pessoaFlatFileItemWriter)
                .build();
    }
}
