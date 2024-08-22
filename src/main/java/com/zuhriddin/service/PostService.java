package com.zuhriddin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zuhriddin.model.Post;
import com.zuhriddin.service.util.JsonUtil;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PostService implements BaseService<Post, UUID> {
    private static final String PATH = "src/main/java/com/zuhriddin/file/posts.json";
    private static final UUID DEFAULT_USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Override
    public Post add(Post post) {
        List<Post> posts = read();
        if (!has(post, posts)) {
            posts.add(post);
            write(posts);
            return post;
        }
        throw new RuntimeException("This post already exists.");
    }

    private boolean has(Post post, List<Post> posts) {
        return posts.stream()
                .anyMatch(p -> p.getUserId().equals(post.getUserId()) &&
                        p.getTitle().equals(post.getTitle()));
    }

    @Override
    public Post get(UUID id) {
        List<Post> posts = read();
        return posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Override
    public List<Post> list() {
        return read();
    }

    @Override
    public void delete(UUID id) {
        List<Post> posts = read();
        posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresent(posts::remove);
        write(posts);
    }

    @Override
    public Post update(Post post) {
        List<Post> posts = read();
        int index = posts.indexOf(posts.stream()
               .filter(p -> p.getId().equals(post.getId()))
               .findFirst()
               .orElseThrow(() -> new RuntimeException("Post not found")));
        posts.set(index, post);
        write(posts);
        return post;
    }

    public List<Post> listMyPosts(UUID userId) {
        List<Post> posts = read();
        if (posts == null || posts.isEmpty()) {
            return Collections.emptyList();
        }
        return posts.stream()
                .filter(post -> post.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Post> listOthersPosts() {
        List<Post> posts = read();
        if (posts == null || posts.isEmpty()) {
            return Collections.emptyList();
        }
        return posts.stream()
                .filter(post -> !post.getUserId().equals(DEFAULT_USER_ID))
                .collect(Collectors.toList());
    }

    private List<Post> read() {
        return JsonUtil.read(PATH, new TypeReference<>() {});
    }

    private void write(List<Post> posts) {
        JsonUtil.write(PATH, posts);
    }
}
