package ru.hisoakende.mycloud.service;

import ru.hisoakende.mycloud.dto.Dto;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.exception.InvalidDataException;
import ru.hisoakende.mycloud.exception.NoAccessToAction;

import java.util.UUID;

public interface EntityService<Entity, UpdateDto extends Dto> {

    Entity getById(UUID id, UUID userId) throws EntityNotFoundException, NoAccessToAction;

    Entity create(Entity entity, UUID userId) throws InvalidDataException, NoAccessToAction;

    Entity update(Entity entity, UpdateDto dto, UUID userId) throws InvalidDataException, NoAccessToAction;

    void delete(Entity entity, UUID userId) throws NoAccessToAction;
}
