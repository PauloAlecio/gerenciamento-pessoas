package com.pauloalecio.gerenciamentopessoas.api.exceptionhandler;

import static com.pauloalecio.gerenciamentopessoas.api.exceptionhandler.ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.pauloalecio.gerenciamentopessoas.domain.exception.EntidadeEmUsoException;
import com.pauloalecio.gerenciamentopessoas.domain.exception.EntidadeNaoEncontradaException;
import com.pauloalecio.gerenciamentopessoas.domain.exception.NegocioException;
import java.lang.reflect.Method;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

class ApiExceptionHandlerTest {

  private ApiExceptionHandler apiExceptionHandler;
  private MessageSource messageSource;
  private WebRequest webRequest;

  @BeforeEach
  public void setup() {
    messageSource = mock(MessageSource.class);
    apiExceptionHandler = new ApiExceptionHandler(messageSource);
    webRequest = mock(WebRequest.class);
  }

  @Test
  void handleHttpMediaTypeNotAcceptable() {
    HttpMediaTypeNotAcceptableException ex = new HttpMediaTypeNotAcceptableException("Not Acceptable");
    ResponseEntity<Object> response = apiExceptionHandler.handleHttpMediaTypeNotAcceptable(ex, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, webRequest);
    assert response != null;
    assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
  }

