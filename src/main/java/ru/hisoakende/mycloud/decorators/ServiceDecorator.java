package ru.hisoakende.mycloud.decorators;

import org.springframework.transaction.annotation.Transactional;
import ru.hisoakende.mycloud.dto.Dto;
import ru.hisoakende.mycloud.entity.EntityInterface;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.exception.InvalidDataException;
import ru.hisoakende.mycloud.exception.NoAccessToAction;
import ru.hisoakende.mycloud.service.BaseEntityService;

import java.util.UUID;

public class ServiceDecorator
        <SubEntity extends EntityInterface,
                SubUpdateDto extends Dto,
                SubService extends BaseEntityService<SubEntity, SubUpdateDto>>
        extends BaseServiceDecorator<SubEntity, SubUpdateDto, SubService> {

    public ServiceDecorator(SubService service) {
        super(service);
    }

    @Override
    @Transactional
    public SubEntity getById(UUID id, UUID userId) throws EntityNotFoundException, NoAccessToAction {
        SubEntity entity = service.getById(id);
        if (!entity.getObject().getOwnerId().equals(userId) &&
                !entity.getObject().isRead()) {
            throw new NoAccessToAction();
        }
        return entity;
    }

    @Override
    @Transactional
    public SubEntity create(SubEntity entity, UUID userId) throws InvalidDataException, NoAccessToAction {
        SubEntity createdEntity = service.create(entity);
        if (!createdEntity.getObject().getOwnerId().equals(userId) &&
                !createdEntity.getFolder().getObject().isWrite()) {
            throw new NoAccessToAction();
        }
        return createdEntity;
    }

    @Override
    @Transactional
    public SubEntity update(SubEntity entity, SubUpdateDto dto, UUID userId) throws InvalidDataException, NoAccessToAction {
        SubEntity updatedEntity = service.update(entity, dto);
        if (!updatedEntity.getObject().getOwnerId().equals(userId) &&
                !updatedEntity.getFolder().getObject().isWrite()) {
            throw new NoAccessToAction();
        }
        return updatedEntity;
    }

    @Override
    @Transactional
    public void delete(SubEntity entity, UUID userId) throws NoAccessToAction {
        service.delete(entity);
        if (!entity.getObject().getOwnerId().equals(userId) && !entity.getObject().isDelete()) {
            throw new NoAccessToAction();
        }
    }
}
