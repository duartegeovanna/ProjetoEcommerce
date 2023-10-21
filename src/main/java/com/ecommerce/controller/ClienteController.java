package com.ecommerce.controller;
import com.ecommerce.entity.Cliente;
import com.ecommerce.service.ClienteService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/cliente", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteController {

    // Singleton: Injetar os componentes do Spring com @Autowired.
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Iterable<Cliente>> buscarTodos() {
        return ResponseEntity.ok(clienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(clienteService.buscarClientePorId(id));
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Cliente> buscarClientePorNome(@PathVariable String nome) throws NotFoundException {

        var response = clienteService.buscarClientePorNome(nome);

        if (response == null) {
            throw new NotFoundException("Não existe um cliente com o nome informado");
        }
        return ResponseEntity.ok(clienteService.buscarClientePorNome(nome));
    }

    @PostMapping
    public ResponseEntity<Cliente> inserir(@RequestBody Cliente cliente) {
        clienteService.salvarNovoClienteComCep(cliente);
        return ResponseEntity.ok(cliente);
    }
}