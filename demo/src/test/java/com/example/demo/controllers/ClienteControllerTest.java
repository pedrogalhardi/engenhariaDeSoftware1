package com.example.demo.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Collections;
import java.util.Optional;

import com.example.demo.models.Cliente;
import com.example.demo.repositories.ClienteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClienteRepository repository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetAllClientes() throws Exception {
    Cliente cliente = new Cliente();
    cliente.setId(1L);
    cliente.setNome("Pedro Galhardi");

    given(repository.findAll()).willReturn(Collections.singletonList(cliente));

    mockMvc.perform(get("/api/clientes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].nome").value("Pedro Galhardi"));
  }

  @Test
  public void testAddCliente() throws Exception {
    String clienteJson = "{\"nome\":\"Maria Clara\", \"cpf\":\"12345678901\", \"email\":\"Maria@example.com\"}";

    mockMvc.perform(post("/api/clientes")
        .contentType("application/json")
        .content(clienteJson))
        .andExpect(status().isOk());

    verify(repository, times(1)).save(any(Cliente.class));
  }

  @Test
  public void testUpdateCliente_Success() throws Exception {
    Cliente cliente = new Cliente();
    cliente.setId(1L);
    cliente.setNome("Pedro Galhardi");

    given(repository.findById(1L)).willReturn(Optional.of(cliente));

    String updatedClienteJson = "{\"id\":1,\"nome\":\"Mauricio\",\"cpf\":\"12345678900\",\"email\":\"Mauricio@example.com\"}";

    mockMvc.perform(put("/api/clientes/1")
        .contentType("application/json")
        .content(updatedClienteJson))
        .andExpect(status().isOk());

    verify(repository, times(1)).save(any(Cliente.class));
  }

  @Test
  public void testUpdateCliente_NotFound() throws Exception {
    given(repository.findById(1L)).willReturn(Optional.empty());

    String updatedClienteJson = "{\"id\":1,\"nome\":\"Mauricio\",\"cpf\":\"12345678900\",\"email\":\"Mauricio@example.com\"}";

    mockMvc.perform(put("/api/clientes/1")
        .contentType("application/json")
        .content(updatedClienteJson))
        .andExpect(status().isNotAcceptable());
  }

  @Test
  public void testDeleteCliente_Success() throws Exception {
    Cliente cliente = new Cliente();
    cliente.setId(1L);

    given(repository.findById(1L)).willReturn(Optional.of(cliente));

    mockMvc.perform(delete("/api/clientes/1"))
        .andExpect(status().isNoContent());

    verify(repository, times(1)).delete(any(Cliente.class));
  }

  @Test
  public void testDeleteCliente_NotFound() throws Exception {
    given(repository.findById(1L)).willReturn(Optional.empty());

    mockMvc.perform(delete("/api/clientes/1"))
        .andExpect(status().isNotFound());
  }
}
