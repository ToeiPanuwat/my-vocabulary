package com.practice.my_vocabulary.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class VocabularyRequest {

    @NotBlank
    private String eng;

    @NotBlank
    private String thai;

    @NotBlank
    private String category;

    @NotBlank
    private String pronunciation;

    private String details;
}
