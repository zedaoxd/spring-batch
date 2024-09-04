package br.com.bruno.envioemailsbatch.readers;

import br.com.bruno.envioemailsbatch.models.Cliente;
import br.com.bruno.envioemailsbatch.models.IntereseProdutoCliente;
import br.com.bruno.envioemailsbatch.models.Produto;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ReaderConfig {

    @Bean
    JdbcCursorItemReader<IntereseProdutoCliente> intereseProdutoClienteJdbcCursorItemReader(
            @Qualifier("appDataSource") DataSource dataSource
    ) {
        return new JdbcCursorItemReaderBuilder<IntereseProdutoCliente>()
                .name("intereseProdutoClienteJdbcCursorItemReader")
                .dataSource(dataSource)
                .sql("SELECT\n" +
                        "    c.id as clienteId,\n" +
                        "    c.nome as clienteNome,\n" +
                        "    c.email,\n" +
                        "    p.id as produtoId,\n" +
                        "    p.nome as produtoNome,\n" +
                        "    p.descricao,\n" +
                        "    p.preco\n" +
                        "    FROM interesse_produto_cliente i\n" +
                        "        INNER JOIN cliente c ON i.cliente = c.id\n" +
                        "        INNER JOIN produto p ON p.id = i.produto")
                .rowMapper((rs, rowNum) -> IntereseProdutoCliente.builder()
                        .cliente(Cliente.builder()
                                .id(rs.getLong("clienteId"))
                                .nome(rs.getString("clienteNome"))
                                .email(rs.getString("email"))
                                .build())
                        .produto(Produto.builder()
                                .id(rs.getLong("produtoId"))
                                .nome(rs.getString("produtoNome"))
                                .descricao(rs.getString("descricao"))
                                .preco(rs.getDouble("preco"))
                                .build())
                        .build()
                )
                .build();
    }
}
