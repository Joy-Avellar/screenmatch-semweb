package br.com.alura.ScreenMatch.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao,
                        @JsonAlias("Genre") String genero,
                         @JsonAlias("Writer") String escritor,
                         @JsonAlias("Actors") String atores,
                         @JsonAlias("Plot") String sinopse,
                         @JsonAlias("Poster") String poster) {



    @Override
    public String toString() {
        return "\n Poster: " + poster +
                "\n Titulo: " + titulo +
                "\n, totalTemporadas: " + totalTemporadas +
                "\n avaliacao: " + avaliacao +
                "\n genero: " + genero +
                "\n escritor: " + escritor +
                "\n atores: " + atores +
                "\n Sinopse: " + sinopse;
}
}
