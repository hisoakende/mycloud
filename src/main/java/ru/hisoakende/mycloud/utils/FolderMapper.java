package ru.hisoakende.mycloud.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import ru.hisoakende.mycloud.dto.FolderCreateDTO;
import ru.hisoakende.mycloud.dto.FolderReadDTO;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.entity.Object;
import ru.hisoakende.mycloud.repositories.FolderRepository;
import ru.hisoakende.mycloud.services.ObjectService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FolderMapper {
    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectService objectService;


    public Folder folderDtoToFolder(FolderCreateDTO folderCreateDTO) {
        Folder folder = modelMapper.map(folderCreateDTO, Folder.class);
        Folder mockParentFolder = new Folder();
        mockParentFolder.setObjectId(folderCreateDTO.getParentFolderId());
        folder.setParentFolder(mockParentFolder);

        return folder;
    }

    public FolderReadDTO folderToFolderReadDTO(Folder folder) {
        Object object = folder.getObject();
        FolderReadDTO folderReadDTO = modelMapper.map(folder, FolderReadDTO.class);
        folderReadDTO.setCreatedAt(object.getCreatedAt());
        folderReadDTO.setUpdatedAt(object.getUpdatedAt());
        folderReadDTO.setUuid(folder.getObjectId());
        if (folder.getChildFolders() == null)
            folderReadDTO.setChildFolders(new ArrayList<>());
        return folderReadDTO;
    }
}
