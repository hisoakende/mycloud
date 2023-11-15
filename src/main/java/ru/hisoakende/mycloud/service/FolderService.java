package ru.hisoakende.mycloud.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.hisoakende.mycloud.dto.FolderUpdateDto;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.entity.Object;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.exception.InvalidDataException;
import ru.hisoakende.mycloud.repository.FolderRepository;
import ru.hisoakende.mycloud.repository.ObjectRepository;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
public class FolderService implements BaseEntityService<Folder, FolderUpdateDto> {

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
        Object object = objectRepository.save(folder.getObject());
        folder.setObjectId(object.getUuid());
        folder.setObject(object);

        try {
            folderRepository.save(folder);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException(e.getMessage());
        }

        return folder;
    }

    @Override
    public void delete(Folder folder) {
        objectRepository.delete(folder.getObject());
    }

    @Override
    public Folder update(Folder folder, FolderUpdateDto folderUpdateDto) throws InvalidDataException {
        folder.setName(folderUpdateDto.getName());
        Object object = folder.getObject();
        object.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        object.setRead(folderUpdateDto.getRead() != null ? folderUpdateDto.getRead() : object.isRead());
        object.setWrite(folderUpdateDto.getWrite() != null ? folderUpdateDto.getWrite() : object.isWrite());
        object.setDelete(folderUpdateDto.getDelete() != null ? folderUpdateDto.getDelete() : object.isDelete());

        try {
            folderRepository.save(folder);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException(e.getMessage());
        }
        folder.setObject(objectRepository.save(object));

        return folder;
    }

    public Folder move(Folder folder, UUID parentFolderID) throws InvalidDataException, EntityNotFoundException {
        Folder newParentFolder = getById(parentFolderID);
        if (folder.getChildFolders().contains(newParentFolder)) {
            throw new InvalidDataException("New parent folder is child");
        }
        folder.setFolder(newParentFolder);

        Folder movedFolder;
        try {
            movedFolder = folderRepository.save(folder);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException(e.getMessage());
        }
        return movedFolder;
    }
}
