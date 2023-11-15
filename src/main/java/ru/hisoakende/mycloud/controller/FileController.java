package ru.hisoakende.mycloud.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.hisoakende.mycloud.decorators.FileServiceDecorator;
import ru.hisoakende.mycloud.dto.FileCreateDto;
import ru.hisoakende.mycloud.dto.FileReadDto;
import ru.hisoakende.mycloud.dto.FileUpdateDto;
import ru.hisoakende.mycloud.entity.File;
import ru.hisoakende.mycloud.exception.InvalidDataException;
import ru.hisoakende.mycloud.exception.InvalidFileException;
import ru.hisoakende.mycloud.exception.NoAccessToAction;
import ru.hisoakende.mycloud.mapper.FileMapper;
import ru.hisoakende.mycloud.service.FileService;
import ru.hisoakende.mycloud.util.EntityFinder;
import ru.hisoakende.mycloud.util.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileServiceDecorator fileService;
    private final FileMapper fileMapper;
    private final EntityFinder<File, FileUpdateDto> entityFinder;

    public FileController(FileService fileService,
                          FileMapper fileMapper,
                          EntityFinder<File, FileUpdateDto> entityFinder) {
        this.fileService = new FileServiceDecorator(fileService);
        this.fileMapper = fileMapper;
        this.entityFinder = entityFinder;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FileReadDto> getFile(
            @RequestHeader(name = "x-user-id") UUID userId,
            @PathVariable UUID uuid
    ) {
        File file;
        try {
            file = entityFinder.findEntityOr404(fileService, uuid, userId);
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        FileReadDto folderReadDto = fileMapper.fileToFileReadDto(file);
        return ResponseEntity.ok().body(folderReadDto);
    }

    @PostMapping
    public ResponseEntity<FileReadDto> createFileMeta(
            @RequestHeader(name = "x-user-id") UUID userId,
            @Valid @RequestBody FileCreateDto fileCreateDto
    ) {
        fileCreateDto.setOwnerId(userId);
        File file = fileMapper.fileCreateDtoToFile(fileCreateDto);
        File createdFile;
        try {
            createdFile = fileService.create(file, userId);
        } catch (InvalidDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        FileReadDto fileReadDto = fileMapper.fileToFileReadDto(createdFile);
        URI location = new URIBuilder<>(fileReadDto.getUuid()).build();
        return ResponseEntity.created(location).body(fileReadDto);
    }

    @PostMapping(
            value = "/{uuid}/data",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<?> uploadFile(
            @RequestHeader(name = "x-user-id") UUID userId,
            @PathVariable UUID uuid,
            @RequestParam("file") MultipartFile fileData
    )
            throws IOException {
        File file;
        try {
            file = entityFinder.findEntityOr404(fileService, uuid, userId);
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if (file.getPath() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        try {
            fileService.uploadFileData(fileData, file, userId);
        } catch (InvalidFileException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteFile(
            @RequestHeader(name = "x-user-id") UUID userId,
            @PathVariable UUID uuid
    ) {
        File file;
        try {
            file = entityFinder.findEntityOr404(fileService, uuid, userId);
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        try {
            fileService.delete(file, userId);
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.noContent().build();
    }
}
