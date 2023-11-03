package ru.hisoakende.mycloud.util;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class PathCreator {

    @Value("${storage}")
    private String baseDirectory;

    public String create() {
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt());

        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        String hashCode = getHash(formattedTime + randomNumber);
        return baseDirectory + "/" + hashCode.substring(0, 2) + "/"
                + hashCode.substring(2, 4) + "/" + hashCode.substring(4);
    }

    @SneakyThrows
    private String getHash(String string) {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] inputBytes = string.getBytes();
        byte[] hashedBytes = md.digest(inputBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}