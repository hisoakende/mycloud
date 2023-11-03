package ru.hisoakende.mycloud.controller;

import jakarta.validation.Valid;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.hisoakende.mycloud.dto.FileCreateDto;
import ru.hisoakende.mycloud.dto.FileReadDto;
import ru.hisoakende.mycloud.entity.File;
import ru.hisoakende.mycloud.exception.InvalidFileException;
import ru.hisoakende.mycloud.exception.InvalidDataException;
import ru.hisoakende.mycloud.mapper.FileMapper;
import ru.hisoakende.mycloud.service.FileService;
import ru.hisoakende.mycloud.util.EntityFinder;
import ru.hisoakende.mycloud.util.FileSaver;
import ru.hisoakende.mycloud.util.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
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

    @GetMapping("/{uuid}")
    public ResponseEntity<FileReadDto> getFile(@PathVariable UUID uuid) {
        File file = entityFinder.findEntityOr404(fileService, uuid);
        FileReadDto folderReadDto = fileMapper.fileToFileReadDto(file);
        return ResponseEntity.ok().body(folderReadDto);
    }

    @PostMapping
    public ResponseEntity<FileReadDto> createFileMeta(
            @Valid @RequestBody FileCreateDto fileCreateDto
    ) {

        File file = fileMapper.fileCreateDtoToFile(fileCreateDto);
        try {
            file = fileService.create(file);
        } catch (InvalidDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }

        FileReadDto fileReadDto = fileMapper.fileToFileReadDto(file);
        URI location = new URIBuilder<>(fileReadDto.getUuid()).build();
        return ResponseEntity.created(location).body(fileReadDto);
    }

    @PostMapping(
            value ="/{uuid}/data",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<?> uploadFile(
            @PathVariable UUID uuid,
            @RequestParam("file") MultipartFile fileData
    )
            throws IOException {

        File file = entityFinder.findEntityOr404(fileService, uuid);
        if (file.getPath() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        try {
            fileService.uploadFileData(fileData, file);
        } catch (InvalidFileException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteFile(@PathVariable UUID uuid) {
        File file = entityFinder.findEntityOr404(fileService, uuid);
        fileService.delete(file);
        return ResponseEntity.noContent().build();
    }
}
