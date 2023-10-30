package ru.hisoakende.mycloud.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.hisoakende.mycloud.dto.FileReadDto;
import ru.hisoakende.mycloud.entity.File;
import ru.hisoakende.mycloud.entity.Object;

@Component
public class FileMapper {
    private final ModelMapper modelMapper;

    public FileMapper() {
        modelMapper = new ModelMapper();
    }

    public FileReadDto FileToFileReadDto(File file) {
        Object object = file.getObject();
        return new FileReadDto(
                object.getUuid(),
                file.getName(),
                file.getPath(),
                object.getCreatedAt(),
                object.getUpdatedAt()
        );
    }
}
