package ru.hisoakende.mycloud.service;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.entity.Object;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.exception.InvalidDataException;
import ru.hisoakende.mycloud.repository.FolderRepository;
import ru.hisoakende.mycloud.repository.ObjectRepository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Service
public class FolderService implements EntityService<Folder, UUID> {

    private final FolderRepository folderRepository;
    private final ObjectRepository objectRepository;

    public FolderService(FolderRepository folderRepository,
                         ObjectRepository objectRepository) {
        this.folderRepository = folderRepository;
        this.objectRepository = objectRepository;
    }

    @Override
    public Folder getById(UUID objectId) throws EntityNotFoundException {
        Optional<Folder> folder = folderRepository.findById(objectId);
        if (folder.isEmpty()) {
            throw new EntityNotFoundException("Folder not found");
        }
        return folder.get();
    }

    @Override
    public Folder create(Folder folder) throws InvalidDataException {
        Object object = objectRepository.save(new Object());
        folder.setObjectId(object.getUuid());

        try {
            folderRepository.save(folder);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException(e.getMessage());
        }

        folder.setObject(object);
        return folder;
    }

    @Override
    public Folder save(Folder folder) {
        return folderRepository.save(folder);
    }

    @Override
    public void delete(Folder folder) {
        folderRepository.delete(folder);
    }
}
