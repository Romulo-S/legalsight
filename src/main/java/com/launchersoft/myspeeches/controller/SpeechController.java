package com.launchersoft.myspeeches.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.launchersoft.myspeeches.dto.SpeechDTO;
import com.launchersoft.myspeeches.exception.ResourceNotFoundException;
import com.launchersoft.myspeeches.mapper.SpeechMapper;
import com.launchersoft.myspeeches.model.Speech;
import com.launchersoft.myspeeches.repository.SpeechRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/speech")
public class SpeechController {

    @Autowired
    private SpeechMapper speechMapper;

    @Autowired
    private SpeechRepository speechRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSpeechs(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size) {

        try {
            Pageable paging = PageRequest.of(page, size);

            Page<Speech> speechPage = speechRepository.findAll(paging);
            List<SpeechDTO> speechDTOS = speechMapper.modelsToDtos(speechPage.getContent());
            PageImpl<SpeechDTO> dtoPage = new PageImpl<>(speechDTOS, paging, speechPage.getTotalElements());
            return getMapResponseEntity(dtoPage);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<SpeechDTO> getSpeechById(@PathVariable Integer id) {

        Speech speech = speechRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Speech id not exist:" + id));

        SpeechDTO speechDTO = speechMapper.modelToDto(speech);

        return ResponseEntity.ok(speechDTO);
    }

    @GetMapping("/author")
    public ResponseEntity<Map<String, Object>> getSpeechesAuthor(@RequestParam(required = true) String author,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size) {

        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Speech> speechPage = null;

            speechPage = speechRepository.findSpeechByMetadataAuthorNameContaining(author, paging);

            List<SpeechDTO> speechDTOS = speechMapper.modelsToDtos(speechPage.getContent());
            PageImpl<SpeechDTO> dtoPage = new PageImpl<>(speechDTOS, paging, speechPage.getTotalElements());
            return getMapResponseEntity(dtoPage);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/keywords")
    public ResponseEntity<Map<String, Object>> getSpeechesForewords(@RequestParam(required = true) String keywords,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size) {

        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Speech> speechPage = null;

            speechPage = speechRepository.findByMetadata_Keywords(paging, keywords);

            List<SpeechDTO> speechDTOS = speechMapper.modelsToDtos(speechPage.getContent());
            PageImpl<SpeechDTO> dtoPage = new PageImpl<>(speechDTOS, paging, speechPage.getTotalElements());
            return getMapResponseEntity(dtoPage);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/text")
    public ResponseEntity<Map<String, Object>> getSpeechBySnippet(@RequestParam(required = true) String snippet,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size) {

        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Speech> speechPage = null;

            speechPage = speechRepository.findByTextContaining(snippet,paging);

            List<SpeechDTO> speechDTOS = speechMapper.modelsToDtos(speechPage.getContent());
            PageImpl<SpeechDTO> dtoPage = new PageImpl<>(speechDTOS, paging, speechPage.getTotalElements());
            return getMapResponseEntity(dtoPage);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/date")
    public ResponseEntity<Map<String, Object>> getSpeechesDate(
        @RequestParam(value = "localDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size) {

        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Speech> speechPage;

            speechPage = speechRepository.findSpeechByMetadata_Date(localDate, paging);

            List<SpeechDTO> speechDTOS = speechMapper.modelsToDtos(speechPage.getContent());
            PageImpl<SpeechDTO> dtoPage = new PageImpl<>(speechDTOS, paging, speechPage.getTotalElements());
            return getMapResponseEntity(dtoPage);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/date/date_between")
    public ResponseEntity<Map<String, Object>> getSpeechesDateRange(
        @RequestParam(value = "localDateMin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDateMin,
        @RequestParam(value = "localDateMax", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDateMax,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size) {

        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Speech> speechPage = null;

            if (localDateMin != null && localDateMax != null) {
                speechPage = speechRepository.findAllByMetadata_DateBetween(localDateMin, localDateMax, paging);
            } else if (localDateMin != null) {
                speechPage = speechRepository.findAllByMetadata_DateGreaterThanEqual(localDateMin, paging);
            } else if (localDateMax != null) {
                speechPage = speechRepository.findAllByMetadata_DateLessThanEqual(localDateMax, paging);
            }

            List<SpeechDTO> speechDTOS = speechMapper.modelsToDtos(speechPage.getContent());
            PageImpl<SpeechDTO> dtoPage = new PageImpl<>(speechDTOS, paging, speechPage.getTotalElements());
            return getMapResponseEntity(dtoPage);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public Speech saveSpeech(@RequestBody SpeechDTO speechDTO) {
        return speechRepository.save(speechMapper.dtoToModel(speechDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<SpeechDTO> updatePath(@PathVariable Integer id, @RequestBody SpeechDTO speechDTO) {
        Speech speech = speechRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Speech not exist with id: " + id));

        speech.setMetadata(speechDTO.getMetadata());
        speech.setText(speechDTO.getText());

        speechRepository.save(speech);

        speechDTO = speechMapper.modelToDto(speech);

        return ResponseEntity.ok(speechDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteSpeech(@PathVariable Integer id) {

        Speech speech = speechRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Speech not exist with id: " + id));

        speechRepository.delete(speech);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    private ResponseEntity<Map<String, Object>> getMapResponseEntity(PageImpl<SpeechDTO> speechPage) {
        Map<String, Object> response = new HashMap<>();

        response.put("clients", speechPage.getContent());
        response.put("currentPage", speechPage.getNumber());
        response.put("totalItems", speechPage.getTotalElements());
        response.put("totalPages", speechPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

