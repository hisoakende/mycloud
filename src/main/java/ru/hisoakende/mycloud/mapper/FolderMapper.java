package ru.hisoakende.mycloud.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hisoakende.mycloud.dto.FolderCreateDto;
import ru.hisoakende.mycloud.dto.FolderPreviewDto;
import ru.hisoakende.mycloud.dto.FolderReadDto;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.entity.Object;


import java.util.*;

@Component
public class FolderMapper {

    @Autowired
    private ModelMapper modelMapper;


    public Folder folderCreateDtoToFolder(FolderCreateDto folderCreateDTO) {
        Folder folder = modelMapper.map(folderCreateDTO, Folder.class);
        Folder mockParentFolder = new Folder();
        mockParentFolder.setObjectId(folderCreateDTO.getParentFolderId());
        folder.setParentFolder(mockParentFolder);

        return folder;
    }

    public FolderReadDto folderToFolderReadDto(Folder folder) {
        Object object = folder.getObject();
        FolderReadDto folderReadDTO = FolderReadDto.builder()
                .uuid(folder.getObjectId())
                .name(folder.getName())
                .parentFolderId(folder.getParentFolderId())
                .createdAt(object.getCreatedAt())
                .updatedAt(object.getUpdatedAt()).build();
        List<FolderPreviewDto> previewDtoList;
        if (folder.getChildFolders() == null) {
            previewDtoList = new ArrayList<>();
        } else {
            previewDtoList = listFolderToListFolderPreviewDto(folder.getChildFolders());
        }
        folderReadDTO.setChildFolders(previewDtoList);
        return folderReadDTO;
    }

    public List<FolderPreviewDto> listFolderToListFolderPreviewDto(List<Folder> folders) {
        List<FolderPreviewDto> previewDtoList = new ArrayList<>();
        for (Folder childFolder : folders) {
            FolderPreviewDto mapChildFolder = FolderPreviewDto.builder()
                    .uuid(childFolder.getObjectId())
                    .name(childFolder.getName())
                    .createdAt(childFolder.getObject().getCreatedAt())
                    .updatedAt(childFolder.getObject().getUpdatedAt()).build();
            previewDtoList.add(mapChildFolder);
        }
        return previewDtoList;
    }
}
