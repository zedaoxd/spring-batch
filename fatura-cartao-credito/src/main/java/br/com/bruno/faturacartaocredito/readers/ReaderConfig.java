package br.com.bruno.faturacartaocredito.readers;

import br.com.bruno.faturacartaocredito.models.CartaoCredito;
import br.com.bruno.faturacartaocredito.models.Cliente;
import br.com.bruno.faturacartaocredito.models.Transacao;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Date;

@Configuration
public class ReaderConfig {

    @Bean
    JdbcCursorItemReader<Transacao> transacaoJdbcCursorItemReader(
            @Qualifier("appDataSource") DataSource dataSource
    ) {
        return new JdbcCursorItemReaderBuilder<Transacao>()
                .name("transacaoJdbcCursorItemReader")
                .dataSource(dataSource)
                .sql("SELECT * FROM transacao INNER JOIN cartao_credito USING (numero_cartao_credito) ORDER BY numero_cartao_credito")
                .rowMapper((rs, rowNum) -> Transacao.builder()
                        .id(rs.getLong("id"))
                        .valor(rs.getBigDecimal("valor"))
                        .data(new Date(rs.getDate("data").getTime()))
                        .cartaoCredito(CartaoCredito.builder()
                                .cliente(Cliente.builder()
                                        .id(rs.getLong("cliente"))
                                        .build())
                                .numeroCartao(rs.getLong("numero_cartao_credito"))
                                .build())
                        .descricao(rs.getString("descricao"))
                        .build())
                .build();
    }
}
