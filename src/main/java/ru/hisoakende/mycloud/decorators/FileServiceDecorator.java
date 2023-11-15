package ru.hisoakende.mycloud.decorators;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hisoakende.mycloud.dto.FileUpdateDto;
import ru.hisoakende.mycloud.entity.File;
import ru.hisoakende.mycloud.exception.InvalidFileException;
import ru.hisoakende.mycloud.exception.NoAccessToAction;
import ru.hisoakende.mycloud.service.FileService;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceDecorator extends ServiceDecorator<File, FileUpdateDto, FileService> {

    public FileServiceDecorator(FileService service) {
        super(service);
    }

    public void uploadFileData(MultipartFile fileData, File file, UUID userId)
            throws NoAccessToAction, InvalidFileException, IOException {
        if (!file.getObject().getOwnerId().equals(userId) && !file.getObject().isWrite()) {
            throw new NoAccessToAction();
        }
        service.uploadFileData(fileData, file);
    }
}
