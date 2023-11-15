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

}
