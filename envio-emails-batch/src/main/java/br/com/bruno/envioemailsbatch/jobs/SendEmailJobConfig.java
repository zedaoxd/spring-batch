package br.com.bruno.envioemailsbatch.jobs;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SendEmailJobConfig {
    private final JobRepository jobRepository;

    @Bean
    Job sendEmailJob(Step step) {
        return new JobBuilder("sendEmailJob", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
