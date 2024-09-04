package br.com.bruno.envioemailsbatch.writers;

import org.springframework.batch.item.mail.SimpleMailMessageItemWriter;
import org.springframework.batch.item.mail.builder.SimpleMailMessageItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

@Configuration
public class WriterConfig {

    @Bean
    SimpleMailMessageItemWriter simpleMailMessageItemWriter(MailSender mailSender) {
        return new SimpleMailMessageItemWriterBuilder()
                .mailSender(mailSender)
                .build();
    }
}
