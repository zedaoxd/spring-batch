package br.com.bruno.migracaodedados.readers;

import br.com.bruno.migracaodedados.models.DadosBancarios;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class DadosBancariosReaderConfig {

    @Bean
    FlatFileItemReader<DadosBancarios> dadosBancariosFlatFileItemReader() {
        return new FlatFileItemReaderBuilder<DadosBancarios>()
                .name("dadosBancariosFlatFileItemReader")
                .resource(new FileSystemResource("files/dados_bancarios.csv"))
                .delimited()
                .names("pessoaId", "agencia", "conta", "banco", "id")
                .addComment("--")
                .targetType(DadosBancarios.class)
                .build();
    }
}
