package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Cliente;
import com.example.demo.models.Quarto;
import com.example.demo.repositories.QuartoRepository;

@RestController
@RequestMapping("/api")
public class QuartoController {

  @Autowired
  QuartoRepository quartoRepository;

  // Método para criar um novo quarto
  @PostMapping("/quartos")
  public ResponseEntity<Quarto> createQuarto(@RequestBody Quarto quarto) {
    try {
      Quarto _quarto = quartoRepository.save(quarto);
      return new ResponseEntity<>(_quarto, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Método para listar todos os quartos
  @GetMapping("/quartos")
  public ResponseEntity<List<Quarto>> getAllQuartos() {
    try {
      List<Quarto> quartos = quartoRepository.findAll();
      if (quartos.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(quartos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Método para buscar um quarto por ID
  @GetMapping("/quartos/{id}")
  public ResponseEntity<Quarto> getQuartoById(@PathVariable("id") long id) {
    Optional<Quarto> quartoData = quartoRepository.findById(id);

    if (quartoData.isPresent()) {
      return new ResponseEntity<>(quartoData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // Método para atualizar um quarto existente
  @PutMapping("/quartos/{id}")
  public ResponseEntity<Quarto> updateQuarto(@PathVariable("id") long id, @RequestBody Quarto quarto) {
    Optional<Quarto> quartoData = quartoRepository.findById(id);

    if (quartoData.isPresent()) {
      Quarto _quarto = quartoData.get();
      _quarto.setNumero(quarto.getNumero());
      _quarto.setTipo(quarto.getTipo());
      _quarto.setPrecoPorNoite(quarto.getPrecoPorNoite());
      return new ResponseEntity<>(quartoRepository.save(_quarto), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // Método para deletar um quarto por ID
  @DeleteMapping("/quartos/{id}")
  public ResponseEntity<Void> deleteQuarto(@PathVariable("id") long id) {
    Optional<Quarto> quartoData = quartoRepository.findById(id);
    if (quartoData.isPresent()) {
      quartoRepository.delete(quartoData.get());
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
