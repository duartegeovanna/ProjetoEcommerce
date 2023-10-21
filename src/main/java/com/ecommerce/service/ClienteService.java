package com.ecommerce.service;

import com.ecommerce.entity.Cliente;
import javassist.NotFoundException;

public interface ClienteService {

    Iterable<Cliente> buscarTodos();

    Cliente buscarClientePorId(Long id) throws NotFoundException;

    Cliente buscarClientePorNome(String nome);

    void salvarNovoClienteComCep(Cliente cliente);

}
