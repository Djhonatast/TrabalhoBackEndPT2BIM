package org.example.apicursos.repository;

import org.example.apicursos.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

    @Query("""
        SELECT c FROM Curso c
        WHERE (:tipoCurso IS NULL OR c.tipoCurso = :tipoCurso)
          AND (:modalidade IS NULL OR c.modalidade = :modalidade)
          AND (:periodo IS NULL OR c.periodo = :periodo)
        """)
    List<Curso> filtrar(@Param("tipoCurso") String tipoCurso,
                        @Param("modalidade") String modalidade,
                        @Param("periodo") String periodo);
}