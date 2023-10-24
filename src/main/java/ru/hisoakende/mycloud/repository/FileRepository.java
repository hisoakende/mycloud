package ru.hisoakende.mycloud.repository;

import ru.hisoakende.mycloud.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID> {

}
