package br.com.bruno.migracaodedados.jobs;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Configuration
public class JobConfig {
    private final JobRepository jobRepository;

    @Bean
    Job job(
            @Qualifier("migrarPessoasStep") Step migrarPessoasStep,
            @Qualifier("migrarDadosBancariosStep") Step migrarDadosBancariosStep
    ) {
        return new JobBuilder("job", jobRepository)
                .start(stepParalelos(migrarPessoasStep, migrarDadosBancariosStep))
                .end()
                .incrementer(new RunIdIncrementer())
                .build();
    }

    private Flow stepParalelos(Step ...steps) {
        AtomicInteger size = new AtomicInteger(0);
        var flows = Arrays.stream(steps).map(step -> new FlowBuilder<Flow>("flow" + size.getAndIncrement())
                .start(step)
                .build()).toArray(Flow[]::new);

        return new FlowBuilder<Flow>("stepParalelos")
                .split(task -> Thread.ofVirtual().start(task))
                .add(flows)
                .build();
    }
}
