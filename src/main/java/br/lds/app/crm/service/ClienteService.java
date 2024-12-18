package br.lds.app.crm.service;

import br.lds.app.crm.model.Cliente;
import br.lds.app.crm.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService implements UserDetailsService {

    private final BCryptPasswordEncoder encoder;

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteService(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    private Cliente formatarCliente(Cliente cliente) {
        cliente.setCnpj(cliente.getCnpj().replaceAll("[^0-9]", ""));
        cliente.setRazaoSocial(cliente.getRazaoSocial().toUpperCase());
        cliente.setCodigoEmpresa(cliente.getCodigoEmpresa().toUpperCase());
        cliente.setNomeRamoAtividade(cliente.getNomeRamoAtividade().toUpperCase());
        cliente.setNome(cliente.getNome().toUpperCase());
        cliente.setDocumentos(new ArrayList<>());
        cliente.setAtivo(true);
        cliente.setBloqueado(false);
        cliente.setEmail(cliente.getEmail().toLowerCase());
        cliente.setTelefone(cliente.getTelefone().replaceAll("[^0-9]", ""));
        cliente.setSenha(encoder.encode(cliente.getSenha()));
        cliente.setCargo("CLIENTE");
        return cliente;
    }

    public void criarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }
        this.formatarCliente(cliente);
        clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarPorId(UUID id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email.toLowerCase());
    }

    public Optional<Cliente> buscarPorCnpj(String cnpj) {
        return clienteRepository.findByCnpj(cnpj.replaceAll("[^0-9]", ""));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return User.builder()
                .username(cliente.getEmail())
                .password(cliente.getSenha())
                .roles(cliente.getCargo())
                .disabled(!cliente.isAtivo())
                .build();
    }

}
