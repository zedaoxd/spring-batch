package br.com.bruno.migracaodedados.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Data
@Builder
public class Pessoa {
    private Long id;
    private String nome;
    private Integer idade;
    private String email;
    private Date dataNascimento;

    public boolean idValid() {
        return !isBlank(nome)
                && idade != null
                && !isBlank(email)
                && dataNascimento != null;
    }
}
