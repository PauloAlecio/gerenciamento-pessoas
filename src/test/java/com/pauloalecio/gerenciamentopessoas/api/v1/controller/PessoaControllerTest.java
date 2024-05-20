package com.pauloalecio.gerenciamentopessoas.api.v1.controller;

import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.EnderecoInputDisassembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.EnderecoModelAssembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.PessoaInputDisassembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.PessoaModelAssembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.EnderecoModel;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.PessoaModel;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInputId;
import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
import com.pauloalecio.gerenciamentopessoas.domain.service.EnderecoService;
import com.pauloalecio.gerenciamentopessoas.domain.service.PessoaService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PessoaController.class)
class PessoaControllerTest {

  @MockBean
  private PessoaModelAssembler pessoaModelAssembler;

  @MockBean
  private PessoaInputDisassembler pessoaInputDisassembler;

  @MockBean
  private EnderecoModelAssembler enderecoModelAssembler;

  @MockBean
  private EnderecoInputDisassembler enderecoInputDisassembler;

  @MockBean
  private EnderecoService enderecoService;

  @MockBean
  private PessoaService pessoaService;

  @Autowired
  private PessoaModelAssembler pessoModelAssembler;

  @Autowired
  private PessoaInputDisassembler pessoInputDisassembler;

  private PessoaInput pessoaInput;

  private PessoaModel pessoaModel;

  private PessoaModel pessoaAtualizadaModel;

  private Pessoa pessoa;

  private Pessoa pessoaAtualizada;

  private Endereco endereco;

  private EnderecoModel enderecoModel;

  private EnderecoInput enderecoInput;

  @InjectMocks
  private PessoaController pessoaController;

  private MockMvc mockMvc;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    pessoa = new Pessoa();
    pessoa.setId(1L);
    pessoa.setNome("Alex Pas");
    pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
    pessoa.setEndereco_principal_id(1L);

    pessoaModel = new PessoaModel();
    pessoaModel.setId(1L);
    pessoaModel.setNome("Alex Pas");
    pessoaModel.setDataNascimento(LocalDate.of(1990, 1, 1));
    pessoaModel.setEndereco_principal_id(1);

    pessoaInput = new PessoaInput();
    pessoaInput.setNome("Alex Pas");
    pessoaInput.setDataNascimento(LocalDate.of(1990, 1, 1));
    pessoaInput.setEndereco_principal_id(1);

    pessoaAtualizada = new Pessoa();
    pessoaAtualizada.setId(1L);
    pessoaAtualizada.setNome("Paulo Goes");
    pessoaAtualizada.setDataNascimento(LocalDate.of(1992, 3, 4));
    pessoaAtualizada.setEndereco_principal_id(2L);

    pessoaAtualizadaModel = new PessoaModel();
    pessoaAtualizadaModel.setId(1L);
    pessoaAtualizadaModel.setNome("Paulo Goes");
    pessoaAtualizadaModel.setDataNascimento(LocalDate.of(1992, 3, 4));
    pessoaAtualizadaModel.setEndereco_principal_id(2);

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

