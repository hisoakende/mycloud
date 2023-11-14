package ru.hisoakende.mycloud.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hisoakende.mycloud.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, UUID> {
    @Query(nativeQuery = true, value = "WITH RECURSIVE FolderHierarchy AS (" +
            "SELECT object_id FROM folder WHERE object_id = :objectId " +
            "UNION ALL " +
            "SELECT f.object_id FROM folder f " +
            "JOIN FolderHierarchy fh ON f.parent_folder_id = fh.object_id) " +
            "SELECT object_id FROM FolderHierarchy WHERE object_id <> :objectId")
    List<UUID> getChildIds(@Param("objectId") UUID objectId);


}
