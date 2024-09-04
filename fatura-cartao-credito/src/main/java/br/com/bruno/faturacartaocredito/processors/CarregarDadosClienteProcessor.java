package br.com.bruno.faturacartaocredito.processors;

import br.com.bruno.faturacartaocredito.models.Cliente;
import br.com.bruno.faturacartaocredito.models.FaturaCartao;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CarregarDadosClienteProcessor implements ItemProcessor<FaturaCartao, FaturaCartao> {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public FaturaCartao process(FaturaCartao fatura) throws Exception {
        var uri = String.format("https://my-json-server.typicode.com/zedaoxd/spring-batch/profile/%d", fatura.getCliente().getId());
        var response = restTemplate.getForEntity(uri, Cliente.class);

        if (response.getStatusCode() != HttpStatus.OK)
            throw new ValidationException("Cliente não encontrado");

        fatura.setCliente(response.getBody());
        return fatura;
    }
}
