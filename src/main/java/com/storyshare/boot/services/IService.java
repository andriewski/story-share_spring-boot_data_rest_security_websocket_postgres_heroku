package com.storyshare.boot.services;

public interface IService<T> {
    T save(T t);

    T get(Long id);

    T update(T t);

    void delete(T t);
}