  @Test
  void handleUncaught() {
    Exception ex = new Exception(MSG_ERRO_GENERICA_USUARIO_FINAL);
    ResponseEntity<Object> response = apiExceptionHandler.handleUncaught(ex, webRequest);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  void handleNoHandlerFoundException() {
    NoHandlerFoundException ex = new NoHandlerFoundException("GET", "/not-found", new HttpHeaders());
    ResponseEntity<Object> response = apiExceptionHandler.handleNoHandlerFoundException(ex, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    assert response != null;
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }


  @Test
  void handleTypeMismatch() {
    MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException("value", String.class, "name", null, new Exception());
    HttpHeaders headers = new HttpHeaders();
    ResponseEntity<Object> response = apiExceptionHandler.handleTypeMismatch(ex, headers, HttpStatus.BAD_REQUEST, webRequest);

    assert response != null;
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Problem problem = (Problem) response.getBody();
    assert problem != null;
    assertEquals("O parâmetro de URL 'name' recebeu o valor 'value', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo String.", problem.getDetail());
  }

  @Test
  void handleHttpMessageNotReadable_InvalidFormatException() {
    InvalidFormatException ex = new InvalidFormatException(null, "Invalid format", "value", String.class);
    HttpMessageNotReadableException httpMessageNotReadableException = new HttpMessageNotReadableException("Invalid format", ex, null);

    ResponseEntity<Object> response = apiExceptionHandler.handleHttpMessageNotReadable(httpMessageNotReadableException, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

    assert response != null;
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Problem problem = (Problem) response.getBody();
    assert problem != null;
    assertEquals("A propriedade '' recebeu o valor 'value', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo String.", problem.getDetail());
  }

  @Test
  void handleHttpMessageNotReadable_PropertyBindingException() {
    TestPropertyBindingException ex = new TestPropertyBindingException(null, "msg", JsonLocation.NA, null, "", null);
    HttpMessageNotReadableException httpMessageNotReadableException = new HttpMessageNotReadableException("Property binding", ex, null);

    ResponseEntity<Object> response = apiExceptionHandler.handleHttpMessageNotReadable(httpMessageNotReadableException, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

    assert response != null;
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Problem problem = (Problem) response.getBody();
    assert problem != null;
    assertEquals("A propriedade '' não existe. Corrija ou remova essa propriedade e tente novamente.", problem.getDetail());
  }

  @Test
  void handleEntidadeNaoEncontrada() {
    TestEntidadeNaoEncontradaException ex = new TestEntidadeNaoEncontradaException("Entidade não encontrada");
    ResponseEntity<Object> response = (ResponseEntity<Object>) apiExceptionHandler.handleEntidadeNaoEncontrada(ex, webRequest);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    Problem problem = (Problem) response.getBody();
    assert problem != null;
    assertEquals("Entidade não encontrada", problem.getUserMessage());
  }

  @Test
  void handleEntidadeEmUso() {
    EntidadeEmUsoException ex = new EntidadeEmUsoException("Entity in use");
    ResponseEntity<Object> response = (ResponseEntity<Object>) apiExceptionHandler.handleEntidadeEmUso(ex, webRequest);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    Problem problem = (Problem) response.getBody();
    assert problem != null;
    assertEquals("Entity in use", problem.getUserMessage());
  }

  @Test
  void handleNegocio() {
    NegocioException ex = new NegocioException("Business error");
    ResponseEntity<Object> response = (ResponseEntity<Object>) apiExceptionHandler.handleNegocio(ex, webRequest);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Problem problem = (Problem) response.getBody();
    assert problem != null;
    assertEquals("Business error", problem.getUserMessage());
  }

  @Test
  public void handleHttpMediaTypeNotAcceptab() throws Exception {
    HttpMediaTypeNotAcceptableException ex = mock(HttpMediaTypeNotAcceptableException.class);
    HttpHeaders headers = new HttpHeaders();
    WebRequest request = mock(WebRequest.class);

    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleHttpMediaTypeNotAcceptable", HttpMediaTypeNotAcceptableException.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class);
    method.setAccessible(true);

    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, headers, HttpStatusCode.valueOf(406), request);
    assertEquals(HttpStatusCode.valueOf(406), response.getStatusCode());
  }

  @Test
  public void handleValidationInternal() throws Exception {
    Exception ex = mock(Exception.class);
    HttpHeaders headers = new HttpHeaders();
    WebRequest request = mock(WebRequest.class);
    BindingResult bindingResult = mock(BindingResult.class);

    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleValidationInternal", Exception.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class, BindingResult.class);
    method.setAccessible(true);

    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, headers, HttpStatusCode.valueOf(400), request, bindingResult);
    assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
  }


  @Test
  public void handleExceptionInternal_bodyIsNull() throws Exception {
    Exception ex = new Exception("Test Exception");
    HttpHeaders headers = new HttpHeaders();
    WebRequest request = mock(WebRequest.class);
    HttpStatusCode status = HttpStatus.BAD_REQUEST;
    Object body = null;

    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleExceptionInternal", Exception.class, Object.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class);
    method.setAccessible(true);

    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, body, headers, status, request);

    assertEquals(status, response.getStatusCode());
    Problem problem = (Problem) response.getBody();
    assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), problem.getTitle());
    assertEquals(status.value(), problem.getStatus());
    assertEquals(MSG_ERRO_GENERICA_USUARIO_FINAL, problem.getUserMessage());
  }

  @Test
  public void handleExceptionInternal_bodyIsString() throws Exception {
    Exception ex = new Exception("Test Exception");
    HttpHeaders headers = new HttpHeaders();
    WebRequest request = mock(WebRequest.class);
    HttpStatusCode status = HttpStatus.BAD_REQUEST;
    String body = "Test Error";

    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleExceptionInternal", Exception.class, Object.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class);
    method.setAccessible(true);

    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, body, headers, status, request);

    assertEquals(status, response.getStatusCode());
    Problem problem = (Problem) response.getBody();
    assertEquals(body, problem.getTitle());
    assertEquals(status.value(), problem.getStatus());
    assertEquals(MSG_ERRO_GENERICA_USUARIO_FINAL, problem.getUserMessage());
  }

  @Test
  public void handleExceptionInternal_bodyIsNotNullOrString() throws Exception {
    Exception ex = new Exception("Test Exception");
    HttpHeaders headers = new HttpHeaders();
    WebRequest request = mock(WebRequest.class);
    HttpStatusCode status = HttpStatus.BAD_REQUEST;
    Object body = new Problem(1,"Problema","Teste dois","Testando dois","Testando dois Problemas", OffsetDateTime.now(), List.of());

    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleExceptionInternal", Exception.class, Object.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class);
    method.setAccessible(true);

    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, body, headers, status, request);

    assertEquals(status, response.getStatusCode());
    assertEquals(body, response.getBody());
  }


  @Test
  public void handleValidationInternal_fieldError() throws Exception {
    Exception ex = new Exception("Validation failed");
    HttpHeaders headers = new HttpHeaders();
    WebRequest request = mock(WebRequest.class);
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("objectName", "fieldName", "defaultMessage");
    when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));
    when(messageSource.getMessage(fieldError, Locale.getDefault())).thenReturn("errorMessage");

    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleValidationInternal", Exception.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class, BindingResult.class);
    method.setAccessible(true);

    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, headers, HttpStatusCode.valueOf(400), request, bindingResult);
    assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
  }

  @Test
  public void handleHttpMediaTypeNotAcceptabl() throws Exception {
    HttpMediaTypeNotAcceptableException ex = mock(HttpMediaTypeNotAcceptableException.class);
    HttpHeaders headers = new HttpHeaders();
    WebRequest request = mock(WebRequest.class);

    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleHttpMediaTypeNotAcceptable", HttpMediaTypeNotAcceptableException.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class);
    method.setAccessible(true);

    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, headers, HttpStatusCode.valueOf(406), request);
    assertEquals(HttpStatusCode.valueOf(406), response.getStatusCode());
  }


  @Test
  public void handleValidationInternal_withFieldErrors() throws Exception {
    Exception ex = mock(Exception.class);
    HttpHeaders headers = new HttpHeaders();
    HttpStatusCode status = HttpStatusCode.valueOf(400);
    BindingResult bindingResult = mock(BindingResult.class);

    FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
    when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));
    when(messageSource.getMessage(any(), any())).thenReturn("error message");

    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleValidationInternal", Exception.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class, BindingResult.class);
    method.setAccessible(true);

    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, headers, status, webRequest, bindingResult);
    assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
  }

  @Test
  public void handleValidationInternal_noFieldErrors() throws Exception {
    Exception ex = mock(Exception.class);
    HttpHeaders headers = new HttpHeaders();
    HttpStatusCode status = HttpStatusCode.valueOf(400);
    BindingResult bindingResult = mock(BindingResult.class);

    when(bindingResult.getAllErrors()).thenReturn(Collections.emptyList());

    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleValidationInternal", Exception.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class, BindingResult.class);
    method.setAccessible(true);

    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, headers, status, webRequest, bindingResult);
    assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
  }


  //  @Test
