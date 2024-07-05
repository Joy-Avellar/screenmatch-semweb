package br.com.alura.ScreenMatch.service.traducao;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
record DadosResposta(@JsonAlias(value = "translatedText")
                             String textoTraduzido) {
}
