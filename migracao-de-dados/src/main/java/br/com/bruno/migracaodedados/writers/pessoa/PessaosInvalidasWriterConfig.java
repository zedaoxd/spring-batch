package br.com.bruno.migracaodedados.writers.pessoa;

import br.com.bruno.migracaodedados.models.Pessoa;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class PessaosInvalidasWriterConfig {

    @Bean
    FlatFileItemWriter<Pessoa> pessoaFlatFileItemWriter() {
        return new FlatFileItemWriterBuilder<Pessoa>()
                .name("pessoaInvalidaWriter")
                .resource(new FileSystemResource("files/pessoas_invalidas.csv"))
                .delimited()
                .names("id")
                .build();
    }
}
