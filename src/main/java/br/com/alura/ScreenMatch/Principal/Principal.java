package br.com.alura.ScreenMatch.Principal;

import br.com.alura.ScreenMatch.Model.*;
import br.com.alura.ScreenMatch.service.ConsumoApi;
import br.com.alura.ScreenMatch.service.ConverteDados;
import br.com.alura.ScreenMatch.Repository.SerieRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.exit;


public class Principal {


    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private List<DadosSerie> dadosSeries = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=" + System.getenv("OMDB_KEY");
    private final String SEASON = "&season=";

    private SerieRepository repositorio;

    Optional<Serie> serieBuscada;

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }
    public void exibeMenu() {

        var opcao = -1;

        while (opcao != 0) {
            try {
                var menu = """
                        O que você deseja fazer?
                        1 - Buscar e Adicionar uma Série no Banco de Dados;
                        2 - Listar Séries Buscadas;
                        3 - Buscar Episodios de uma Série;
                        4 - Buscar uma série no Banco de Dados;
                        5 - BUscar Série por Ator;
                        6 - BUscar Séries POr Gênero;
                        7 - Byscar Séries por NUmero de Temporadas;
                        8 - BUscar Séries POr Avaliação;
                        9 - Buscar Episódios POr temporada;
                        0 - Sair
                        """;
                System.out.println(menu);
                System.out.println("Digite uma opção: ");
                opcao = leitura.nextInt();
                leitura.nextLine();

                switch (opcao) {

                    case 1:
                        buscarSerieWeb();
                        break;
                    case 3:
                        buscarEpisodioPorSerie();
                        break;
                    case 2:
                        listarSeriesBUscadas();
                        break;
                    case 4:
                        buscarSeriePorTitulo();
                        break;
                    case 5:
                        buscarSeriePorAtor();
                        break;
                    case 6:
                        buscarSeriePorGenero();
                        break;
                    case 7:
                        buscarSeriePorNUmeroTemporada();
                        break;
                    case 8:
                        buscarSeriesPorAvaliacao();
                        break;
                    case 9:
                        buscarEpisodiosPorTemporada();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Opção invalida, tente novamente");
                leitura.nextLine();
            }
        }
    }

    private void buscarEpisodiosPorTemporada() {
        buscarSeriePorTitulo();

        if(serieBuscada.isPresent()) {
            System.out.println("QUal temporada deseja buscar? ");
            var numeroDaTemporada = leitura.nextInt();
            leitura.nextLine();
            List <Episodio> episodiosTemporada = repositorio.findByTemporada(numeroDaTemporada);


            if (!episodiosTemporada.isEmpty()) {
                System.out.println("Os episódios dessa temporada são: ");

                episodiosTemporada.forEach(System.out::println);

            } else {
                System.out.println("Temporada não encontrada");
            } }


    }
    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }
    private void buscarSerieWeb() {
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
    private void buscarEpisodioPorSerie() {
        listarSeriesBUscadas();
        System.out.println("ESCOLHA UMA SÉRIE: ");
        var nomeSerie = leitura.nextLine();

        Optional <Serie> serie = repositorio.findByTituloContainingIgnoreCase (nomeSerie);


        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + SEASON + i + API_KEY);
                DadosTemporada dadosTemporadas = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporadas);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada");
        }
    }
    private void listarSeriesBUscadas() {

        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
    private void buscarSeriePorTitulo() {
        System.out.println("DIgite o nome da serie");
        var nomeSerie = leitura.nextLine();
        serieBuscada = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()) {
            System.out.println("Os dados da série buscada são: " + serieBuscada.get());
        } else {
            System.out.println("Série Não encontrada. Deseja adicionar? \n " +
                    "DIgite 1 para Sim ou 2 para encerrar.");
            var respostaUsuario = leitura.nextInt();
            leitura.nextLine();

            if (respostaUsuario == 1) {
                buscarSerieWeb();
            } else if (respostaUsuario == 2) {
                System.out.println("Encerrando programa");
                System.exit(0);
            } else {
                System.out.println("Opção invalida, tente novamente");
            }
        }


    }
    private void buscarSeriePorAtor() {

        System.out.println("DIgite o nome do Ator");
        var nomeAtor = leitura.nextLine();
        List <Serie> seriesAtor = repositorio.findByAtoresContainingIgnoreCase(nomeAtor);

        if (!seriesAtor.isEmpty()) {
            System.out.println("As séries encontradas foram: ");
            seriesAtor.forEach(s -> System.out.println(s.getTitulo()
                    + "\nAvaliação: "
                    + s.getAvaliacao()
                    + "\nAtores: "
                    + s.getAtores()
                    + "\n"
                    +  s.getGenero()));
        } else {
            System.out.println("Nenhuma Série Encontrada");
        }
    }
    private void buscarSeriePorGenero() {

        System.out.println("DIgite um Gênero");
        String tipoGenero = leitura.nextLine();

        Categoria categoria = Categoria.fromPortugues(tipoGenero);
        List<Serie> seriesGenero = repositorio.findByGenero(categoria);
        System.out.println("As séries da categoria " + tipoGenero + " são");

        seriesGenero.forEach(System.out::println);

    }
    private void buscarSeriePorNUmeroTemporada() {
        System.out.println("Até quantas Temporadas deseja buscar? ");
        var numeroTemporadas = leitura.nextInt();
        leitura.nextLine();
        List <Serie> serieTemporadas = repositorio.findByTotalTemporadasLessThanEqual(numeroTemporadas);


        if (!serieTemporadas.isEmpty()) {
            System.out.println("As séries  com menos de " + numeroTemporadas + " são");

            serieTemporadas.forEach(System.out::println);

        } else {
            System.out.println("Nenhuma Série Encontrada");
        }

    }
    private void buscarSeriesPorAvaliacao() {

        System.out.println("Qual avaliação mínima deseja buscar? ");
        var avaliacaoMinima = leitura.nextInt();
        leitura.nextLine();
        List <Serie> serieTemporadas = repositorio.findByAvaliacaoGreaterThanEqual(avaliacaoMinima);


        if (!serieTemporadas.isEmpty()) {
            System.out.println("As séries  com mais de " + avaliacaoMinima + " são");

            serieTemporadas.forEach(System.out::println);

        } else {
            System.out.println("Nenhuma Série Encontrada");
        }


    }

}

