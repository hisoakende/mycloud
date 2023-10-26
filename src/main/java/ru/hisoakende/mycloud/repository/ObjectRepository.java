package ru.hisoakende.mycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hisoakende.mycloud.entity.Object;

import java.util.UUID;

public interface ObjectRepository extends JpaRepository<Object, UUID> {
}
