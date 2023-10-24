package ru.hisoakende.mycloud.util;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.service.EntityService;

@Component
public class EntityFinder<Entity, IdType> {

    public Entity findEntityOr404(EntityService<Entity, IdType> service, IdType id){
        try {
            return service.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        }
    }
}
