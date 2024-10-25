package com.knowledger.knowledger.commom.mapper;

import java.util.List;

public interface IMapper<E, T> {
    T toDomain(E entity);

    E toEntity(T domain);

    List<T> toDomainList(List<E> entities);

    List<E> toEntityList(List<T> domains);

}