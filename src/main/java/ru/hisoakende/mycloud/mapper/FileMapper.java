package ru.hisoakende.mycloud.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {
    private final ModelMapper modelMapper;

    public FileMapper() {
        modelMapper = new ModelMapper();
    }
}
