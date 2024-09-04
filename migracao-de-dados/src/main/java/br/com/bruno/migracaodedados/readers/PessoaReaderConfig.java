package br.com.bruno.migracaodedados.readers;

import br.com.bruno.migracaodedados.models.Pessoa;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.Date;


@Configuration
public class PessoaReaderConfig {

    @Bean
    FlatFileItemReader<Pessoa> pessoaReader() {
        return new FlatFileItemReaderBuilder<Pessoa>()
                .name("pessoaReader")
                .resource(new FileSystemResource("files/pessoas.csv"))
                .delimited()
                .names("nome" ,"email", "dataNascimento", "idade", "id")
                .addComment("--")
                .fieldSetMapper(fieldSet -> Pessoa.builder()
                        .id(fieldSet.readLong("id"))
                        .nome(fieldSet.readRawString("nome"))
                        .email(fieldSet.readString("email"))
                        .idade(fieldSet.readInt("idade"))
                        .dataNascimento(new Date(fieldSet.readDate("dataNascimento", "yyyy-MM-dd").getTime()))
                        .build())
                .build();
    }
}
