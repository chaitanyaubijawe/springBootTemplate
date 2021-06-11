package com.smartdubai.assesment.eShop;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractControllerTest extends AbstractTest {
  @Autowired protected ObjectMapper objectMapper;
  @Autowired protected WebApplicationContext wac;
  private MockMvc mockMvc;

  @BeforeEach
  public void setupMvcMock() {

    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

// Helper methods
  protected abstract String url();

  <T> List<T> listPageContent(Class<T> clazz, Object... urlPaths) throws Exception {

    final MvcResult res =
        mockMvc
            .perform(MockMvcRequestBuilders.get(fullUrl(urlPaths)))
            .andExpect(status().isOk())
            .andReturn();
    return listContent(clazz, "content", res);
  }

  <T> List<T> listWithoutPageContent(Class<T> clazz, Object... urlPaths) throws Exception {

    final MvcResult res =
        mockMvc
            .perform(MockMvcRequestBuilders.get(fullUrl(urlPaths)))
            .andExpect(status().isOk())
            .andReturn();
    JsonNode contentNode =
        objectMapper.readValue(res.getResponse().getContentAsByteArray(), JsonNode.class);
    return objectMapper.convertValue(
        contentNode, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
  }

  <T> List<T> listContent(Class<T> clazz, String contentNodeName, MvcResult res) throws Exception {

    final JsonNode listNode =
        objectMapper.readValue(res.getResponse().getContentAsByteArray(), JsonNode.class);
    JsonNode contentNode = listNode;
    if (contentNodeName != null) {
      contentNode = listNode.findPath(contentNodeName);
    }

    return objectMapper.convertValue(
        contentNode, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
  }

  protected <T> T get(Class<T> clazz, Object... urlPaths) throws Exception {

    final MvcResult res =
        mockMvc
            .perform(MockMvcRequestBuilders.get(fullUrl(urlPaths)))
            .andExpect(status().isOk())
            .andReturn();
    return objectMapper.readValue(res.getResponse().getContentAsByteArray(), clazz);
  }

  String fullUrl(Object... urlPaths) {

    final String addedUrl =
        Arrays.stream(urlPaths).map(Object::toString).collect(Collectors.joining("/"));
    String postfix = url().endsWith("/") ? "" : "/";
    return url() + postfix + addedUrl;
  }

  @SuppressWarnings("unchecked")
  <T> T post(final T bodyObj, final Object... urlPaths) throws Exception {

    final MvcResult result = post(bodyObj, status().isCreated(), urlPaths).andReturn();
    return readEntity(result, (Class<T>) bodyObj.getClass());
  }

  protected <T> T post(
      final Object bodyObj, Class<T> respClass, ResultMatcher status, final Object... urlPaths)
      throws Exception {

    final MvcResult result = post(bodyObj, status, urlPaths).andReturn();
    return parseTo(result, respClass);
  }

  <T> T post(
      final Object bodyObj,
      Class<T> respClass,
      ResultMatcher status,
      MultiValueMap<String, String> params,
      final Object... urlPaths)
      throws Exception {

    final MvcResult result = post(bodyObj, status, params, urlPaths).andReturn();
    return parseTo(result, respClass);
  }

  protected <T> T post(final Object bodyObj, Class<T> respClass, final Object... urlPaths)
      throws Exception {

    return post(bodyObj, respClass, status().isOk(), urlPaths);
  }

  protected <T> T patch(final Object bodyObj, Class<T> respClass, final Object... urlPaths)
      throws Exception {

    return patch(bodyObj, respClass, status().isOk(), urlPaths);
  }

  protected <T> T put(final Object bodyObj, Class<T> respClass, final Object... urlPaths)
      throws Exception {

    return put(bodyObj, respClass, status().isOk(), urlPaths);
  }

  protected <T> T put(
      final Object bodyObj, Class<T> respClass, ResultMatcher status, final Object... urlPaths)
      throws Exception {

    final MvcResult result = put(bodyObj, status, urlPaths).andReturn();
    return parseTo(result, respClass);
  }

  protected <T> T patch(
      final Object bodyObj, Class<T> respClass, ResultMatcher status, final Object... urlPaths)
      throws Exception {

    final MvcResult result = patch(bodyObj, status, urlPaths).andReturn();
    return parseTo(result, respClass);
  }

  <T> ResultActions patch(final T bodyObj, ResultMatcher status, final Object... urlPaths)
      throws Exception {

    return mockMvc
        .perform(
            MockMvcRequestBuilders.patch(fullUrl(urlPaths))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bodyObj)))
        .andExpect(status);
  }

  <T> ResultActions put(final T bodyObj, ResultMatcher status, final Object... urlPaths)
      throws Exception {

    return mockMvc
        .perform(
            MockMvcRequestBuilders.put(fullUrl(urlPaths))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bodyObj)))
        .andExpect(status);
  }

  <T> T post(
      final Object bodyObj,
      Class<T> respClass,
      MultiValueMap<String, String> params,
      final Object... urlPaths)
      throws Exception {

    return post(bodyObj, respClass, status().isOk(), params, urlPaths);
  }

  protected <T> T post(
      final Object bodyObj,
      Class<T> respClass,
      RequestPostProcessor requestPostProcessor,
      final Object... urlPaths)
      throws Exception {

    final MvcResult result =
        post(bodyObj, status().isCreated(), requestPostProcessor, urlPaths).andReturn();
    return parseTo(result, respClass);
  }

  <T> ResultActions post(final T bodyObj, ResultMatcher status, final Object... urlPaths)
      throws Exception {

    return mockMvc
        .perform(
            MockMvcRequestBuilders.post(fullUrl(urlPaths))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bodyObj)))
        .andExpect(status);
  }

  <T> ResultActions post(
      final T bodyObj,
      ResultMatcher status,
      MultiValueMap<String, String> params,
      final Object... urlPaths)
      throws Exception {

    return mockMvc
        .perform(
            MockMvcRequestBuilders.post(fullUrl(urlPaths))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bodyObj))
                .params(params))
        .andExpect(status);
  }

  <T> ResultActions post(
      final T bodyObj,
      ResultMatcher status,
      RequestPostProcessor requestPostProcessor,
      final Object... urlPaths)
      throws Exception {

    return mockMvc
        .perform(
            MockMvcRequestBuilders.post(fullUrl(urlPaths))
                .with(requestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bodyObj)))
        .andExpect(status);
  }

  <T> T readEntity(MvcResult result, Class<T> entityClass) throws IOException {
    /*        Assertions(value, notNullValue());*/
    return parseTo(result, entityClass);
  }

  <T> T parseTo(MvcResult result, Class<T> entityClass) throws IOException {

    return objectMapper.readValue(result.getResponse().getContentAsString(), entityClass);
  }

  <T> T fetchSearchCriteria(Class<T> clazz, String contentNodeName, Object... urlPaths)
      throws Exception {

    final MvcResult res =
        mockMvc
            .perform(MockMvcRequestBuilders.get(fullUrl(urlPaths)))
            .andExpect(status().isOk())
            .andReturn();
    final JsonNode listNode =
        objectMapper.readValue(res.getResponse().getContentAsByteArray(), JsonNode.class);
    JsonNode contentNode = listNode;
    if (contentNodeName != null) {
      contentNode = listNode.findPath(contentNodeName);
    }

    return objectMapper.convertValue(contentNode, clazz);
  }

  protected <T> T delete(Class<T> clazz, Object... urlPaths) throws Exception {

    final MvcResult res =
        mockMvc
            .perform(MockMvcRequestBuilders.delete(fullUrl(urlPaths)))
            .andExpect(status().isOk())
            .andReturn();
    return objectMapper.readValue(res.getResponse().getContentAsByteArray(), clazz);
  }
}
