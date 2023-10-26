package ru.hisoakende.mycloud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hisoakende.mycloud.dto.FileReadDto;
import ru.hisoakende.mycloud.entity.File;
import ru.hisoakende.mycloud.mapper.FileMapper;
import ru.hisoakende.mycloud.service.FileService;
import ru.hisoakende.mycloud.util.EntityFinder;

import java.util.UUID;

@RestController
@RequestMapping("/api/files/")
public class FileController {

    private final FileService fileService;
    private final FileMapper fileMapper;
    private final EntityFinder<File, UUID> entityFinder;

    public FileController(FileService fileService,
                          FileMapper fileMapper,
                          EntityFinder<File, UUID> entityFinder) {
        this.fileService = fileService;
        this.fileMapper = fileMapper;
        this.entityFinder = entityFinder;
    }

    @GetMapping("/{uuid}/")
    public ResponseEntity<FileReadDto> getFile(@PathVariable UUID uuid) {
        File file = entityFinder.findEntityOr404(fileService, uuid);
        FileReadDto folderReadDto = fileMapper.FileToFileReadDto(file);
        return ResponseEntity.ok().body(folderReadDto);
    }
}
