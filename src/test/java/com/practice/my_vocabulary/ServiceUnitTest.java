package com.practice.my_vocabulary;

import com.practice.my_vocabulary.controller.request.VocabularyRequest;
import com.practice.my_vocabulary.exception.NotFoundException;
import com.practice.my_vocabulary.model.Vocabulary;
import com.practice.my_vocabulary.repository.VocabularyRepository;
import com.practice.my_vocabulary.service.VocabularyServiceImp;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceUnitTest {
    @Mock
    private VocabularyRepository repository;

    @InjectMocks
    private VocabularyServiceImp serviceImp;

    @Test
    void testCreate() {
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

        when(repository.save(any(Vocabulary.class))).thenReturn(mockVocabulary);

        Vocabulary vocabulary = serviceImp.create(request);

        assertNotNull(vocabulary);
        assertNotNull(vocabulary.getId());
        assertEquals(TestData.eng, vocabulary.getEng());
        assertEquals(TestData.thai, vocabulary.getThai());
        assertEquals(TestData.category, vocabulary.getCategory());
        assertEquals(TestData.pronunciation, vocabulary.getPronunciation());
        assertEquals(TestData.details, vocabulary.getDetails());
    }

    @Test
    public void testFindByIdNotFound() {
        VocabularyRequest request = new VocabularyRequest()
                .setEng(TestUpdate.eng)
                .setThai(TestUpdate.thai)
                .setCategory(TestUpdate.category)
                .setPronunciation(TestUpdate.pronunciation)
                .setDetails(TestUpdate.details);

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> serviceImp.update(request, TestData.id));
    }

    @Test
    void testUpdate() {
        VocabularyRequest request = new VocabularyRequest()
                .setEng(TestUpdate.eng)
                .setThai(TestUpdate.thai)
                .setCategory(TestUpdate.category)
                .setPronunciation(TestUpdate.pronunciation)
                .setDetails(TestUpdate.details);

        Vocabulary mockExistingVocabulary = new Vocabulary();
        mockExistingVocabulary.setId(TestData.id);
        mockExistingVocabulary.setEng(TestData.eng);
        mockExistingVocabulary.setThai(TestData.thai);
        mockExistingVocabulary.setCategory(TestData.category);
        mockExistingVocabulary.setPronunciation(TestData.pronunciation);
        mockExistingVocabulary.setDetails(TestData.details);

        Vocabulary mockUpdateVocabulary = new Vocabulary();
        mockUpdateVocabulary.setId(TestData.id);
        mockUpdateVocabulary.setEng(TestUpdate.eng);
        mockUpdateVocabulary.setThai(TestUpdate.thai);
        mockUpdateVocabulary.setCategory(TestUpdate.category);
        mockUpdateVocabulary.setPronunciation(TestUpdate.pronunciation);
        mockUpdateVocabulary.setDetails(TestUpdate.details);

        when(repository.findById(anyLong())).thenReturn(Optional.of(mockExistingVocabulary));
        when(repository.save(any(Vocabulary.class))).thenReturn(mockUpdateVocabulary);

        Vocabulary vocabulary = serviceImp.update(request, TestData.id);

        assertNotNull(vocabulary);
        assertEquals(TestUpdate.eng, vocabulary.getEng());
        assertEquals(TestUpdate.thai, vocabulary.getThai());
        assertEquals(TestUpdate.category, vocabulary.getCategory());
        assertEquals(TestUpdate.pronunciation, vocabulary.getPronunciation());
        assertEquals(TestUpdate.details, vocabulary.getDetails());
    }

    interface TestData {
        long id = 1L;
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

//  testCreate()
//        Vocabulary mockVocabulary = new Vocabulary()
//                .setEng(TestData.eng)
//                .setThai(TestData.thai)
//                .setCategory(TestData.category)
//                .setPronunciation(TestData.pronunciation)
//                .setDetails(TestData.details);

//        when(repository.save(any(Vocabulary.class))).then(invocation -> {
//            Vocabulary savedVocabulary = invocation.getArgument(0);
//            savedVocabulary.setId(1L);
//            return savedVocabulary;
//        });