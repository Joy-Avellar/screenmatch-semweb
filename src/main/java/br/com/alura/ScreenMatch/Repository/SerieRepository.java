package br.com.alura.ScreenMatch.Repository;

import br.com.alura.ScreenMatch.Model.Categoria;
import br.com.alura.ScreenMatch.Model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    List<Serie> findByTitulo(String titulo);
    Optional <Serie> findByTituloContainingIgnoreCase(String titulo);
    List <Serie> findByAtoresContainingIgnoreCase(String atores);
    List<Serie> findByGenero(Categoria categoria);
    List<Serie> findByTotalTemporadasLessThanEqual(Integer numeroTemporadas);
    List<Serie> findByAvaliacaoGreaterThanEqual(int avaliacao);

}
