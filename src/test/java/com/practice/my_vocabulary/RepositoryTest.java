package com.practice.my_vocabulary;

import com.practice.my_vocabulary.model.Vocabulary;
import com.practice.my_vocabulary.repository.VocabularyRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //บอก Spring Boot ให้ใช้ฐานข้อมูลในหน่วยความจำที่พบใน classpath สำหรับการทดสอบ
//(replace = Replace.NONE) บอก Spring Boot ว่าจะใช้การตั้งค่าฐานข้อมูลจริงที่กำหนดในไฟล์ .yml
public class RepositoryTest {

    @Autowired
    private VocabularyRepository repository;

    @Order(1)
    @Test
    public void testSaveAndFind() {
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
