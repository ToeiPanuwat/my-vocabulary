package com.practice.my_vocabulary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.my_vocabulary.controller.api.VocabularyController;
import com.practice.my_vocabulary.controller.request.VocabularyRequest;
import com.practice.my_vocabulary.model.Vocabulary;
import com.practice.my_vocabulary.service.VocabularyServiceImp;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VocabularyController.class)
public class ControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VocabularyServiceImp vocabularyServiceImp;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreate() throws Exception {
        VocabularyRequest request = new VocabularyRequest()
                .setEng(TestData.eng)
                .setThai(TestData.thai)
                .setCategory(TestData.category)
                .setPronunciation(TestData.pronunciation)
                .setDetails(TestData.details);

        Vocabulary mockVocabulary = new Vocabulary()
                .setEng(TestData.eng)
                .setThai(TestData.thai)
                .setCategory(TestData.category)
                .setPronunciation(TestData.pronunciation)
                .setDetails(TestData.details);

        when(vocabularyServiceImp.create(any(VocabularyRequest.class))).thenReturn(mockVocabulary);

        mockMvc.perform(post("/api/v1/vocabularies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.eng").value(TestData.eng))
                .andExpect(jsonPath("$.thai").value(TestData.thai))
                .andExpect(jsonPath("$.category").value(TestData.category))
                .andExpect(jsonPath("$.pronunciation").value(TestData.pronunciation))
                .andExpect(jsonPath("$.details").value(TestData.details));
    }

    @Test
    void testUpdate() throws Exception {
        VocabularyRequest request = new VocabularyRequest()
                .setEng(TestUpdate.eng)
                .setThai(TestUpdate.thai)
                .setCategory(TestUpdate.category)
                .setPronunciation(TestUpdate.pronunciation)
                .setDetails(TestUpdate.details);

        Vocabulary mockVocabulary = new Vocabulary()
                .setEng(TestUpdate.eng)
                .setThai(TestUpdate.thai)
                .setCategory(TestUpdate.category)
                .setPronunciation(TestUpdate.pronunciation)
                .setDetails(TestUpdate.details);

        when(vocabularyServiceImp.update(any(VocabularyRequest.class), anyLong())).thenReturn(mockVocabulary);

        mockMvc.perform(put("/api/v1/vocabularies/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eng").value(TestUpdate.eng))
                .andExpect(jsonPath("$.thai").value(TestUpdate.thai))
                .andExpect(jsonPath("$.category").value(TestUpdate.category))
                .andExpect(jsonPath("$.pronunciation").value(TestUpdate.pronunciation))
                .andExpect(jsonPath("$.details").value(TestUpdate.details));

    }

    interface TestData {
        String eng = "have";
        String thai = "มี";
        String category = "verb";
        String pronunciation = "แฮฟว์";
        String details = "create";
    }

    interface TestUpdate {
        String eng = "run";
        String thai = "วิ่ง";
        String category = "verb";
        String pronunciation = "รัน";
        String details = "update";
    }
}
