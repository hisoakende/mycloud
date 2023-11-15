package ru.hisoakende.mycloud.dto;

import lombok.Data;

@Data
public class FileUpdateDto implements Dto {

    private String name;

    private Boolean read = false;

    private Boolean write = false;

    private Boolean delete = false;
}
