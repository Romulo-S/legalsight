package com.launchersoft.myspeeches.dto;

import com.launchersoft.myspeeches.model.Metadata;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import jakarta.persistence.Embedded;

@Data
@NoArgsConstructor
public class SpeechDTO {

    @NonNull
    private Integer id;

    @NonNull
    private String text;

    @Embedded
    @NonNull
    private Metadata metadata;
}
