package ru.hisoakende.mycloud.util.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hisoakende.mycloud.dto.FolderCreateDto;
import ru.hisoakende.mycloud.dto.FolderPreviewDto;
import ru.hisoakende.mycloud.dto.FolderReadDto;
import ru.hisoakende.mycloud.entity.Folder;
import ru.hisoakende.mycloud.entity.Object;
import ru.hisoakende.mycloud.repository.FolderRepository;

import java.util.*;

@Component
public class FolderMapper {
    @Autowired
    private ModelMapper modelMapper;


    public Folder folderDtoToFolder(FolderCreateDto folderCreateDTO) {
        Folder folder = modelMapper.map(folderCreateDTO, Folder.class);
        Folder mockParentFolder = new Folder();
        mockParentFolder.setObjectId(folderCreateDTO.getParentFolderId());
        folder.setParentFolder(mockParentFolder);

        return folder;
    }

    public FolderReadDto folderToFolderReadDTO(Folder folder) {
        Object object = folder.getObject();
        FolderReadDto folderReadDTO = modelMapper.map(folder, FolderReadDto.class);
        folderReadDTO.setCreatedAt(object.getCreatedAt());
        folderReadDTO.setUpdatedAt(object.getUpdatedAt());
        folderReadDTO.setUuid(folder.getObjectId());
        folderReadDTO.setParentFolderId(folder.getParentFolder().getObjectId());
        if (folder.getChildFolders() == null)
            folderReadDTO.setChildFolders(new ArrayList<>());
        else
            folderReadDTO.setChildFolders(listFolderToListFolderPreviewDto(folder.getChildFolders()));

        return folderReadDTO;
    }

    public List<FolderPreviewDto> listFolderToListFolderPreviewDto(List<Folder> folders) {
        List<FolderPreviewDto> previewDtoList = new ArrayList<>();
        for (Folder childFolder : folders) {
            FolderPreviewDto mapChildFolder = FolderPreviewDto.builder()
                    .uuid(childFolder.getObjectId())
                    .name(childFolder.getName())
                    .createdAt(childFolder.getObject().getCreatedAt())
                    .updatedAt(childFolder.getObject().getUpdatedAt())
                    .build();
            previewDtoList.add(mapChildFolder);
        }
        return previewDtoList;
    }
}
