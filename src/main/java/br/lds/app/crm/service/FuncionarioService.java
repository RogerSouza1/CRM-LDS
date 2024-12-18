package br.lds.app.crm.service;

import br.lds.app.crm.model.Funcionario;
import br.lds.app.crm.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FuncionarioService implements UserDetailsService {

    private final BCryptPasswordEncoder encoder;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public FuncionarioService() {
        this.encoder = new BCryptPasswordEncoder();
    }

    private Funcionario formatarFuncionario(Funcionario funcionario) {
        funcionario.setCpf(funcionario.getCpf().replaceAll("[^0-9]", ""));
        funcionario.setAtivo(true);
        funcionario.setBloqueado(false);
        funcionario.setEmail(funcionario.getEmail().toLowerCase());
        funcionario.setTelefone(funcionario.getTelefone().replaceAll("[^0-9]", ""));
        funcionario.setSenha(encoder.encode(funcionario.getSenha()));
        funcionario.setCargo("FUNCIONARIO");
        return funcionario;
    }

    public void criarFuncionario(Funcionario funcionario) {

        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }

        funcionarioRepository.save(formatarFuncionario(funcionario));
    }

    public Optional<Funcionario> buscarPorId(UUID id) {
        return funcionarioRepository.findById(id);
    }

    public Optional<Funcionario> buscarPorEmail(String email) {
        return funcionarioRepository.findByEmail(email.toLowerCase());
    }

    public Optional<Funcionario> buscarPorCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf.replaceAll("[^0-9]", ""));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return User.builder()
                .username(funcionario.getEmail())
                .password(funcionario.getSenha())
                .roles(funcionario.getCargo())
                .disabled(!funcionario.isAtivo())
                .build();
    }


}
