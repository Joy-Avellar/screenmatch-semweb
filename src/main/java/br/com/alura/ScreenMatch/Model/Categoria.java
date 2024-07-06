package br.com.alura.ScreenMatch.Model;

import br.com.alura.ScreenMatch.Principal.Principal;
import br.com.alura.ScreenMatch.Repository.SerieRepository;

public enum Categoria {

        ACAO("Action", "Ação"),
        ROMANCE("Romance", "Romance"),
        COMEDIA("Comedy", "Comédia"),
        DRAMA("Drama", "Drama"),
        CRIME("Crime", "Crime");


        private String categoriaOmdb;
        private String categoriaPortugues;
        private SerieRepository repositorio;
        Principal principal = new Principal(repositorio);

        Categoria(String categoriaOmdb, String categoriaPortugues) {
            this.categoriaOmdb = categoriaOmdb;
            this.categoriaPortugues = categoriaPortugues;
        }

        public static Categoria fromString(String text) {
            for (Categoria categoria : Categoria.values()) {
                if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                    return categoria;
                }
            }
            throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
        }

        public static Categoria fromPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

}
