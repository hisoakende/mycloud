package ru.hisoakende.mycloud.service;

import ru.hisoakende.mycloud.entity.File;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.repository.FileRepository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Component
@Service
public class FileService implements EntityService<File, UUID> {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File getById(UUID id) throws EntityNotFoundException {
        Optional<File> file = fileRepository.findById(id);
        if (file.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return file.get();
    }

    @Override
    public File create(File file) {
        return null;
    }

    @Override
    public void delete(File file) {

    }
}