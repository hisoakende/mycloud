package ru.hisoakende.mycloud.services;

import org.springframework.stereotype.Service;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.entity.Object;
import ru.hisoakende.mycloud.exceptions.EntityNotFoundException;
import ru.hisoakende.mycloud.repositories.FolderRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class FolderService implements EntityService<Folder, UUID>{

    private final FolderRepository folderRepository;
    private final ObjectService objectService;

    public FolderService(FolderRepository folderRepository, ObjectService objectService) {
        this.folderRepository = folderRepository;
        this.objectService = objectService;
    }

    public Folder create(Folder folder) {
        Object object = objectService.create();
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
            throw new EntityNotFoundException();
        }
        return folder.get();
    }

    public boolean isUniqueFolderFromParent(String name, Folder parentFolder){
        for (Folder child : parentFolder.getChildFolders()) {
            if (child.getName().equals(name)) return false;
        }
        return true;
    }



}
