package br.com.escola.alunos.repository;

import br.com.escola.alunos.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("select a from Aluno a where a.nome like %:nome%")
    public List<Aluno> findByNome(@Param("nome") String Nome);

}
