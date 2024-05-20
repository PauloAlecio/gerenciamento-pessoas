package com.pauloalecio.gerenciamentopessoas.integrationTest;

import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInputId;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInputId;
import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
import com.pauloalecio.gerenciamentopessoas.domain.repository.EnderecoRepository;
import com.pauloalecio.gerenciamentopessoas.domain.repository.PessoaRepository;
import com.pauloalecio.gerenciamentopessoas.domain.service.PessoaService;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PessoaControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private EnderecoRepository enderecoRepository;

  @Autowired
  PessoaService pessoaService;

  @BeforeEach
  public void setup() {
    enderecoRepository.deleteAll();
    pessoaRepository.deleteAll();
    enderecoRepository.flush();
    pessoaRepository.flush();
  }

  @Test
  public void deveListarPessoas() throws Exception {
    Pessoa pessoa = new Pessoa();
    pessoa.setId(1L);
    pessoa.setNome("Alex Pas");
    pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
    pessoa.setEndereco_principal_id(1L);
    pessoaRepository.save(pessoa);

    mockMvc.perform(get("/api/v1/pessoas"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.pessoas").isArray())
        .andExpect(jsonPath("$._embedded.pessoas.length()").value(1));
  }

  @Test
  public void deveBuscarPessoaPorId() throws Exception {
    Pessoa pessoa = new Pessoa();
    pessoa.setId(1L);
    pessoa.setNome("Alex Pas");
    pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
    pessoa.setEndereco_principal_id(1L);
    pessoa = pessoaRepository.save(pessoa);

    mockMvc.perform(get("/api/v1/pessoas/{pessoaId}", pessoa.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(pessoa.getId()));
  }

  @Test
  public void deveCriarPessoa() throws Exception {
    PessoaInput pessoaInput = new PessoaInput();
    pessoaInput.setNome("Alex Pas");
    pessoaInput.setDataNascimento(LocalDate.of(1990, 1, 1));
    pessoaInput.setEndereco_principal_id(1);

    mockMvc.perform(post("/api/v1/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pessoaInput)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").isNotEmpty());
  }

  @Test
  public void deveAtualizarPessoa() throws Exception {
    Pessoa pessoa = new Pessoa();
    pessoa.setId(1L);
    pessoa.setNome("Alex Pas");
    pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
    pessoa.setEndereco_principal_id(1L);
    pessoa = pessoaRepository.save(pessoa);

    PessoaInputId pessoaInputId = new PessoaInputId();
    pessoaInputId.setId(1L);
    pessoaInputId.setNome("Juliana Paes");
    pessoaInputId.setDataNascimento(LocalDate.of(1985, 2, 3));
    pessoaInputId.setEndereco_principal_id(2);

    mockMvc.perform(put("/api/v1/pessoas/{pessoaId}", pessoa.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pessoaInputId)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(pessoa.getId()));
  }

  @Test
  public void deveDeletarPessoa() throws Exception {
    Pessoa pessoa = new Pessoa();
    pessoa.setNome("Juliana Paes");
    pessoa.setDataNascimento(LocalDate.of(1992, 3, 4));
    pessoa = pessoaRepository.save(pessoa);

    mockMvc.perform(delete("/api/v1/pessoas/{pessoaId}", pessoa.getId()))
        .andExpect(status().isNoContent());
  }

  @Test
  public void deveAdicionarEndereco() throws Exception {
    Pessoa pessoa = new Pessoa();
    pessoa.setId(1L);
    pessoa.setNome("Alex Pas");
    pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
    pessoa.setEndereco_principal_id(1L);
    pessoa = pessoaRepository.save(pessoa);

    EnderecoInput enderecoInput = new EnderecoInput();
    enderecoInput.setCep("12345-678");
    enderecoInput.setCidade("Bauru");
    enderecoInput.setEstado("SP");
    enderecoInput.setLogradouro("Rua Joaquim da Silva Martha");
    enderecoInput.setNumero("526");

    mockMvc.perform(post("/api/v1/pessoas/{pessoaId}/endereco", pessoa.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(enderecoInput)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").isNotEmpty());
  }

  @Test
  public void deveAtualizarEndereco() throws Exception {
    Pessoa pessoa = new Pessoa();
    pessoa.setNome("Alex Pas");
    pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
    pessoa.setEndereco_principal_id(1L);
    pessoa = pessoaRepository.save(pessoa);

    Endereco endereco = new Endereco();
    endereco.setCep("12345-678");
    endereco.setCidade("Belo Horizonte");
    endereco.setEstado("MG");
    endereco.setLogradouro("Rua Minas Gerais");
    endereco.setNumero("448");
    endereco = enderecoRepository.save(endereco);

    var inputIdEndereco = endereco.getId();

    EnderecoInputId enderecoInputId = new EnderecoInputId();
    enderecoInputId.setId(inputIdEndereco);
    enderecoInputId.setCep("12345-678");
    enderecoInputId.setCidade("Bauru");
    enderecoInputId.setEstado("SP");
    enderecoInputId.setLogradouro("Rua Joaquim da Silva Martha");
    enderecoInputId.setNumero("526");

    mockMvc.perform(put("/api/v1/pessoas/{pessoaId}/endereco/{enderecoId}", pessoa.getId(), endereco.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(enderecoInputId)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(endereco.getId()));
  }

  @Test
  public void deveBuscarEnderecos() throws Exception {
    Pessoa pessoa = new Pessoa();
    pessoa.setNome("Alex Pas");
    pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
    pessoa.setEndereco_principal_id(1L);
    pessoa = pessoaRepository.save(pessoa);

    Endereco endereco = new Endereco();
    endereco.setCep("12345-678");
    endereco.setCidade("Belo Horizonte");
    endereco.setEstado("MG");
    endereco.setLogradouro("Rua Minas Gerais");
    endereco.setNumero("448");
    endereco.setPessoa(pessoa);
    enderecoRepository.save(endereco);

    mockMvc.perform(get("/api/v1/pessoas/{pessoaId}/enderecos", pessoa.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.enderecos").isArray())
        .andExpect(jsonPath("$._embedded.enderecos.length()").value(1));
  }

  @Test
  public void deveBuscarEnderecoPorPessoaId() throws Exception {
    Pessoa pessoa = new Pessoa();
    pessoa.setNome("Alex Pas");
    pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
    pessoa.setEndereco_principal_id(1L);
    pessoa = pessoaRepository.save(pessoa);

    Endereco endereco = new Endereco();
    endereco.setCep("12345-678");
    endereco.setCidade("Bauru");
    endereco.setEstado("SP");
    endereco.setLogradouro("Rua Joaquim da Silva Martha");
    endereco.setNumero("526");
    endereco.setPessoa(pessoa);
    endereco = enderecoRepository.save(endereco);

    mockMvc.perform(get("/api/v1/pessoas/{pessoaId}/endereco/{enderecoId}", pessoa.getId(), endereco.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(endereco.getId()));
  }

  @Test
  public void deveDefinirEnderecoPrincipal() throws Exception {
    Pessoa pessoa = new Pessoa();
    pessoa.setNome("Juliana Paes");
    pessoa.setDataNascimento(LocalDate.of(1992, 3, 4));
    pessoa = pessoaRepository.save(pessoa);

    Endereco endereco = new Endereco();
    endereco.setCep("12345-678");
    endereco.setCidade("Bauru");
    endereco.setEstado("SP");
    endereco.setLogradouro("Rua Joaquim da Silva Martha");
    endereco.setNumero("526");
    endereco.setPessoa(pessoa);
    endereco = enderecoRepository.save(endereco);

    mockMvc.perform(post("/api/v1/pessoas/{pessoaId}/endereco-principal?enderecoId={enderecoId}", pessoa.getId(), endereco.getId()))
        .andExpect(status().isOk());
  }

  @Test
  public void deveObterEnderecoPrincipal() throws Exception {
    Pessoa pessoa = new Pessoa();
    pessoa.setNome("Juliana Paes");
    pessoa.setDataNascimento(LocalDate.of(1992, 3, 4));
    pessoa = pessoaRepository.save(pessoa);

    Endereco endereco = new Endereco();
    endereco.setCep("12345-678");
    endereco.setCidade("Bauru");
    endereco.setEstado("SP");
    endereco.setLogradouro("Rua Joaquim da Silva Martha");
    endereco.setNumero("526");
    endereco.setPessoa(pessoa);
    endereco = enderecoRepository.save(endereco);

    pessoaService.definirEnderecoPrincipal(pessoa.getId(), endereco.getId());

    mockMvc.perform(get("/api/v1/pessoas/{pessoaId}/endereco-principal", pessoa.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(endereco.getId()));
  }

  @Test
  public void deveExcluirEndereco() throws Exception {
    Pessoa pessoa = new Pessoa();
    pessoa.setNome("Juliana Paes");
    pessoa.setDataNascimento(LocalDate.of(1992, 3, 4));
    pessoa = pessoaRepository.save(pessoa);

    Endereco endereco = new Endereco();
    endereco.setCep("12345-678");
    endereco.setCidade("Bauru");
    endereco.setEstado("SP");
    endereco.setLogradouro("Rua Joaquim da Silva Martha");
    endereco.setNumero("526");
    endereco.setPessoa(pessoa);
    endereco = enderecoRepository.save(endereco);

    mockMvc.perform(delete("/api/v1/pessoas/{pessoaId}/endereco/{enderecoId}", pessoa.getId(), endereco.getId()))
        .andExpect(status().isNoContent());
  }
}

