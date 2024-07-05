package br.com.alura.ScreenMatch.Model;

import br.com.alura.ScreenMatch.service.traducao.ConsultaMyMemory;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.OptionalDouble;

public class Serie  {

    @JsonAlias("Title") String titulo;
    @JsonAlias("totalSeasons") Integer totalTemporadas;
    @JsonAlias("imdbRating") Double avaliacao;
    @JsonAlias("Genre") Categoria genero;
    @JsonAlias("Writer") String escritor;
    @JsonAlias("Actors") String atores;
    @JsonAlias("Plot") String sinopse;
    @JsonAlias("poster") String poster;

    public Serie(DadosSerie dadosSerie) {
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.escritor = dadosSerie.escritor();
        this.sinopse = ConsultaMyMemory.obterTraducao(dadosSerie.sinopse()).trim();
        this.poster = dadosSerie.poster();

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getEscritor() {
        return escritor;
    }

    public void setEscritor(String escritor) {
        this.escritor = escritor;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "\n Titulo: " + titulo +
                "\n Total Temporadas: " + totalTemporadas +
                "\n Avaliaçãoo: " + avaliacao +
                "\n Genero: " + genero +
                "\n Escritor: " + escritor +
                "\n Atores: " + atores +
                "\n Sinopse: " + sinopse +
                "\n Poster: " + poster;
    }
}
