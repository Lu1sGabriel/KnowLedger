package com.knowledger.knowledger.commom.mapper;

import java.util.List;

public interface IMapperDTO<D, T> {
    D toDto(T domain);

    List<D> toDtoList(List<T> domains);

}