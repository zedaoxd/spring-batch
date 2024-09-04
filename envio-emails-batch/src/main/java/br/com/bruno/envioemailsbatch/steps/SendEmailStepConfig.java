package br.com.bruno.envioemailsbatch.steps;

import br.com.bruno.envioemailsbatch.models.IntereseProdutoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class SendEmailStepConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    Step sendEmailStep(
            ItemReader<IntereseProdutoCliente> reader,
            ItemProcessor<IntereseProdutoCliente, SimpleMailMessage> processor,
            ItemWriter<SimpleMailMessage> writer
    ) {
        return new StepBuilder("sendEmailStep", jobRepository)
                .<IntereseProdutoCliente, SimpleMailMessage>chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
