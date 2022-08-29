package com.launchersoft.myspeeches.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
@Getter
@Setter
public class Metadata {

    @Column(name = "author_name")
    @NotNull
    private String authorName;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @Column(name = "keywords")
    @NotNull
    private String keywords;
}
