package com.practice.my_vocabulary;

import com.practice.my_vocabulary.model.Vocabulary;
import com.practice.my_vocabulary.repository.VocabularyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//(replace = Replace.NONE) บอก Spring Boot ว่าจะใช้การตั้งค่าฐานข้อมูลจริงที่กำหนดในไฟล์ .yml
public class RepositoryTest {

    @Autowired
    private VocabularyRepository repository;


    @Test
    public void testCreate() {
        Vocabulary vocabulary = new Vocabulary()
                .setEng(TestData.eng)
                .setThai(TestData.thai)
                .setCategory(TestData.category)
                .setPronunciation(TestData.pronunciation)
                .setDetails(TestData.details);

        Vocabulary savedVocabulary = repository.save(vocabulary);

        assertNotNull(savedVocabulary);
        assertNotNull(savedVocabulary.getId());

        assertEquals(TestData.eng, savedVocabulary.getEng());
        assertEquals(TestData.thai, savedVocabulary.getThai());
        assertEquals(TestData.category, savedVocabulary.getCategory());
        assertEquals(TestData.pronunciation, savedVocabulary.getPronunciation());
        assertEquals(TestData.details, savedVocabulary.getDetails());

        Vocabulary foundVocabulary = repository.findById(savedVocabulary.getId()).orElse(null);
        assertNotNull(foundVocabulary);

        assertEquals(TestData.eng, foundVocabulary.getEng());
        assertEquals(TestData.thai, foundVocabulary.getThai());
        assertEquals(TestData.category, foundVocabulary.getCategory());
        assertEquals(TestData.pronunciation, foundVocabulary.getPronunciation());
        assertEquals(TestData.details, foundVocabulary.getDetails());
    }

    @Test
    public void testUpdate() {
        Vocabulary vocabulary = new Vocabulary()
                .setEng(TestData.eng)
                .setThai(TestData.thai)
                .setCategory(TestData.category)
                .setPronunciation(TestData.pronunciation)
                .setDetails(TestData.details);

        Vocabulary savedVocabulary = repository.save(vocabulary);

        Vocabulary existingVocabulary = repository.findById(savedVocabulary.getId()).orElse(null);
        assertNotNull(existingVocabulary);

        existingVocabulary
                .setEng(TestUpdate.eng)
                .setThai(TestUpdate.thai)
                .setCategory(TestUpdate.category)
                .setPronunciation(TestUpdate.pronunciation)
                .setDetails(TestUpdate.details);

        Vocabulary UpdatedVocabulary = repository.save(existingVocabulary);

        assertNotNull(UpdatedVocabulary);

        assertEquals(vocabulary.getId(), UpdatedVocabulary.getId());
        assertEquals(TestUpdate.eng, UpdatedVocabulary.getEng());
        assertEquals(TestUpdate.thai, UpdatedVocabulary.getThai());
        assertEquals(TestUpdate.category, UpdatedVocabulary.getCategory());
        assertEquals(TestUpdate.pronunciation, UpdatedVocabulary.getPronunciation());
        assertEquals(TestUpdate.details, UpdatedVocabulary.getDetails());
    }

    @Test
    public void testDelete() {
        Vocabulary vocabulary = new Vocabulary()
                .setEng(TestData.eng)
                .setThai(TestData.thai)
                .setCategory(TestData.category)
                .setPronunciation(TestData.pronunciation)
                .setDetails(TestData.details);

        Vocabulary savedVocabulary = repository.save(vocabulary);
        long id = savedVocabulary.getId();

        Vocabulary existingVocabulary = repository.findById(id).orElse(null);

        repository.deleteById(id);

        Optional<Vocabulary> optDelete = repository.findById(id);

        assertNotNull(savedVocabulary);
        assertNotNull(existingVocabulary);
        assertTrue(optDelete.isEmpty());
    }

    @Test
    public void testSearchEng() {
        Vocabulary vocabulary = new Vocabulary()
                .setEng(TestData.eng)
                .setThai(TestData.thai)
                .setCategory(TestData.category)
                .setPronunciation(TestData.pronunciation)
                .setDetails(TestData.details);

        Vocabulary savedVocabulary = repository.save(vocabulary);
        List<Vocabulary> foundVocabularies = repository.findByEng(TestData.eng);

        assertNotNull(savedVocabulary);
        assertEquals(1, foundVocabularies.size());
        Vocabulary found = foundVocabularies.get(0);
        assertEquals(TestData.eng, found.getEng());
        assertEquals(TestData.thai, found.getThai());
        assertEquals(TestData.category, found.getCategory());
        assertEquals(TestData.pronunciation, found.getPronunciation());
        assertEquals(TestData.details, found.getDetails());
    }
    @Test
    public void testSearchThai() {
        Vocabulary vocabulary = new Vocabulary()
                .setEng(TestData.eng)
                .setThai(TestData.thai)
                .setCategory(TestData.category)
                .setPronunciation(TestData.pronunciation)
                .setDetails(TestData.details);

        Vocabulary savedVocabulary = repository.save(vocabulary);
        List<Vocabulary> foundVocabularies = repository.findByThai(TestData.thai);

        assertNotNull(savedVocabulary);
        assertEquals(1, foundVocabularies.size());
        Vocabulary found = foundVocabularies.get(0);
        assertEquals(TestData.eng, found.getEng());
        assertEquals(TestData.thai, found.getThai());
        assertEquals(TestData.category, found.getCategory());
        assertEquals(TestData.pronunciation, found.getPronunciation());
        assertEquals(TestData.details, found.getDetails());
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
