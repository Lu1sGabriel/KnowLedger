package com.knowledger.knowledger.commom.mapper;

public interface IMapper<D, E, T> {
    D toDto(T domain);
    T toDomain(E entity);
    E toEntity(T domain);
}


