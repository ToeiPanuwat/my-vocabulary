package com.practice.my_vocabulary.repository;

import com.practice.my_vocabulary.model.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {

    List<Vocabulary> findByEng(String eng);

    List<Vocabulary> findByThai(String thai);
}
