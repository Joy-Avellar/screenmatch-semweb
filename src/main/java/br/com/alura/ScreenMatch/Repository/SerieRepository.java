package br.com.alura.ScreenMatch.Repository;

import br.com.alura.ScreenMatch.Model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    List<Serie> findByTitulo(String titulo);

}
