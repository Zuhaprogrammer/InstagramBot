package com.zuhriddin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zuhriddin.model.Notification;
import com.zuhriddin.service.util.JsonUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class NotificationService implements BaseService<Notification, UUID> {
    private static final String PATH = "src/file/Notification.json";

    @Override
    public Notification add(Notification notification) {
        List<Notification> notificationList = read();
        if (has(notificationList, notification)) {
            throw new RuntimeException();
        }
        notificationList.add(notification);
        write(notificationList);
        return notification;
    }

    private boolean has(List<Notification> notificationList, Notification notification) {
        return notificationList.stream()
                .anyMatch(n -> n.getId().equals(notification.getId()));
    }

    @Override
    public Notification get(UUID id) {
        return read().stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Notification> list() {
        return read();
    }

    @Override
    public void delete(UUID id) {
        List<Notification> notificationList = read().stream()
                .filter(n -> !n.getId().equals(id))
                .collect(Collectors.toList());
        write(notificationList);
    }

    @Override
    public Notification update(Notification notification) {
        List<Notification> notificationList = read();
        for (int i = 0; i < notificationList.size(); i++) {
            if (notificationList.get(i).getId().equals(notification.getId())) {
                notificationList.get(i).setRead(notification.isRead());
                notificationList.get(i).setUserId(notification.getUserId());
                notificationList.get(i).setClpId(notification.getClpId());
                write(notificationList);
                return notification;
            }
        } throw new RuntimeException();
    }

    private List<Notification> read() {
        return JsonUtil.read(PATH, new TypeReference<>() {});
    }

    private void write(List<Notification> notificationList) {
        JsonUtil.write(PATH, notificationList);
    }
}
