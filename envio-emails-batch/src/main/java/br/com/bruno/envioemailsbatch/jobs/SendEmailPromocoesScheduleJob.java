package br.com.bruno.envioemailsbatch.jobs;

import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

@RequiredArgsConstructor
public class SendEmailPromocoesScheduleJob extends QuartzJobBean {
    private final Job job;
    private final JobExplorer jobExplorer;
    private final JobLauncher jobLauncher;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        var jobParameter = new JobParametersBuilder(this.jobExplorer).getNextJobParameters(this.job).toJobParameters();
        try {
            this.jobLauncher.run(this.job, jobParameter);
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