    mockMvc = MockMvcBuilders.standaloneSetup(pessoaController).build();
  }

  @Test
  void listarPessoas() throws Exception {

    List<Pessoa> pessoas = Collections.singletonList(pessoa);
    CollectionModel<PessoaModel> pessoaModels = CollectionModel.of(Collections.singletonList(pessoaModel));
    when(pessoaService.listarPessoas()).thenReturn(pessoas);
    when(pessoaModelAssembler.toCollectionModel(pessoas)).thenReturn(pessoaModels);

    mockMvc.perform(get("/api/v1/pessoas")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(pessoaModel.getId()))
        .andExpect(jsonPath("$.content[0].nome").value(pessoaModel.getNome()))
        .andExpect(jsonPath("$.content[0].dataNascimento[0]").value(pessoaModel.getDataNascimento().getYear()))
        .andExpect(jsonPath("$.content[0].dataNascimento[1]").value(pessoaModel.getDataNascimento().getMonthValue()))
        .andExpect(jsonPath("$.content[0].dataNascimento[2]").value(pessoaModel.getDataNascimento().getDayOfMonth()))
        .andExpect(jsonPath("$.content[0].endereco_principal_id").value(pessoaModel.getEndereco_principal_id()));

    verify(pessoaService, times(1)).listarPessoas();
    verify(pessoaModelAssembler, times(1)).toCollectionModel(pessoas);

  }

  @Test
  void buscarPessoaPorId() throws Exception {
    Pessoa pessoa = new Pessoa();
    pessoa.setId(1L);

    when(pessoaService.buscarPessoaPorId(anyLong())).thenReturn(pessoa);
    when(pessoModelAssembler.toModel(pessoa)).thenReturn(pessoaModel);

    mockMvc.perform(get("/api/v1/pessoas/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(pessoaModel.getId()))
        .andExpect(jsonPath("$.nome").value(pessoaModel.getNome()))
        .andExpect(jsonPath("$.dataNascimento[0]").value(pessoaModel.getDataNascimento().getYear()))
        .andExpect(jsonPath("$.dataNascimento[1]").value(pessoaModel.getDataNascimento().getMonthValue()))
        .andExpect(jsonPath("$.dataNascimento[2]").value(pessoaModel.getDataNascimento().getDayOfMonth()))
        .andExpect(jsonPath("$.endereco_principal_id").value(pessoaModel.getEndereco_principal_id()));

  }

  @Test
  void criarPessoa() throws Exception {

    when(pessoaInputDisassembler.toDomainObject(any(PessoaInput.class))).thenReturn(pessoa);
    when(pessoaService.criarPessoa(any(Pessoa.class))).thenReturn(pessoa);
    when(pessoModelAssembler.toModel(pessoa)).thenReturn(pessoaModel);

    mockMvc.perform(post("/api/v1/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":1,\"nome\":\"Alex Pas\",\"dataNascimento\":\"1990-01-01\",\"endereco_principal_id\":1,\"_links\":{\"self\":{\"href\":\"http://localhost:8081/gerenciamento-pessoa/api/v1/pessoas/1\"},\"pessoas\":{\"href\":\"http://localhost:8081/gerenciamento-pessoa/api/v1/pessoas\"}}}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(pessoaModel.getId()))
        .andExpect(jsonPath("$.nome").value(pessoaModel.getNome()))
        .andExpect(jsonPath("$.dataNascimento[0]").value(pessoaModel.getDataNascimento().getYear()))
        .andExpect(jsonPath("$.dataNascimento[1]").value(pessoaModel.getDataNascimento().getMonthValue()))
        .andExpect(jsonPath("$.dataNascimento[2]").value(pessoaModel.getDataNascimento().getDayOfMonth()))
        .andExpect(jsonPath("$.endereco_principal_id").value(pessoaModel.getEndereco_principal_id()));


    verify(pessoaInputDisassembler, times(1)).toDomainObject(any(PessoaInput.class));
    verify(pessoaService, times(1)).criarPessoa(any(Pessoa.class));
    verify(pessoaModelAssembler, times(1)).toModel(pessoa);
  }

  @Test
  void atualizarPessoa() throws Exception {
    when(pessoaService.buscarPessoaPorId(1L)).thenReturn(pessoa);
    doNothing().when(pessoInputDisassembler).copyToDomainObject(any(PessoaInputId.class), any(Pessoa.class));
    when(pessoaService.atualizarPessoa(any(Pessoa.class))).thenReturn(pessoaAtualizada);
    when(pessoaModelAssembler.toModel(any(Pessoa.class))).thenReturn(pessoaAtualizadaModel);

    mockMvc.perform(put("/api/v1/pessoas/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":1,\"nome\":\"Paulo Goes\",\"dataNascimento\":\"1992-03-04\",\"endereco_principal_id\":2,\"_links\":{\"self\":{\"href\":\"http://localhost:8081/gerenciamento-pessoa/api/v1/pessoas/1\"},\"pessoas\":{\"href\":\"http://localhost:8081/gerenciamento-pessoa/api/v1/pessoas\"}}}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(pessoaAtualizada.getId()))
        .andExpect(jsonPath("$.nome").value(pessoaAtualizada.getNome()))
        .andExpect(jsonPath("$.dataNascimento[0]").value(pessoaAtualizada.getDataNascimento().getYear()))
        .andExpect(jsonPath("$.dataNascimento[1]").value(pessoaAtualizada.getDataNascimento().getMonthValue()))
        .andExpect(jsonPath("$.dataNascimento[2]").value(pessoaAtualizada.getDataNascimento().getDayOfMonth()))
        .andExpect(jsonPath("$.endereco_principal_id").value(pessoaAtualizada.getEndereco_principal_id()));


    verify(pessoaService, times(1)).buscarPessoaPorId(1L);
    verify(pessoaInputDisassembler, times(1)).copyToDomainObject(any(PessoaInputId.class), any(Pessoa.class));
    verify(pessoaService, times(1)).atualizarPessoa(any(Pessoa.class));
    verify(pessoaModelAssembler, times(1)).toModel(any(Pessoa.class));
  }

  @Test
  void deletarPessoa() throws Exception {
    mockMvc.perform(delete("/api/v1/pessoas/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void adicionarEndereco() throws Exception {

    when(enderecoInputDisassembler.toDomainObject(any(EnderecoInput.class))).thenReturn(endereco);
    when(enderecoService.criarEndereco(any(Endereco.class))).thenReturn(endereco);
    when(enderecoModelAssembler.toModel(endereco)).thenReturn(enderecoModel);
    when(enderecoService.criarEndereco(any(Endereco.class))).thenReturn(endereco);
    when(pessoaService.adicionarEndereco(anyLong(), any(Endereco.class))).thenReturn(endereco);

    mockMvc.perform(post("/api/v1/pessoas/{pessoaId}/endereco", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"logradouro\":\"Rua A\",\"cep\":\"12345-678\",\"numero\":\"10\",\"cidade\":\"Cidade A\",\"estado\":\"AA\"}"))
        .andExpect(status().isCreated());
  }

  @Test
  void editarEndereco() throws Exception {
    when(enderecoService.buscarEnderecoPorId(anyLong())).thenReturn(endereco);
    when(pessoaService.editarEndereco(anyLong(), any(Endereco.class))).thenReturn(endereco);

    mockMvc.perform(put("/api/v1/pessoas/{pessoaId}/endereco/{enderecoId}", 1L, 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"logradouro\": \"Rua A\", \"cep\": \"12345-678\", \"numero\": \"10\", \"cidade\": \"Cidade A\", \"estado\": \"AA\"}"))
        .andExpect(status().isOk());
  }

  @Test
  void buscarEnderecos() throws Exception {
    when(enderecoService.buscarEndereco(anyLong())).thenReturn(Collections.singletonList(new Endereco()));

    mockMvc.perform(get("/api/v1/pessoas/{pessoaId}/enderecos", 1L)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void buscarEnderecoPorPessoaId() throws Exception {
    when(enderecoService.buscarEnderecoPorPessoaId(anyLong(), anyLong())).thenReturn(endereco);

    mockMvc.perform(get("/api/v1/pessoas/{pessoaId}/endereco/{enderecoId}", 1L, 1L)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void definirEnderecoPrincipal() throws Exception {
    mockMvc.perform(post("/api/v1/pessoas/{pessoaId}/endereco-principal", 1L)
            .param("enderecoId", "1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void obterEnderecoPrincipal() throws Exception {
    when(pessoaService.obterEnderecoPrincipal(anyLong())).thenReturn(1L);

    mockMvc.perform(get("/api/v1/pessoas/{pessoaId}/endereco-principal", 1L)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(1L));
  }

  @Test
  void excluirEndereco() throws Exception {
    mockMvc.perform(delete("/api/v1/pessoas/{pessoaId}/endereco/{enderecoId}", 1L, 1L)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }
}
