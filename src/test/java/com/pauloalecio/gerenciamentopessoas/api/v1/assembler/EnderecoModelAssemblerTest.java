package com.pauloalecio.gerenciamentopessoas.api.v1.assembler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pauloalecio.gerenciamentopessoas.api.v1.ApiLinks;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.EnderecoModel;
import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

@ExtendWith(MockitoExtension.class)
public class EnderecoModelAssemblerTest {

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private ApiLinks apiLinks;

  @InjectMocks
  private EnderecoModelAssembler enderecoModelAssembler;

  @Test
  void testToCollectionModel() {
    Iterable<Endereco> enderecos = new ArrayList<>();

    CollectionModel<EnderecoModel> collectionModel = CollectionModel.of(Collections.emptyList(), Link.of("http://localhost:8081/gerenciamento-pessoa/api/v1/enderecos/2"));

    when(apiLinks.linkToEndereco()).thenReturn(Link.of("http://localhost:8081/gerenciamento-pessoa/api/v1/enderecos/2"));

    CollectionModel<EnderecoModel> result = enderecoModelAssembler.toCollectionModel(enderecos);

    verify(apiLinks).linkToEndereco();

    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
    assertThat(result.getLinks()).hasSize(1);
  }
}