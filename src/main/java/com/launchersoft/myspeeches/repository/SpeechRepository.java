package com.launchersoft.myspeeches.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.launchersoft.myspeeches.model.Speech;

public interface SpeechRepository extends JpaRepository<Speech,Integer> {

    Page<Speech> findAll(Pageable pageable);

    Page<Speech> findSpeechByMetadataAuthorNameContaining(String authorName, Pageable pageable);

    Page<Speech> findSpeechByMetadata_Date(LocalDate date, Pageable pageable);

    Page<Speech> findAllByMetadata_DateGreaterThanEqual(LocalDate dateMin, Pageable pageable);

    Page<Speech> findAllByMetadata_DateLessThanEqual(LocalDate dateMax, Pageable pageable);

    Page<Speech> findByMetadata_Keywords(Pageable pageable, String forewords);
    Page<Speech> findByTextContaining(String text, Pageable pageable );


    //findAllByPublicationTimeBetween
    Page<Speech> findAllByMetadata_DateBetween(
        LocalDate publicationTimeStart,
        LocalDate publicationTimeEnd, Pageable pageable);

}
