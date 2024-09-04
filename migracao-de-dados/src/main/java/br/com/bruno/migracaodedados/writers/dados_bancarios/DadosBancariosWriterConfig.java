package br.com.bruno.migracaodedados.writers.dados_bancarios;

import br.com.bruno.migracaodedados.models.DadosBancarios;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DadosBancariosWriterConfig {

    @Bean
    JdbcBatchItemWriter<DadosBancarios> dadosBancariosJdbcBatchItemWriter(
            @Qualifier("appDataSource") DataSource dataSource
    ) {
        return new JdbcBatchItemWriterBuilder<DadosBancarios>()
                .dataSource(dataSource)
                .sql("INSERT INTO dados_bancarios (id, pessoa_id, agencia, conta, banco) VALUES (:id, :pessoaId, :agencia, :conta, :banco)")
                .beanMapped()
                .build();
    }
}
