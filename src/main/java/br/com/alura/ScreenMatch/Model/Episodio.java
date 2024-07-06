package br.com.alura.ScreenMatch.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import jdk.jfr.Category;
import org.hibernate.annotations.ManyToAny;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodios")
public class Episodio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

    @ManyToOne
    private Serie serie;

    public Episodio(){

    }

    public Episodio(Integer numeroTemporada, DadosEpsodios dadosEpsodios) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpsodios.titulo();
        this.numeroEpisodio = dadosEpsodios.numero();
        try {
            this.avaliacao = Double.valueOf(dadosEpsodios.avaliacao());
        } catch (NumberFormatException ex){
            this.avaliacao = 0.0;
        }
        try{
            this.dataLancamento = LocalDate.parse(dadosEpsodios.dataLancamento());
        } catch (DateTimeParseException ex){
            this.dataLancamento = null;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numeroEpisodio;
    }

    public void setNumero(Integer numero) {
        this.numeroEpisodio = numero;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return  "Temporada: " + temporada +
                " Numero do Episodio: " + numeroEpisodio +
                " Titulo: '" + titulo +
                " Avaliacão: " + avaliacao +
                " Data de Lançamento " + dataLancamento;
    }
}
