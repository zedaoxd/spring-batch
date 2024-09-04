package br.com.bruno.leitorwebservice.writers;

import br.com.bruno.leitorwebservice.models.User;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class WriterConfig {

    @Bean
    FlatFileItemWriter<User> writer() {
        return new FlatFileItemWriterBuilder<User>()
                .name("userWriter")
                .resource(new FileSystemResource("output/users.csv"))
                .delimited()
                .delimiter(",")
                .names("id", "name", "email", "gender", "status")
                .build();
    }
}
