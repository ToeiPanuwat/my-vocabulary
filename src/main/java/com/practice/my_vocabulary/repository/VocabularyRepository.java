package com.practice.my_vocabulary.repository;

import com.practice.my_vocabulary.model.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
}
