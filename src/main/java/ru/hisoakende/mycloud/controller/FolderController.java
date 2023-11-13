package ru.hisoakende.mycloud.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.hisoakende.mycloud.dto.FolderCreateDto;
import ru.hisoakende.mycloud.dto.FolderPatchDto;
import ru.hisoakende.mycloud.dto.FolderReadDto;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.exception.EntityNotFoundException;
import ru.hisoakende.mycloud.exception.InvalidDataException;
import ru.hisoakende.mycloud.service.FolderService;
import ru.hisoakende.mycloud.util.EntityFinder;
import ru.hisoakende.mycloud.mapper.FolderMapper;
import ru.hisoakende.mycloud.util.URIBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/folders")
public class FolderController {

    private final FolderService folderService;
    private final FolderMapper folderMapper;
    private final EntityFinder<Folder, UUID> entityFinder;

    public FolderController(FolderService folderService,
                            FolderMapper folderMapper,
                            EntityFinder<Folder, UUID> entityFinder) {
        this.folderService = folderService;
        this.folderMapper = folderMapper;
        this.entityFinder = entityFinder;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FolderReadDto> getFolder(@PathVariable UUID uuid) {
        Folder folder = entityFinder.findEntityOr404(folderService, uuid);
        FolderReadDto folderReadDTO = folderMapper.folderToFolderReadDto(folder);
        return ResponseEntity.ok().body(folderReadDTO);
    }

    @PostMapping
    public ResponseEntity<FolderReadDto> createFolder(@Valid @RequestBody FolderCreateDto folderCreateDto) {
        Folder folder = folderMapper.folderCreateDtoToFolder(folderCreateDto);
        try {
            folder = folderService.create(folder);
        } catch (InvalidDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }

        FolderReadDto folderReadDTO = folderMapper.folderToFolderReadDto(folder);
        URI location = new URIBuilder<>(folderReadDTO.getUuid()).build();
        return ResponseEntity.created(location).body(folderReadDTO);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<FolderReadDto> updateFolder(@Valid @RequestBody FolderPatchDto folderPatchDto,
                                                     @PathVariable UUID uuid) {
        Folder folder = null;
        try {
            folder = folderService.getById(uuid);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Folder not found");
        }
        try {
            folder = folderService.update(folder, folderPatchDto);
        } catch (InvalidDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }

        FolderReadDto folderReadDto = folderMapper.folderToFolderReadDto(folder);
        return ResponseEntity.ok().body(folderReadDto);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteFolder(@PathVariable UUID uuid) {
        Folder folder = entityFinder.findEntityOr404(folderService, uuid);
        folderService.delete(folder);
        return ResponseEntity.noContent().build();
    }
}
