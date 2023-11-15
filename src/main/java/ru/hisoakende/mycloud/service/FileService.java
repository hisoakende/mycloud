package ru.hisoakende.mycloud.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hisoakende.mycloud.dto.FilePatchDto;
import ru.hisoakende.mycloud.entity.File;
import ru.hisoakende.mycloud.entity.Object;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.exception.InvalidDataException;
import ru.hisoakende.mycloud.exception.InvalidFileException;
import ru.hisoakende.mycloud.repository.FileRepository;
import ru.hisoakende.mycloud.repository.ObjectRepository;
import ru.hisoakende.mycloud.util.FileSaver;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Component
@Service
public class FileService implements EntityService<File, UUID> {

    private final ObjectRepository objectRepository;
    private final FileRepository fileRepository;
    private final FileSaver fileSaver;

    public FileService(ObjectRepository objectRepository,
                       FileRepository fileRepository,
                       FileSaver fileSaver) {
        this.objectRepository = objectRepository;
        this.fileRepository = fileRepository;
        this.fileSaver = fileSaver;
    }

    public File getById(UUID id) throws EntityNotFoundException {
        Optional<File> file = fileRepository.findById(id);
        if (file.isEmpty()) {
            throw new EntityNotFoundException("File not found");
        }
        return file.get();
    }

    @Override
    public File create(File file) throws InvalidDataException {
        Object object = objectRepository.save(new Object());
        file.setObjectId(object.getUuid());

        try {
            fileRepository.save(file);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException(e.getMessage());
        }

        file.setObject(object);
        return file;
    }

    public void uploadFileData(MultipartFile fileData, File file)
            throws InvalidFileException, IOException {
        if (fileData.isEmpty()) {
            throw new InvalidFileException("Failed to store empty file.");
        }

        String destination = fileSaver.save(fileData);
        file.setPath(destination);
        fileRepository.save(file);
    }

    @Override
    public void delete(File file) {
        objectRepository.delete(file.getObject());
    }

    public File update(File file, FilePatchDto filePatchDto) throws InvalidDataException {

        file.setName(filePatchDto.getName());
        Object object = file.getObject();
        object.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        try {
            fileRepository.save(file);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException(e.getMessage());
        }
        objectRepository.save(object);

        return file;
    }
}