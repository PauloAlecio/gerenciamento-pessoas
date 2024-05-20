package com.pauloalecio.gerenciamentopessoas.integrationTest;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInputId;
import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import com.pauloalecio.gerenciamentopessoas.domain.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EnderecoControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private EnderecoRepository enderecoRepository;

  @BeforeEach
  public void setup() {
    enderecoRepository.deleteAll();
    enderecoRepository.flush();
  }

  @Test
  public void deveListarEnderecos() throws Exception {
    Endereco endereco = new Endereco();
    endereco.setId(1L);
    endereco.setLogradouro("Rua José Bonifácio");
    endereco.setCep("54321-876");
    endereco.setNumero("20");
    endereco.setCidade("Belo Horizonte");
    endereco.setEstado("MG");
    enderecoRepository.save(endereco);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/enderecos"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.enderecos").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.enderecos.length()").value(1));
  }

  @Test
  public void deveBuscarEnderecoPorId() throws Exception {
    Endereco endereco = new Endereco();
    endereco.setId(1L);
    endereco.setLogradouro("Rua José Bonifácio");
    endereco.setCep("54321-876");
    endereco.setNumero("20");
    endereco.setCidade("Belo Horizonte");
    endereco.setEstado("MG");
    // configure o endereço conforme necessário
    endereco = enderecoRepository.save(endereco);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/enderecos/{id}", endereco.getId()))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(endereco.getId()));
  }

  @Test
  public void deveCriarEndereco() throws Exception {
    EnderecoInput enderecoInput = new EnderecoInput();
    enderecoInput.setLogradouro("Rua A");
    enderecoInput.setCep("12345-678");
    enderecoInput.setNumero("10");
    enderecoInput.setCidade("Cidade A");
    enderecoInput.setEstado("AA");

    mockMvc.perform(post("/api/v1/enderecos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(enderecoInput)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
  }

  @Test
  public void deveAtualizarEndereco() throws Exception {
     Endereco endereco;
     EnderecoInputId enderecoInputId;

    endereco = new Endereco();
    endereco.setId(1L);
    endereco.setLogradouro("Rua José Bonifácio");
    endereco.setCep("54321-876");
    endereco.setNumero("20");
    endereco.setCidade("Belo Horizonte");
    endereco.setEstado("MG");

    enderecoInputId = new EnderecoInputId();
    enderecoInputId.setId(1L);
    enderecoInputId.setLogradouro("Rua A");
    enderecoInputId.setCep("12345-678");
    enderecoInputId.setNumero("10");
    enderecoInputId.setCidade("Cidade A");
    enderecoInputId.setEstado("AA");

    endereco= enderecoRepository.save(endereco);

    mockMvc.perform(put("/api/v1/enderecos/{id}", endereco.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(enderecoInputId)))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(endereco.getId()));
  }

  @Test
  public void deveDeletarEndereco() throws Exception {
    Endereco endereco = new Endereco();
    endereco.setCep("12345-678");
    endereco.setCidade("Cidade Exemplo");
    endereco.setEstado("Estado Exemplo");
    endereco.setLogradouro("Rua Exemplo");
    endereco.setNumero("123");
    endereco = enderecoRepository.save(endereco);

    mockMvc.perform(delete("/api/v1/enderecos/{id}", endereco.getId()))
        .andExpect(status().isNoContent());
  }
}
