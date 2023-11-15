package ru.hisoakende.mycloud.decorators;

import ru.hisoakende.mycloud.dto.Dto;
import ru.hisoakende.mycloud.entity.EntityInterface;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.exception.InvalidDataException;
import ru.hisoakende.mycloud.exception.NoAccessToAction;
import ru.hisoakende.mycloud.service.BaseEntityService;
import ru.hisoakende.mycloud.service.EntityService;

import java.util.UUID;

public class BaseServiceDecorator
        <SubEntity extends EntityInterface,
                SubUpdateDto extends Dto,
                SubService extends BaseEntityService<SubEntity, SubUpdateDto>>
        implements EntityService<SubEntity, SubUpdateDto> {

    protected final SubService service;

    public BaseServiceDecorator(SubService service) {
        this.service = service;
    }

    public SubEntity getById(UUID id, UUID userId) throws EntityNotFoundException, NoAccessToAction {
        return service.getById(id);
    }

    public SubEntity create(SubEntity entity, UUID userId) throws InvalidDataException, NoAccessToAction {
        return service.create(entity);
    }

    public SubEntity update(SubEntity subEntity, SubUpdateDto dto, UUID userId) throws InvalidDataException, NoAccessToAction {
        return service.update(subEntity, dto);
    }

    public void delete(SubEntity entity, UUID userId) throws NoAccessToAction {
        service.delete(entity);
    }
}
