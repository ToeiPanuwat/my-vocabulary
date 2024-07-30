package com.practice.my_vocabulary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.my_vocabulary.controller.api.VocabularyController;
import com.practice.my_vocabulary.controller.request.VocabularyRequest;
import com.practice.my_vocabulary.model.Vocabulary;
import com.practice.my_vocabulary.service.VocabularyServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VocabularyController.class)
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
public class ControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private VocabularyServiceImp serviceImp;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    public void testCreate() throws Exception {
        VocabularyRequest request = new VocabularyRequest()
                .setEng(TestData.eng)
                .setThai(TestData.thai)
                .setCategory(TestData.category)
                .setPronunciation(TestData.pronunciation)
                .setDetails(TestData.details);

        Vocabulary mockVocabulary = new Vocabulary();
        mockVocabulary.setId(TestData.id);
        mockVocabulary.setEng(TestData.eng);
        mockVocabulary.setThai(TestData.thai);
        mockVocabulary.setCategory(TestData.category);
        mockVocabulary.setPronunciation(TestData.pronunciation);
        mockVocabulary.setDetails(TestData.details);

        when(serviceImp.create(any(VocabularyRequest.class))).thenReturn(mockVocabulary);

        mockMvc.perform(post("/api/v1/vocabularies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.eng").value(TestData.eng))
                .andExpect(jsonPath("$.thai").value(TestData.thai))
                .andExpect(jsonPath("$.category").value(TestData.category))
                .andExpect(jsonPath("$.pronunciation").value(TestData.pronunciation))
                .andExpect(jsonPath("$.details").value(TestData.details))
                .andDo(document("create-vocabulary",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    public void testUpdate() throws Exception {
        VocabularyRequest request = new VocabularyRequest()
                .setEng(TestUpdate.eng)
                .setThai(TestUpdate.thai)
                .setCategory(TestUpdate.category)
                .setPronunciation(TestUpdate.pronunciation)
                .setDetails(TestUpdate.details);

        Vocabulary mockVocabulary = new Vocabulary();
        mockVocabulary.setId(TestUpdate.id);
        mockVocabulary.setEng(TestUpdate.eng);
        mockVocabulary.setThai(TestUpdate.thai);
        mockVocabulary.setCategory(TestUpdate.category);
        mockVocabulary.setPronunciation(TestUpdate.pronunciation);
        mockVocabulary.setDetails(TestUpdate.details);

        when(serviceImp.update(any(VocabularyRequest.class), anyLong())).thenReturn(mockVocabulary);

        mockMvc.perform(put("/api/v1/vocabularies/{id}", TestData.id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eng").value(TestUpdate.eng))
                .andExpect(jsonPath("$.thai").value(TestUpdate.thai))
                .andExpect(jsonPath("$.category").value(TestUpdate.category))
                .andExpect(jsonPath("$.pronunciation").value(TestUpdate.pronunciation))
                .andExpect(jsonPath("$.details").value(TestUpdate.details))
                .andDo(document("update-vocabulary",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(serviceImp).delete(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/vocabularies/{id}", TestData.id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("delete-vocabulary",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));

        verify(serviceImp).delete(TestData.id);
    }

    @Test
    public void testSearchEng() throws Exception {
        Vocabulary mockVocabulary = new Vocabulary();
        mockVocabulary.setId(TestData.id);
        mockVocabulary.setEng(TestData.eng);
        mockVocabulary.setThai(TestData.thai);
        mockVocabulary.setCategory(TestData.category);
        mockVocabulary.setPronunciation(TestData.pronunciation);
        mockVocabulary.setDetails(TestData.details);

        when(serviceImp.getVocabularyByEng(anyString())).thenReturn(List.of(mockVocabulary));

        mockMvc.perform(get("/api/v1/vocabularies/search")
                .param("eng", TestData.eng)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eng").value(TestData.eng))
                .andExpect(jsonPath("$[0].thai").value(TestData.thai))
                .andExpect(jsonPath("$[0].category").value(TestData.category))
                .andExpect(jsonPath("$[0].pronunciation").value(TestData.pronunciation))
                .andExpect(jsonPath("$[0].details").value(TestData.details))
                .andDo(document("search-eng-vocabulary",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    public void testSearchThai() throws Exception {
        Vocabulary mockVocabulary = new Vocabulary();
        mockVocabulary.setId(TestData.id);
        mockVocabulary.setEng(TestData.eng);
        mockVocabulary.setThai(TestData.thai);
        mockVocabulary.setCategory(TestData.category);
        mockVocabulary.setPronunciation(TestData.pronunciation);
        mockVocabulary.setDetails(TestData.details);

        when(serviceImp.getVocabularyByThai(anyString())).thenReturn(List.of(mockVocabulary));
        mockMvc.perform(get("/api/v1/vocabularies/search")
                .param("thai", TestData.thai)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eng").value(TestData.eng))
                .andExpect(jsonPath("$[0].thai").value(TestData.thai))
                .andExpect(jsonPath("$[0].category").value(TestData.category))
                .andExpect(jsonPath("$[0].pronunciation").value(TestData.pronunciation))
                .andExpect(jsonPath("$[0].details").value(TestData.details))
                .andDo(document("search-thai-vocabulary",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    public void testSearchEngNotFound() throws Exception {
        when(serviceImp.getVocabularyByEng(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/vocabularies/search")
                .param("eng", "nonexistent")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testSearchThaiNotFound() throws Exception {
        when(serviceImp.getVocabularyByThai(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/vocabularies/search")
                        .param("thai", "ไม่มี")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    interface TestData {
        Long id = 1L;
        String eng = "have";
        String thai = "มี";
        String category = "verb";
        String pronunciation = "แฮฟว์";
        String details = "test";
    }

    interface TestUpdate {
        Long id = 1L;
        String eng = "run";
        String thai = "วิ่ง";
        String category = "verb";
        String pronunciation = "รัน";
        String details = "test";
    }
}
