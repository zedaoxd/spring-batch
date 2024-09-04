package br.com.bruno.envioemailsbatch.processors;

import br.com.bruno.envioemailsbatch.models.IntereseProdutoCliente;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class ProcessorConfig implements ItemProcessor<IntereseProdutoCliente, SimpleMailMessage> {

    @Override
    public SimpleMailMessage process(IntereseProdutoCliente item) throws Exception {
        var email = new SimpleMailMessage();
        email.setFrom("xpto@mp-replay.com");
        email.setTo(item.getCliente().getEmail());
        email.setSubject("Produto em promoção");
        String text = String.format("Olá %s, o produto %s está em promoção por apenas R$ %.2f",
                item.getCliente().getNome(), item.getProduto().getNome(), item.getProduto().getPreco());
        email.setText(text);

        // Using a free mailtrap account, we can send emails with short delays
        Thread.sleep(2_000);
        return email;
    }
}
