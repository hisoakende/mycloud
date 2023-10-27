package ru.hisoakende.mycloud.services;

import org.springframework.stereotype.Service;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.entity.Object;
import ru.hisoakende.mycloud.exceptions.EntityNotFoundException;
import ru.hisoakende.mycloud.repositories.ObjectRepository;

import javax.naming.event.ObjectChangeListener;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObjectService implements EntityService<Object, UUID>{
    private final ObjectRepository objectRepository;

    public ObjectService(ObjectRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    public Object create() {
        Object object = new Object();
        return objectRepository.save(object);
    }

    @Override
    public Object getById(UUID id) throws EntityNotFoundException {
        Optional<Object> object = objectRepository.findById(id);
        if (object.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return object.get();
    }

    @Override
    public Object save(Object object) {
        return objectRepository.save(object);
    }
}
