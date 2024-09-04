package br.com.bruno.migracaodedados.writers.pessoa;

import br.com.bruno.migracaodedados.models.Pessoa;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Date;

@Configuration
public class PessoaWriterConfig {

    @Bean
    JdbcBatchItemWriter<Pessoa> pessoaJdbcBatchItemWriter(
            @Qualifier("appDataSource") DataSource dataSource
    ) {
        return new JdbcBatchItemWriterBuilder<Pessoa>()
                .dataSource(dataSource)
                .sql("INSERT INTO pessoa (id, nome, email, data_nascimento, idade) VALUES (?, ?, ?, ?, ?)")
                .itemPreparedStatementSetter((item, ps) -> {
                    ps.setLong(1, item.getId());
                    ps.setString(2, item.getNome());
                    ps.setString(3, item.getEmail());
                    ps.setDate(4, new Date(item.getDataNascimento().getTime()));
                    ps.setInt(5, item.getIdade());
                })
                .build();
    }
}
