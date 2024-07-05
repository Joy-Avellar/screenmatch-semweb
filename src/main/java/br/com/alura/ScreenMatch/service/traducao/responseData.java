package br.com.alura.ScreenMatch.service.traducao;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
record DadosTraducao(@JsonAlias(value = "responseData")
                            DadosResposta dadosResposta) {
}
