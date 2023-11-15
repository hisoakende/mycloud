package ru.hisoakende.mycloud.util;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.hisoakende.mycloud.dto.Dto;
import ru.hisoakende.mycloud.entity.EntityInterface;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.exception.NoAccessToAction;
import ru.hisoakende.mycloud.service.EntityService;

import java.util.UUID;

@Component
public class EntityFinder<SubEntity extends EntityInterface, SubUpdateEntity extends Dto> {

    public SubEntity findEntityOr404(EntityService<SubEntity, SubUpdateEntity> service, UUID id, UUID userId) throws NoAccessToAction {
        try {
            return service.getById(id, userId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        }
    }
}
