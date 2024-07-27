package com.practice.my_vocabulary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
public class Vocabulary extends BaseModel {

    @Column(nullable = false, length = 60)
    private String eng;

    @Column(nullable = false, length = 60)
    private String thai;

    @Column(nullable = false, length = 60)
    private String category;

    @Column(nullable = false, length = 120)
    private String pronunciation;

    @Column
    private String details;
}
