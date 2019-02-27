package com.storyshare.boot.services.impl;

import com.storyshare.boot.services.IService;
import com.storyshare.boot.services.ServiceException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

@Transactional
@Data
public abstract class BaseService<T> implements IService<T> {
    @Autowired
    private JpaRepository<T, Long> jpaRepository;

    @Override
    public T save(T t) {
        try {
            return jpaRepository.save(t);
        } catch (ConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error saving " + t);
        }
    }

    @Override
    public T get(Long id) {
        try {
            return jpaRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new ServiceException("Error getting " + id);
        }
    }

    @Override
    public T update(T t) {
        try {
            return save(t);
        } catch (Exception e) {
            throw new ServiceException("Error updating " + t);
        }
    }

    @Override
    public void delete(T t) {
        try {
            jpaRepository.delete(t);
        } catch (Exception e) {
            throw new ServiceException("Error deleting " + t);
        }
    }
}
