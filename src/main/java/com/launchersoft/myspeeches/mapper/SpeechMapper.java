package com.launchersoft.myspeeches.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.launchersoft.myspeeches.dto.SpeechDTO;
import com.launchersoft.myspeeches.model.Speech;

@Mapper(componentModel = "spring")
public interface SpeechMapper {

    SpeechMapper INSTANCE = Mappers.getMapper(SpeechMapper.class);

    SpeechDTO modelToDto(Speech speech);
    Speech dtoToModel(SpeechDTO speechDTO);

    List<SpeechDTO> modelsToDtos(List<Speech> speechList);
}
