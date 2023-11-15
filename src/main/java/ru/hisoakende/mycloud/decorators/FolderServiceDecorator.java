package ru.hisoakende.mycloud.decorators;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hisoakende.mycloud.dto.FolderUpdateDto;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.exception.InvalidDataException;
import ru.hisoakende.mycloud.exception.NoAccessToAction;
import ru.hisoakende.mycloud.service.FolderService;

import java.util.UUID;

@Service
public class FolderServiceDecorator extends ServiceDecorator<Folder, FolderUpdateDto, FolderService> {

    public FolderServiceDecorator(FolderService service) {
        super(service);
    }

    @Transactional
    public Folder move(Folder folder, UUID parentFolderID, UUID userId)
            throws NoAccessToAction, InvalidDataException, EntityNotFoundException {
        Folder movedFolder = service.move(folder, parentFolderID);
        if (!movedFolder.getFolder().getObject().isWrite() ||
                (!movedFolder.getObject().isWrite() ||
                        movedFolder.getObject().getOwnerId() != userId)) {
            throw new NoAccessToAction();
        }
        return movedFolder;
    }
}
