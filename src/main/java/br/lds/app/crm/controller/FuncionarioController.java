package br.lds.app.crm.controller;

import br.lds.app.crm.model.Endereco;
import br.lds.app.crm.model.Funcionario;
import br.lds.app.crm.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/criar")
    public ModelAndView criarFuncionario(Model model) {
        Funcionario funcionario = new Funcionario();
        funcionario.setEndereco(new Endereco());
        model.addAttribute("funcionario", funcionario);
        System.out.println("Criar funcionario no forms");
        return new ModelAndView("backoffice/formularios/criar/criar-usuario");
    }

    @PostMapping("/criar")
    public ModelAndView criarFuncionario(@Valid @ModelAttribute Funcionario funcionario, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Fomulario com erro");
            return new ModelAndView("backoffice/formularios/criar/criar-usuario");
        }


        Optional<Funcionario> funcionarioOptionalByCnpj = funcionarioService.buscarPorCpf(funcionario.getCpf());
        if (funcionarioOptionalByCnpj.isPresent()) {
            System.out.println("CPF já cadastrado");
            model.addAttribute("mensagem", "CPF já cadastrado");
            return new ModelAndView("backoffice/formularios/criar/criar-usuario");
        }

        Optional<Funcionario> funcionarioOptionalByEmail = funcionarioService.buscarPorEmail(funcionario.getEmail());
        if (funcionarioOptionalByEmail.isPresent()) {
            System.out.println("Email já cadastrado");
            model.addAttribute("mensagem", "Email já cadastrado");
            return new ModelAndView("backoffice/formularios/criar/criar-usuario");
        }


        funcionarioService.criarFuncionario(funcionario);

        redirectAttributes.addFlashAttribute("mensagem", "Funcionário criado com sucesso!");
        redirectAttributes.addFlashAttribute("email", funcionario.getEmail());
        return new ModelAndView("redirect:/login");
    }

}
