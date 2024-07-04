package br.com.alura.ScreenMatch.Principal;

import br.com.alura.ScreenMatch.Model.DadosEpsodios;
import br.com.alura.ScreenMatch.Model.DadosSerie;
import br.com.alura.ScreenMatch.Model.DadosTemporada;
import br.com.alura.ScreenMatch.Model.Episodio;
import br.com.alura.ScreenMatch.service.ConsumoApi;
import br.com.alura.ScreenMatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=7d277e04";
    private final String SEASON = "&season=";

    public void exibeMenu() {

        System.out.println("Digite o nome da série para busca");

        var nomeSerie = leitura.nextLine();

        //http://www.omdbapi.com/?t=grey's+anatomy&season=" + i + "&apikey=7d277e04"
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+") + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

	    for (int i = 1; i <= dados.totalTemporadas() ; i++) {
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+") + SEASON + i + API_KEY);
			DadosTemporada dadosTemporadas = conversor.obterDados(json,DadosTemporada.class);
			temporadas.add(dadosTemporadas);
		}

		temporadas.forEach(System.out::println);

        //(parametro) -> {expressão}

        temporadas.forEach(t -> {
            System.out.println("Temporada: " + t.numero());
            t.episodios().forEach(e -> System.out.println(e.titulo()));
        });

        List<DadosEpsodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("Top 5");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpsodios:: avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                    .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

//       temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    //    temporadas.forEach(t -> t.episodios().;



    }
}
