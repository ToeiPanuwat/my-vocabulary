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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceUnitTest {
    @Mock
    private VocabularyRepository repository;

    @InjectMocks
    private VocabularyServiceImp serviceImp;

    @Test
    public void testCreate() {
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
    public void testUpdate() {
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

        Vocabulary mockUpdate = new Vocabulary();
        mockUpdate.setId(TestUpdate.id);
        mockUpdate.setEng(TestUpdate.eng);
        mockUpdate.setThai(TestUpdate.thai);
        mockUpdate.setCategory(TestUpdate.category);
        mockUpdate.setPronunciation(TestUpdate.pronunciation);
        mockUpdate.setDetails(TestUpdate.details);

        when(repository.findById(anyLong())).thenReturn(Optional.of(mockExistingVocabulary));
        when(repository.save(any(Vocabulary.class))).thenReturn(mockUpdate);

        Vocabulary vocabulary = serviceImp.update(request, TestData.id);

        assertNotNull(vocabulary);
        assertEquals(mockUpdate.getEng(), vocabulary.getEng());
        assertEquals(mockUpdate.getThai(), vocabulary.getThai());
        assertEquals(mockUpdate.getCategory(), vocabulary.getCategory());
        assertEquals(mockUpdate.getPronunciation(), vocabulary.getPronunciation());
        assertEquals(mockUpdate.getDetails(), vocabulary.getDetails());
    }

    @Test
    public void delete() {
        Vocabulary mockDelete = new Vocabulary();
        mockDelete.setId(TestData.id);
        mockDelete.setEng(TestData.eng);
        mockDelete.setThai(TestData.thai);
        mockDelete.setCategory(TestData.category);
        mockDelete.setPronunciation(TestData.pronunciation);
        mockDelete.setDetails(TestData.details);

        when(repository.findById(anyLong())).thenReturn(Optional.of(mockDelete));

        doNothing().when(repository).deleteById(anyLong());

        serviceImp.delete(TestData.id);

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Vocabulary> optDelete = repository.findById(TestData.id);
        assertTrue(optDelete.isEmpty());
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
        long id = 1L;
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