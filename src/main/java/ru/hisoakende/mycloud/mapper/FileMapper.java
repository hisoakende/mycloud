package ru.hisoakende.mycloud.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hisoakende.mycloud.dto.FileCreateDto;
import ru.hisoakende.mycloud.dto.FileReadDto;
import ru.hisoakende.mycloud.entity.File;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.entity.Object;

@Component
public class FileMapper {

    @Autowired
    private ModelMapper modelMapper;

    public File fileCreateDtoToFile(FileCreateDto fileCreateDto) {
        File file = modelMapper.map(fileCreateDto, File.class);
        Folder mockFolder = new Folder();
        mockFolder.setObjectId(fileCreateDto.getFolderId());
        file.setFolder(mockFolder);

        return file;
    }

    public FileReadDto fileToFileReadDto(File file) {
        Object object = file.getObject();
        return new FileReadDto(
                object.getUuid(),
                file.getName(),
                file.getPath(),
                file.getFolderId(),
                object.getCreatedAt(),
                object.getUpdatedAt()
        );
    }
}
