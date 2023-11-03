package ru.hisoakende.mycloud.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileSaver {

    private final PathCreator pathCreator;

    public FileSaver(PathCreator pathCreator) {
        this.pathCreator = pathCreator;
    }

    public String save(MultipartFile fileData) throws IOException {
        String destination;
        do {
            destination = pathCreator.create();
        } while ((new File(destination)).exists());

        Path path = Path.of(destination);
        Files.createDirectories(path.getParent());
        Files.copy(fileData.getInputStream(), path);

        return destination;
    }
}
