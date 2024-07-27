package com.practice.my_vocabulary.service;

import com.practice.my_vocabulary.controller.request.VocabularyRequest;
import com.practice.my_vocabulary.model.Vocabulary;

import java.util.List;

public interface VocabularyService {

    List<Vocabulary> getAll();

    Vocabulary create(VocabularyRequest request);

    Vocabulary update(VocabularyRequest request, long id);
}
