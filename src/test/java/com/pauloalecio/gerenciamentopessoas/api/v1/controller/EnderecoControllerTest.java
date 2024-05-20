package com.pauloalecio.gerenciamentopessoas.api.v1.controller;

import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.EnderecoInputDisassembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.EnderecoModelAssembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.EnderecoModel;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInputId;
import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import com.pauloalecio.gerenciamentopessoas.domain.service.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

  @MockBean
  private EnderecoService enderecoService;

  @MockBean
  private EnderecoModelAssembler enderecoModelAssembler;

  @MockBean
  private EnderecoInputDisassembler enderecoInputDisassembler;

  @InjectMocks
  private EnderecoController enderecoController;

  private MockMvc mockMvc;

  private Endereco endereco;
  private EnderecoInput enderecoInput;
  private Endereco enderecoAtualizado;
  private EnderecoModel enderecoModel;
  private EnderecoModel enderecoModelAtualizado;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    endereco = new Endereco();
    endereco.setId(1L);
    endereco.setLogradouro("Rua A");
    endereco.setCep("12345-678");
    endereco.setNumero("10");
    endereco.setCidade("Cidade A");
    endereco.setEstado("AA");

    enderecoModel = new EnderecoModel();
    enderecoModel.setId(1L);
    enderecoModel.setLogradouro("Rua A");
    enderecoModel.setCep("12345-678");
    enderecoModel.setNumero("10");
    enderecoModel.setCidade("Cidade A");
    enderecoModel.setEstado("AA");

    enderecoInput = new EnderecoInput();
    enderecoInput.setLogradouro("Rua A");
    enderecoInput.setCep("12345-678");
    enderecoInput.setNumero("10");
    enderecoInput.setCidade("Cidade A");
    enderecoInput.setEstado("AA");

    enderecoAtualizado = new Endereco();
    enderecoAtualizado.setId(1L);
    enderecoAtualizado.setLogradouro("Rua B");
    enderecoAtualizado.setCep("98765-432");
    enderecoAtualizado.setNumero("20");
    enderecoAtualizado.setCidade("Cidade B");
    enderecoAtualizado.setEstado("BB");

    enderecoModelAtualizado = new EnderecoModel();
    enderecoModelAtualizado.setId(1L);
    enderecoModelAtualizado.setLogradouro("Rua B");
    enderecoModelAtualizado.setCep("98765-432");
    enderecoModelAtualizado.setNumero("20");
    enderecoModelAtualizado.setCidade("Cidade B");
    enderecoModelAtualizado.setEstado("BB");


    mockMvc = MockMvcBuilders.standaloneSetup(enderecoController).build();
  }

  @Test
  void listarEnderecos() throws Exception {
    List<Endereco> enderecos = Collections.singletonList(endereco);
    CollectionModel<EnderecoModel> enderecoModels = CollectionModel.of(Collections.singletonList(enderecoModel));
    when(enderecoService.listarEnderecos()).thenReturn(enderecos);
    when(enderecoModelAssembler.toCollectionModel(enderecos)).thenReturn(enderecoModels);
    mockMvc.perform(get("/api/v1/enderecos")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(enderecoModel.getId()))
        .andExpect(jsonPath("$.content[0].logradouro").value(enderecoModel.getLogradouro()))
        .andExpect(jsonPath("$.content[0].cep").value(enderecoModel.getCep()))
        .andExpect(jsonPath("$.content[0].numero").value(enderecoModel.getNumero()))
        .andExpect(jsonPath("$.content[0].cidade").value(enderecoModel.getCidade()))
        .andExpect(jsonPath("$.content[0].estado").value(enderecoModel.getEstado()));

    verify(enderecoService, times(1)).listarEnderecos();
    verify(enderecoModelAssembler, times(1)).toCollectionModel(enderecos);
  }

  @Test
  void buscarEnderecoPorId() throws Exception {
    when(enderecoService.buscarEnderecoPorId(1L)).thenReturn(endereco);
    when(enderecoModelAssembler.toModel(endereco)).thenReturn(enderecoModel);

    mockMvc.perform(get("/api/v1/enderecos/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(enderecoModel.getId()))
        .andExpect(jsonPath("$.logradouro").value(enderecoModel.getLogradouro()))
        .andExpect(jsonPath("$.cep").value(enderecoModel.getCep()))
        .andExpect(jsonPath("$.numero").value(enderecoModel.getNumero()))
        .andExpect(jsonPath("$.cidade").value(enderecoModel.getCidade()))
        .andExpect(jsonPath("$.estado").value(enderecoModel.getEstado()));

    verify(enderecoService, times(1)).buscarEnderecoPorId(1L);
    verify(enderecoModelAssembler, times(1)).toModel(endereco);
  }

  @Test
  void criarEndereco() throws Exception {
    when(enderecoInputDisassembler.toDomainObject(any(EnderecoInput.class))).thenReturn(endereco);
    when(enderecoService.criarEndereco(any(Endereco.class))).thenReturn(endereco);
    when(enderecoModelAssembler.toModel(endereco)).thenReturn(enderecoModel);

    mockMvc.perform(post("/api/v1/enderecos")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":2,\"logradouro\":\"RuaB\",\"cep\":\"54321-876\",\"numero\":\"200\",\"cidade\":\"RiodeJaneiro\",\"estado\":\"RJ\",\"_links\":{\"self\":{\"href\":\"http://localhost:8081/gerenciamento-pessoa/api/v1/enderecos/2\"},\"enderecos\":{\"href\":\"http://localhost:8081/gerenciamento-pessoa/api/v1/enderecos\"}}}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(enderecoModel.getId()))
        .andExpect(jsonPath("$.logradouro").value(enderecoModel.getLogradouro()))
        .andExpect(jsonPath("$.cep").value(enderecoModel.getCep()))
        .andExpect(jsonPath("$.numero").value(enderecoModel.getNumero()))
        .andExpect(jsonPath("$.cidade").value(enderecoModel.getCidade()))
        .andExpect(jsonPath("$.estado").value(enderecoModel.getEstado()));


    verify(enderecoInputDisassembler, times(1)).toDomainObject(any(EnderecoInput.class));
    verify(enderecoService, times(1)).criarEndereco(any(Endereco.class));
    verify(enderecoModelAssembler, times(1)).toModel(endereco);
  }

  @Test
  void atualizarEndereco() throws Exception {
    when(enderecoService.buscarEnderecoPorId(1L)).thenReturn(endereco);
    doNothing().when(enderecoInputDisassembler).copyToDomainObject(any(EnderecoInputId.class), any(Endereco.class));
    when(enderecoService.atualizarEndereco(any(Endereco.class))).thenReturn(enderecoAtualizado);
    when(enderecoModelAssembler.toModel(any(Endereco.class))).thenReturn(enderecoModelAtualizado);

    mockMvc.perform(put("/api/v1/enderecos/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":2,\"logradouro\":\"RuaB\",\"cep\":\"54321-876\",\"numero\":\"200\",\"cidade\":\"RiodeJaneiro\",\"estado\":\"RJ\",\"_links\":{\"self\":{\"href\":\"http://localhost:8081/gerenciamento-pessoa/api/v1/enderecos/2\"},\"enderecos\":{\"href\":\"http://localhost:8081/gerenciamento-pessoa/api/v1/enderecos\"}}}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(enderecoModelAtualizado.getId()))
        .andExpect(jsonPath("$.logradouro").value(enderecoModelAtualizado.getLogradouro()))
        .andExpect(jsonPath("$.cep").value(enderecoModelAtualizado.getCep()))
        .andExpect(jsonPath("$.numero").value(enderecoModelAtualizado.getNumero()))
        .andExpect(jsonPath("$.cidade").value(enderecoModelAtualizado.getCidade()))
        .andExpect(jsonPath("$.estado").value(enderecoModelAtualizado.getEstado()));

    verify(enderecoService, times(1)).buscarEnderecoPorId(1L);
    verify(enderecoInputDisassembler, times(1)).copyToDomainObject(any(EnderecoInputId.class), any(Endereco.class));
    verify(enderecoService, times(1)).atualizarEndereco(any(Endereco.class));
    verify(enderecoModelAssembler, times(1)).toModel(any(Endereco.class));
  }

  @Test
  void deletarEndereco() throws Exception {
    doNothing().when(enderecoService).deletarEndereco(1L);

    mockMvc.perform(delete("/api/v1/enderecos/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(enderecoService, times(1)).deletarEndereco(1L);
  }
}
