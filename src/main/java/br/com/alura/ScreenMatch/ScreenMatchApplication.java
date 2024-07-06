package br.com.alura.ScreenMatch;
import br.com.alura.ScreenMatch.Repository.SerieRepository;

import br.com.alura.ScreenMatch.Model.DadosEpsodios;
import br.com.alura.ScreenMatch.Model.DadosSerie;
import br.com.alura.ScreenMatch.Model.DadosTemporada;
import br.com.alura.ScreenMatch.Principal.Principal;
import br.com.alura.ScreenMatch.service.ConsumoApi;
import br.com.alura.ScreenMatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {
	@Autowired
	private SerieRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio);
			principal.exibeMenu();
	}
}
