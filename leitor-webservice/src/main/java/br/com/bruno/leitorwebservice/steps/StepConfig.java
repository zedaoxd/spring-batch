package br.com.bruno.leitorwebservice.steps;

import br.com.bruno.leitorwebservice.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class StepConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    @Value("${chunkSize}")
    private Integer chunkSize;

    @Bean
    Step step(
            ItemReader<User> reader,
            ItemWriter<User> writer
    ) {
        return new StepBuilder("step", jobRepository)
                .<User, User>chunk(chunkSize, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }
}
