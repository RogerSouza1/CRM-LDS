package br.lds.app.crm.controller;

import br.lds.app.crm.model.Cliente;
import br.lds.app.crm.model.Endereco;
import br.lds.app.crm.service.ClienteService;
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
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/criar")
    public ModelAndView criarCliente(Model model) {
        Cliente cliente = new Cliente();
        cliente.setEndereco(new Endereco());
        model.addAttribute("cliente", cliente);
        return new ModelAndView("backoffice/formularios/criar/criar-usuario");
    }

    @PostMapping("/criar")
    public ModelAndView criarCliente(@Valid @ModelAttribute Cliente cliente, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return new ModelAndView("backoffice/formularios/criar/criar-usuario");
        }

        Optional<Cliente> clienteOptionalByCnpj = clienteService.buscarPorCnpj(cliente.getCnpj());
        if (clienteOptionalByCnpj.isPresent()) {
            model.addAttribute("mensagem", "CNPJ já cadastrado");
            return new ModelAndView("backoffice/formularios/criar/criar-usuario");
        }

        Optional<Cliente> clienteOptionalByEmail = clienteService.buscarPorEmail(cliente.getEmail());
        if (clienteOptionalByEmail.isPresent()) {
            model.addAttribute("mensagem", "Email já cadastrado");
            return new ModelAndView("backoffice/formularios/criar/criar-usuario");
        }

        clienteService.criarCliente(cliente);

        redirectAttributes.addFlashAttribute("mensagem", "Cliente criado com sucesso!");
        redirectAttributes.addFlashAttribute("email", cliente.getEmail());
        return new ModelAndView("redirect:/login");
    }

}
