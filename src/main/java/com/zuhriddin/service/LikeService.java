package com.zuhriddin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuhriddin.model.Like;
import com.zuhriddin.model.Messege;
import com.zuhriddin.service.util.JsonUtile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LikeService implements BaseService<Like, UUID> {
    private static final String PATH = "likes.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Like> likes;

    @Override
    public Like add(Like like) {
        List<Like> likes1 = read();
        if (likes1.stream().noneMatch(m -> m.getID().equals(like.getID()))) {
            likes1.add(like);
            write(likes1);
            return like;
        }
        throw new IllegalArgumentException("Like already exists!");
    }

    @Override
    public Like get(UUID id) {
        return likes.stream()
                .filter(like -> like.getID().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Like> list() {
        return new ArrayList<>(likes);
    }

    @Override
    public void delete(UUID id) {
        List<Like> likes1 = read().stream()
                .filter(m -> !m.getID().equals(id))
                .collect(Collectors.toList());
        write(likes);
    }

    @Override
    public Like update(Like like) {
        for (int i = 0; i < likes.size(); i++) {
            if (likes.get(i).getID().equals(like.getID())) {
                likes.set(i, like);
                saveLikes();
                return like;
            }
        }
        throw new IllegalArgumentException("Like not found!");
    }

    private void saveLikes() {
        try {
            objectMapper.writeValue(new File(PATH), likes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Like> read() {
        return JsonUtile.read(PATH, new TypeReference<>() {});
    }

    private void write(List<Like> likes) {
        JsonUtile.write(PATH, likes);
    }
}
