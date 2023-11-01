package ru.hisoakende.mycloud.service;

import ru.hisoakende.mycloud.exception.EntityNotFoundException;

public interface EntityService<Entity, IdType> {
    Entity getById(IdType id) throws EntityNotFoundException;

    Entity save(Entity entity);

    void delete(Entity entity);
}
