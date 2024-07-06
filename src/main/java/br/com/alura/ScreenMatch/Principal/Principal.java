package br.com.alura.ScreenMatch.Principal;

import br.com.alura.ScreenMatch.Model.*;
import br.com.alura.ScreenMatch.service.ConsumoApi;
import br.com.alura.ScreenMatch.service.ConverteDados;
import br.com.alura.ScreenMatch.Repository.SerieRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class Principal {


    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=" + System.getenv("OMDB_KEY");
    private final String SEASON = "&season=";
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repositorio;

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {

        var opcao = -1;

        while (opcao != 0) {
            try {
                var menu = """
                        O que você deseja fazer?
                        1- Buscar Serie
                        2- Buscar Informações detalhadas
                        3 - LIstar Séries BUscadas
                        0- Sair
                        """;
                System.out.println(menu);
                System.out.println("Digite uma opção: ");
                opcao = leitura.nextInt();
                leitura.nextLine();

                switch (opcao) {

                    case 1:
                        buscarSerieWeb();
                        break;
                    case 2:
                        buscarEpisodioPorSerie();
                        break;
                    case 3:
                        listarSeriesBUscadas();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida");
                } } catch(InputMismatchException ex){
            System.out.println("Opção invalida, tente novamente");
            leitura.nextLine();
            }
        }
    }


    private void buscarSerieWeb () {
//        DadosSerie dados = getDadosSerie();
//        Serie serie = new Serie(dados);
//        //dadosSeries.add(dados);
//        repositorio.save(serie);
//        System.out.println(dados);

        DadosSerie dados = getDadosSerie();
        List<Serie> existingSerie = repositorio.findByTitulo(dados.getTitulo());
        if (!existingSerie.isEmpty()) {
            System.out.println("A série já existe no banco de dados: " + existingSerie.get(0));
        } else {
            Serie serie = new Serie(dados);
            repositorio.save(serie);
            System.out.println("Série salva: " + dados);
        }

            }
    private DadosSerie getDadosSerie () {
                System.out.println("Digite o nome da série para busca");
                var nomeSerie = leitura.nextLine();
                var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
                DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
                return dados;
            }
    private void buscarEpisodioPorSerie () {
                DadosSerie dados = getDadosSerie();
                List<DadosTemporada> temporadas = new ArrayList<>();

                for (int i = 1; i <= dados.totalTemporadas(); i++) {
                    var json = consumo.obterDados(ENDERECO + dados.titulo().replace(" ", "+") + SEASON + i + API_KEY);
                    DadosTemporada dadosTemporadas = conversor.obterDados(json, DadosTemporada.class);
                    temporadas.add(dadosTemporadas);
                }
                temporadas.forEach(System.out::println);
            }
    private void listarSeriesBUscadas() {

        List<Serie> series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }



}

//    private DadosEpsodios getDadosEpisodios() {
//        System.out.println("Digite o nome da Série");
//
//    }
//
//
//    List<DadosTemporada> temporadas = new ArrayList<>();
//
//            for (int i = 1; i <= dados.totalTemporadas() ; i++) {
//                json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+") + SEASON + i + API_KEY);
//                DadosTemporada dadosTemporadas = conversor.obterDados(json,DadosTemporada.class);
//                temporadas.add(dadosTemporadas);
//            }
//
//            temporadas.forEach(System.out::println);
//
//        //(parametro) -> {expressão}
//
//        temporadas.forEach(t -> {
//            System.out.println("Temporada: " + t.numero());
//            t.episodios().forEach(e -> System.out.println(e.titulo()));
//        });
//
//        List<DadosEpsodios> dadosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                .collect(Collectors.toList());
//
//        System.out.println("Top 10");
//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(DadosEpsodios:: avaliacao).reversed())
//                .limit(10)
//                .map(e-> e.titulo().toUpperCase())
//                .forEach(System.out::println)