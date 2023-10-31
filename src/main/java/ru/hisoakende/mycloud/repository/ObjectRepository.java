package ru.hisoakende.mycloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hisoakende.mycloud.entity.Object;

import java.util.UUID;

@Repository
public interface ObjectRepository extends JpaRepository<Object, UUID> {
}
