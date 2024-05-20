package com.pauloalecio.gerenciamentopessoas.api.v1.assembler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pauloalecio.gerenciamentopessoas.api.v1.ApiLinks;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.PessoaModel;
import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
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
class PessoaModelAssemblerTest {

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private ApiLinks apiLinks;

  @InjectMocks
  private PessoaModelAssembler pessoaModelAssembler;

  @Test
  void testToCollectionModel() {
    Iterable<Pessoa> pessoas = new ArrayList<>();
    CollectionModel<PessoaModel> collectionModel = CollectionModel.of(Collections.emptyList(), Link.of("http://localhost:8081/gerenciamento-pessoa/api/v1/pessoas/2"));

    when(apiLinks.linkToPessoas()).thenReturn(Link.of("http://localhost:8081/gerenciamento-pessoa/api/v1/pessoas/2"));

    CollectionModel<PessoaModel> result = pessoaModelAssembler.toCollectionModel(pessoas);

    verify(apiLinks).linkToPessoas();

    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
    assertThat(result.getLinks()).hasSize(1);
  }
}