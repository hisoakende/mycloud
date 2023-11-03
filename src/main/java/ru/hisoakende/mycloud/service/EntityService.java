package ru.hisoakende.mycloud.service;

import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.exception.InvalidDataException;

public interface EntityService<Entity, IdType> {
    Entity getById(IdType id) throws EntityNotFoundException;

    Entity create(Entity entity) throws InvalidDataException;

    Entity save(Entity Entity);

    void delete(Entity entity);
}
