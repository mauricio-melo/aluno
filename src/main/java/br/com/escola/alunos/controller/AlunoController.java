package br.com.escola.alunos.controller;

import br.com.escola.alunos.model.Aluno;
import br.com.escola.alunos.repository.filter.AlunoFilter;
import br.com.escola.alunos.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/alunos")
public class AlunoController{
    private static final String CADASTRO_VIEW = "CadastroAluno";

    @Autowired
    private AlunoService service;

    @RequestMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
        mv.addObject(new Aluno());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String salvar(@Validated Aluno aluno, Errors errors, RedirectAttributes attributes) {

        if (errors.hasErrors()) {
            return CADASTRO_VIEW;
        }

        try {
            service.salvar(aluno);
            attributes.addFlashAttribute("mensagem", "Aluno salvo com sucesso!");
            return "redirect:/alunos/novo";
        } catch (IllegalArgumentException e) {
            errors.rejectValue("dataNascimento", null, e.getMessage());
            return CADASTRO_VIEW;
        }
    }

    @RequestMapping
    public ModelAndView pesquisar(@ModelAttribute("filtro") AlunoFilter filtro) {
        List<Aluno> todosAlunos = service.filtrar(filtro);

        ModelAndView mv = new ModelAndView("PesquisaAlunos");
        mv.addObject("alunos", todosAlunos);
        return mv;
    }

    @RequestMapping("{id}")
    public ModelAndView edicao(@PathVariable("id") Aluno aluno) {
        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
        mv.addObject(aluno);
        return mv;
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
        service.excluir(id);

        attributes.addFlashAttribute("mensagem", "Aluno excluído com sucesso!");
        return "redirect:/alunos";
    }

}
