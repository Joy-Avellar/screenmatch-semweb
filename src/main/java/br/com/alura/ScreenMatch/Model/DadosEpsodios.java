package br.com.alura.ScreenMatch.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpsodios(@JsonAlias("Title") String titulo,
                           @JsonAlias("Episode") Integer numero,
                            @JsonAlias("imdbRating") String avaliacao,
                            @JsonAlias("Released") String dataLancamento) {

    @Override
    public String toString() {
        return  numero +
                "\n Titulo: " + titulo +
                "\n Avaliação: " + avaliacao +
                "\n Data de Lançamento: " + dataLancamento + "\n";
    }
}
