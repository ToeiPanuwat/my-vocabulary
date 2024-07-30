package com.practice.my_vocabulary.service;

import com.practice.my_vocabulary.controller.request.VocabularyRequest;
import com.practice.my_vocabulary.exception.NotFoundException;
import com.practice.my_vocabulary.model.Vocabulary;
import com.practice.my_vocabulary.repository.VocabularyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocabularyServiceImp implements VocabularyService {

    private final VocabularyRepository vocabularyRepository;

    public VocabularyServiceImp(VocabularyRepository vocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;
    }

    @Override
    public List<Vocabulary> getAll() {
        return vocabularyRepository.findAll();
    }

    @Override
    public Vocabulary create(VocabularyRequest request) {
        Vocabulary newVocabulary = new Vocabulary()
                .setEng(request.getEng())
                .setThai(request.getThai())
                .setCategory(request.getCategory())
                .setPronunciation(request.getPronunciation())
                .setDetails(request.getDetails());
        return vocabularyRepository.save(newVocabulary);
    }

    @Override
    public Vocabulary update(VocabularyRequest request, long id) {
        Vocabulary existingVocabulary = vocabularyRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(id));
        existingVocabulary
                .setEng(request.getEng())
                .setThai(request.getThai())
                .setCategory(request.getCategory())
                .setPronunciation(request.getPronunciation())
                .setDetails(request.getDetails());
        return vocabularyRepository.save(existingVocabulary);
    }

    @Override
    public void delete(long id) {
        Vocabulary existingVocabulary = vocabularyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        vocabularyRepository.deleteById(id);
    }
}
