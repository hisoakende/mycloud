package ru.hisoakende.mycloud.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hisoakende.mycloud.dto.FolderCreateDto;
import ru.hisoakende.mycloud.dto.FolderReadDto;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.service.FolderService;
import ru.hisoakende.mycloud.util.EntityFinder;
import ru.hisoakende.mycloud.util.mapper.FolderMapper;
import ru.hisoakende.mycloud.util.URIBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/folder")
public class FolderController {

    private final FolderService folderService;
    private final FolderMapper folderMapper;
    private final EntityFinder<Folder, UUID> entityFinder;

    public FolderController(FolderService folderService, FolderMapper folderMapper, EntityFinder<Folder, UUID> entityFinder) {
        this.folderService = folderService;
        this.folderMapper = folderMapper;
        this.entityFinder = entityFinder;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FolderReadDto> getFolder(@PathVariable UUID uuid){
        Folder folder = entityFinder.findEntityOr404(folderService, uuid);
        FolderReadDto folderReadDTO = folderMapper.folderToFolderReadDTO(folder);
        return ResponseEntity.ok().body(folderReadDTO);
    }

    @PostMapping
    public ResponseEntity<FolderReadDto> createFolder(@Valid @RequestBody FolderCreateDto folderCreateDTO) {
        Folder folder = folderMapper.folderDtoToFolder(folderCreateDTO);
        folderService.create(folder);
        FolderReadDto folderReadDTO = folderMapper.folderToFolderReadDTO(folder);
        URI location = new URIBuilder<>(folderReadDTO.getUuid()).build();
        return ResponseEntity.created(location).body(folderReadDTO);
    }

}
