package dev.weslleydourado.challengeliteratura.repository;

import java.util.List;

import dev.weslleydourado.challengeliteratura.entity.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LivroRepository extends JpaRepository<LivroEntity, Long> {

    @Query("SELECT l FROM LivroEntity l WHERE l.lenguaje >= :idioma")
    List<LivroEntity> findForLanguaje(String idioma);

}
