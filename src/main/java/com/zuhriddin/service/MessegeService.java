package com.zuhriddin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zuhriddin.model.Messege;
import com.zuhriddin.service.util.JsonUtile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MessegeService implements BaseService<Messege, UUID> {
    private static final String PATH = "messeges.json";

    @Override
    public Messege add(Messege messege) {
        List<Messege> messeges = read();
        if (messeges.stream().noneMatch(m -> m.getId().equals(messege.getId()))) {
            messeges.add(messege);
            write(messeges);
            return messege;
        }
        throw new IllegalArgumentException("Message already exists!");
    }

    @Override
    public Messege get(UUID id) {
        return read().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Message not found!"));
    }

    @Override
    public List<Messege> list() {
        return read();
    }

    @Override
    public void delete(UUID id) {
        List<Messege> messeges = read().stream()
                .filter(m -> !m.getId().equals(id))
                .collect(Collectors.toList());
        write(messeges);
    }

    @Override
    public Messege update(Messege messege) {
        List<Messege> messeges = read();
        for (int i = 0; i < messeges.size(); i++) {
            if (messeges.get(i).getId().equals(messege.getId())) {
                messeges.set(i, messege);
                write(messeges);
                return messege;
            }
        }
        throw new IllegalArgumentException("Message not found!");
    }

    private List<Messege> read() {
        return JsonUtile.read(PATH, new TypeReference<>() {});
    }

    private void write(List<Messege> messeges) {
        JsonUtile.write(PATH, messeges);
    }

}
