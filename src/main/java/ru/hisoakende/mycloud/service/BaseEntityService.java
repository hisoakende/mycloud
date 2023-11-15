package ru.hisoakende.mycloud.service;

import ru.hisoakende.mycloud.dto.Dto;
import ru.hisoakende.mycloud.entity.EntityInterface;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.exception.InvalidDataException;
import ru.hisoakende.mycloud.exception.NoAccessToAction;

import java.util.UUID;

public interface BaseEntityService<Entity extends EntityInterface, UpdateDto extends Dto> {
    Entity getById(UUID id) throws EntityNotFoundException;

    Entity create(Entity entity) throws InvalidDataException;

    Entity update(Entity entity, UpdateDto dto) throws InvalidDataException;

    void delete(Entity entity);
}
