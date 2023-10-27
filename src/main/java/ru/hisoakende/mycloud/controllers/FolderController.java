package ru.hisoakende.mycloud.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import ru.hisoakende.mycloud.dto.FolderCreateDTO;
import ru.hisoakende.mycloud.dto.FolderReadDTO;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.services.FolderService;
import ru.hisoakende.mycloud.services.ObjectService;
import ru.hisoakende.mycloud.utils.FolderMapper;
import ru.hisoakende.mycloud.utils.URIBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/folder")
public class FolderController {

    private final FolderService folderService;
    private final ObjectService objectService;
    private final FolderMapper folderMapper;

    public FolderController(FolderService folderService,
                            ObjectService objectService,
                            FolderMapper folderMapper) {
        this.folderService = folderService;
        this.objectService = objectService;
        this.folderMapper = folderMapper;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FolderReadDTO> getFolder(@PathVariable UUID uuid) {
        return ResponseEntity.ok().body(new FolderReadDTO());
    }

    @PostMapping
    public ResponseEntity<FolderReadDTO> createFolder(@Valid @RequestBody FolderCreateDTO folderCreateDTO) {
        Folder folder = folderMapper.folderDtoToFolder(folderCreateDTO);
        folderService.create(folder);
        FolderReadDTO folderReadDTO = folderMapper.folderToFolderReadDTO(folder);
        URI location = new URIBuilder<>(folderReadDTO.getUuid()).build();
        return ResponseEntity.created(location).body(folderReadDTO);
    }

}
