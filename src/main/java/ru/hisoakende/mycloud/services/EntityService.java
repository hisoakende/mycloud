package ru.hisoakende.mycloud.services;

import ru.hisoakende.mycloud.exceptions.EntityNotFoundException;

public interface EntityService<Entity, IdType> {
    Entity getById(IdType id) throws EntityNotFoundException;
    Entity save(Entity entity);
}