//  public void handleMethodArgumentNotValid_singleFieldError() throws Exception {
//    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
//    BindingResult bindingResult = mock(BindingResult.class);
//    FieldError fieldError = new FieldError("objectName", "fieldName", "defaultMessage");
//    when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));
//    when(ex.getBindingResult()).thenReturn(bindingResult);
//    when(messageSource.getMessage(fieldError, Locale.getDefault())).thenReturn("errorMessage");
//
//    HttpHeaders headers = new HttpHeaders();
//    WebRequest request = mock(WebRequest.class);
//
//    ResponseEntity<Object> response = apiExceptionHandler.handleMethodArgumentNotValid(ex, headers, HttpStatusCode.valueOf(400), request);
//
//    assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
//    // Add more assertions to verify the problem details
//  }


//  @Test
//  public void handleMethodArgumentNotValid_withErrors2() throws Exception {
//    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
//    BindingResult bindingResult = mock(BindingResult.class);
//    when(ex.getBindingResult()).thenReturn(bindingResult);
//    when(bindingResult.hasErrors()).thenReturn(true);
//
//    HttpHeaders headers = new HttpHeaders();
//    HttpStatusCode status = HttpStatusCode.valueOf(400);
//
//    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleMethodArgumentNotValid", MethodArgumentNotValidException.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class);
//    method.setAccessible(true);
//
//    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, headers, status, webRequest);
//    assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
//  }

//  @Test
//  public void handleMethodArgumentNotValid_noErrors2() throws Exception {
//    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
//    BindingResult bindingResult = mock(BindingResult.class);
//    when(ex.getBindingResult()).thenReturn(bindingResult);
//    when(bindingResult.hasErrors()).thenReturn(false);
//
//    HttpHeaders headers = new HttpHeaders();
//    HttpStatusCode status = HttpStatusCode.valueOf(400);
//
//    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleMethodArgumentNotValid", MethodArgumentNotValidException.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class);
//    method.setAccessible(true);
//
//    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, headers, status, webRequest);
//    assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
//  }

  //  @Test
//  public void handleMethodArgumentNotValid_withErrors() throws Exception {
//    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
//    BindingResult bindingResult = mock(BindingResult.class);
//    when(ex.getBindingResult()).thenReturn(bindingResult);
//    when(bindingResult.hasErrors()).thenReturn(true);
//
//    HttpHeaders headers = new HttpHeaders();
//    HttpStatusCode status = HttpStatusCode.valueOf(400);
//
//    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleMethodArgumentNotValid", MethodArgumentNotValidException.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class);
//    method.setAccessible(true);
//
//    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, headers, status, webRequest);
//    assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
//  }

//  @Test
//  public void handleMethodArgumentNotValid_noErrors() throws Exception {
//    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
//    BindingResult bindingResult = mock(BindingResult.class);
//    when(ex.getBindingResult()).thenReturn(bindingResult);
//    when(bindingResult.hasErrors()).thenReturn(false);
//
//    HttpHeaders headers = new HttpHeaders();
//    HttpStatusCode status = HttpStatusCode.valueOf(400);
//
//    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleMethodArgumentNotValid", MethodArgumentNotValidException.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class);
//    method.setAccessible(true);
//
//    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, headers, status, webRequest);
//    assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
//  }

//  @Test
//  public void handleMethodArgumentNotValid() throws Exception {
//    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
//    BindingResult bindingResult = mock(BindingResult.class);
//    when(ex.getBindingResult()).thenReturn(bindingResult);
//
//
//    HttpHeaders headers = new HttpHeaders();
//    WebRequest request = mock(WebRequest.class);
//
//    Method method = ApiExceptionHandler.class.getDeclaredMethod("handleMethodArgumentNotValid", MethodArgumentNotValidException.class, HttpHeaders.class, HttpStatusCode.class, WebRequest.class);
//    method.setAccessible(true);
//
//    ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(apiExceptionHandler, ex, headers, HttpStatusCode.valueOf(400), request);
//    assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
//  }


  // Classe de exceção concreta para simular PropertyBindingException
  static class TestPropertyBindingException extends PropertyBindingException {
    protected TestPropertyBindingException(JsonParser p, String msg,
        JsonLocation loc, Class<?> referringClass, String propName,
        Collection<Object> propertyIds) {
      super(p, msg, loc, referringClass, propName, propertyIds);
    }
  }

  // Classe de exceção concreta para simular EntidadeNaoEncontradaException
  static class TestEntidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
    public TestEntidadeNaoEncontradaException(String mensagem) {
      super(mensagem);
    }
  }
}