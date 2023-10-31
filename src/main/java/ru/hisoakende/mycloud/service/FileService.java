package ru.hisoakende.mycloud.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.hisoakende.mycloud.entity.File;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.repository.FileRepository;
import ru.hisoakende.mycloud.repository.ObjectRepository;

import java.util.Optional;
import java.util.UUID;

@Component
@Service
public class FileService implements EntityService<File, UUID> {

    private final ObjectRepository objectRepository;
    private final FileRepository fileRepository;

    public FileService(ObjectRepository objectRepository,
                       FileRepository fileRepository) {
        this.objectRepository = objectRepository;
        this.fileRepository = fileRepository;
    }

    public File getById(UUID id) throws EntityNotFoundException {
        Optional<File> file = fileRepository.findById(id);
        if (file.isEmpty()) {
            throw new EntityNotFoundException("File not found");
        }
        return file.get();
    }

    @Override
    public File save(File file) {
        return null;
    }

    @Override
    public void delete(File file) {
        objectRepository.delete(file.getObject());
    }
}