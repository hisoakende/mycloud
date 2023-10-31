package ru.hisoakende.mycloud.service;

import org.springframework.stereotype.Service;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.entity.Object;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.repository.FolderRepository;
import ru.hisoakende.mycloud.repository.ObjectRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class FolderService implements EntityService<Folder, UUID> {

    private final FolderRepository folderRepository;
    private final ObjectRepository objectRepository;

    public FolderService(FolderRepository folderRepository, ObjectRepository objectRepository) {
        this.folderRepository = folderRepository;
        this.objectRepository = objectRepository;
    }

    public Folder create(Folder folder) {
        Object object = new Object();
        object =  objectRepository.save(object);
        folder.setObjectId(object.getUuid());
        folderRepository.save(folder);
        folder.setObject(object);

        return folder;
    }

    @Override
    public Folder save(Folder folder) {
        return folderRepository.save(folder);
    }

    @Override
    public Folder getById(UUID objectId) throws EntityNotFoundException {
        Optional<Folder> folder = folderRepository.findById(objectId);
        if (folder.isEmpty()) {
            throw new EntityNotFoundException("Folder not found");
        }
        return folder.get();
    }

    public boolean isUniqueFolderFromParent(String name, Folder parentFolder) {
        for (Folder child : parentFolder.getChildFolders()) {
            if (child.getName().equals(name)) return false;
        }
        return true;
    }


}
