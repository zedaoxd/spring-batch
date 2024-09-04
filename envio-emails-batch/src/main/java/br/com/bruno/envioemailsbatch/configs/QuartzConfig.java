package br.com.bruno.envioemailsbatch.configs;

import br.com.bruno.envioemailsbatch.jobs.SendEmailPromocoesScheduleJob;
import org.quartz.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    JobDetail jobDetail() {
        return JobBuilder.newJob()
                .ofType(SendEmailPromocoesScheduleJob.class)
                .storeDurably()
                .withIdentity("sendEmailPromocoesJob")
                .withDescription("Send email promocoes job")
                .build();
    }

    @Bean
    Trigger jobTrigger(JobDetail job) {
        return TriggerBuilder.newTrigger()
                .forJob(job)
                .withIdentity("sendEmailPromocoesTrigger")
                .withDescription("Send email promocoes trigger")
                // each 1 minute
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *"))
                .build();
    }
}
