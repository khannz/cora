package com.epam.cora.esa.service;

import com.epam.cora.esa.exception.EntityNotFoundException;
import com.epam.cora.esa.model.EntityContainer;

import java.util.Optional;

public interface EntityLoader<T> {

    Optional<EntityContainer<T>> loadEntity(Long id) throws EntityNotFoundException;
}
