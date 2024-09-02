package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Cliente;
import com.example.demo.repositories.ClienteRepository;

@RestController
@RequestMapping("/api")
public class ClienteController {

  @Autowired
  ClienteRepository repository;

  @GetMapping("/clientes")
  public ResponseEntity<List<Cliente>> getAllClientes() {
    return new ResponseEntity<List<Cliente>>(repository.findAll(), HttpStatus.OK);
  }

  @PostMapping("/clientes")
  public void addCliente(@RequestBody Cliente cliente) {
    repository.save(cliente);
  }

  @PutMapping("/clientes/{id}")
  public ResponseEntity<HttpStatus> updateCliente(@PathVariable("id") long id,
      @RequestBody Cliente cliente) {
    Optional<Cliente> clienteData = repository.findById(id);

    if (clienteData.isPresent()) {
      Cliente t = clienteData.get();
      t.setId(cliente.getId());
      t.setNome(cliente.getNome());
      t.setCpf(cliente.getCpf());
      t.setEmail(cliente.getEmail());
      repository.save(t);
      return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    return new ResponseEntity<HttpStatus>(HttpStatus.NOT_ACCEPTABLE);

  }

  @DeleteMapping("/clientes/{id}")
  public ResponseEntity<Void> deleteCliente(@PathVariable("id") long id) {
    Optional<Cliente> clienteData = repository.findById(id);

    if (clienteData.isPresent()) {
      repository.delete(clienteData.get());
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
