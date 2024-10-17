package com.knowledger.knowledger.commom.mapper;

public interface IMapper<E, T> {
    T toDomain(E entity);
    E toEntity(T domain);
}