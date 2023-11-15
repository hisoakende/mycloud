package ru.hisoakende.mycloud.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.hisoakende.mycloud.decorators.FolderServiceDecorator;
import ru.hisoakende.mycloud.dto.FolderCreateDto;
import ru.hisoakende.mycloud.dto.FolderUpdateDto;
import ru.hisoakende.mycloud.dto.FolderReadDto;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.exception.InvalidDataException;
import ru.hisoakende.mycloud.exception.NoAccessToAction;
import ru.hisoakende.mycloud.service.FolderService;
import ru.hisoakende.mycloud.util.EntityFinder;
import ru.hisoakende.mycloud.mapper.FolderMapper;
import ru.hisoakende.mycloud.util.URIBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/folders")
public class FolderController {

    private final FolderServiceDecorator folderService;
    private final FolderMapper folderMapper;
    private final EntityFinder<Folder, FolderUpdateDto> entityFinder;

    public FolderController(FolderService folderService,
                            FolderMapper folderMapper,
                            EntityFinder<Folder, FolderUpdateDto> entityFinder) {
        this.folderService = new FolderServiceDecorator(folderService);
        this.folderMapper = folderMapper;
        this.entityFinder = entityFinder;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FolderReadDto> getFolder(
            @RequestHeader(name = "x-user-id") UUID userId,
            @PathVariable UUID uuid
    ) {
        Folder folder;
        try {
            folder = entityFinder.findEntityOr404(folderService, uuid, userId);
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        FolderReadDto folderReadDTO = folderMapper.folderToFolderReadDto(folder);
        return ResponseEntity.ok().body(folderReadDTO);
    }

    @PostMapping
    public ResponseEntity<FolderReadDto> createFolder(
            @RequestHeader(name = "x-user-id") UUID userId,
            @Valid @RequestBody FolderCreateDto folderCreateDto
    ) {
        folderCreateDto.setOwnerId(userId);
        Folder folder = folderMapper.folderCreateDtoToFolder(folderCreateDto);
        try {
            folder = folderService.create(folder, userId);
        } catch (InvalidDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        FolderReadDto folderReadDTO = folderMapper.folderToFolderReadDto(folder);
        URI location = new URIBuilder<>(folderReadDTO.getUuid()).build();
        return ResponseEntity.created(location).body(folderReadDTO);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<FolderReadDto> updateFolder(
            @RequestHeader(name = "x-user-id") UUID userId,
            @Valid @RequestBody FolderUpdateDto folderUpdateDto,
            @PathVariable UUID uuid
    ) {
        Folder folder;
        try {
            folder = folderService.getById(uuid, userId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Folder not found");
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        try {
            folder = folderService.update(folder, folderUpdateDto, userId);
        } catch (InvalidDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        FolderReadDto folderReadDto = folderMapper.folderToFolderReadDto(folder);
        return ResponseEntity.ok().body(folderReadDto);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteFolder(
            @RequestHeader(name = "x-user-id") UUID userId,
            @PathVariable UUID uuid
    ) {
        Folder folder;
        try {
            folder = entityFinder.findEntityOr404(folderService, uuid, userId);
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        try {
            folderService.delete(folder, userId);
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/move/{uuid}")
    public ResponseEntity<FolderReadDto> moveFolder(
            @RequestHeader(name = "x-user-id") UUID userId,
            @PathVariable UUID uuid,
            @RequestHeader UUID parentFolderId
    ) {
        Folder folder;
        try {
            folder = folderService.getById(uuid, userId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Folder not found");
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Folder movedFolder;
        try {
            movedFolder = folderService.move(folder, parentFolderId, userId);
        } catch (InvalidDataException | EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        } catch (NoAccessToAction e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        FolderReadDto folderReadDto = folderMapper.folderToFolderReadDto(movedFolder);

        return ResponseEntity.ok().body(folderReadDto);
    }
}
