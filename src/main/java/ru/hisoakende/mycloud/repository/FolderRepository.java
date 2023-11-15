package ru.hisoakende.mycloud.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hisoakende.mycloud.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FolderRepository extends JpaRepository<Folder, UUID> {

    @Query(nativeQuery = true,
            value = "WITH RECURSIVE FolderHierarchy AS (" +
                    "SELECT * FROM folder WHERE object_id = :objectId " +
                    "UNION ALL " +
                    "SELECT f.* FROM folder f " +
                    "JOIN FolderHierarchy fh ON f.folder_id = fh.object_id) " +
                    "SELECT * FROM FolderHierarchy WHERE object_id = :findId")
    Folder findFolderInHierarchy(@Param("objectId") UUID objectId, @Param("findId") UUID findId);
}
