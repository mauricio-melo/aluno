package br.com.escola.alunos.service;

import br.com.escola.alunos.model.Aluno;
import br.com.escola.alunos.repository.AlunoRepository;
import br.com.escola.alunos.repository.filter.AlunoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository repository;

    public void salvar(Aluno aluno) {
        repository.save(aluno);
    }



    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public List<Aluno> filtrar(AlunoFilter filtro) {
        String nome = filtro.getNome() == null ? "%" : filtro.getNome();
        return repository.findByNome(nome);
    }


}
