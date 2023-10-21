package com.ecommerce.service.impl;

import com.ecommerce.entity.Cliente;
import com.ecommerce.entity.Endereco;
import com.ecommerce.repository.ClienteRepository;
import com.ecommerce.repository.EnderecoRepository;
import com.ecommerce.service.ClienteService;
import com.ecommerce.service.ViaCepService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Singleton: Injetar os componentes do Spring com @Autowired.
// Strategy: Implementar os métodos definidos na interface.
// Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarClientePorId(Long id) throws NotFoundException {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isEmpty()) {
            throw new NotFoundException("Não existe um cliente com o ID informado");
        }
        return cliente.get();
    }

    @Override
    public Cliente buscarClientePorNome(String nome) {
        return clienteRepository.findByNome(nome);
    }

    @Override
    public void salvarNovoClienteComCep(Cliente cliente) {
        // Verificar se o Endereco do Cliente já existe (pelo CEP).
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            // Caso não exista, integrar com o ViaCEP e persistir o retorno.
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        // Inserir Cliente, vinculando o Endereco (novo ou existente).
        clienteRepository.save(cliente);
    }
}
