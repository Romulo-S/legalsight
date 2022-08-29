package com.launchersoft.myspeeches;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.launchersoft.myspeeches.dto.SpeechDTO;
import com.launchersoft.myspeeches.model.Metadata;
import com.launchersoft.myspeeches.model.Speech;
import com.launchersoft.myspeeches.repository.SpeechRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpeechRegisterIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SpeechRepository speechRepository;

    @Test
    void getSpeechById() throws Exception {


        Metadata metadata = new Metadata();
        metadata.setAuthorName("Romulo");
        metadata.setKeywords("infraestructure");
        metadata.setDate(LocalDate.of(1992, Month.JULY,20));

        SpeechDTO speechDto = new SpeechDTO();
        speechDto.setId(4);
        speechDto.setText("My Speech");
        speechDto.setMetadata(metadata);


        mockMvc.perform(post("/api/v1/speech")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(speechDto)))
            .andExpect(status().isOk());

        Pageable paging = PageRequest.of(0, 3);

        Page<Speech> speech = speechRepository.findSpeechByMetadata_Date(speechDto.getMetadata().getDate(),paging);
        Speech speechContent = speech.getContent().get(0);
        assertThat(speechContent.getText()).isEqualTo(speechDto.getText());
        assertThat(speechContent.getMetadata().getAuthorName()).isEqualTo(speechDto.getMetadata().getAuthorName());
        assertThat(speechContent.getMetadata().getKeywords()).isEqualTo(speechDto.getMetadata().getKeywords());
        assertThat(speechContent.getMetadata().getDate()).isEqualTo(speechDto.getMetadata().getDate());

    }

}
