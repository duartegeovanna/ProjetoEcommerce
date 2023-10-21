package com.ecommerce.repository;

import com.ecommerce.entity.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    Cliente findByNome(String nome);

}