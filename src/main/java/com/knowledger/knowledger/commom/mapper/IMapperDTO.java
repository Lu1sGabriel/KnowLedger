package com.knowledger.knowledger.commom.mapper;

import java.util.List;

import org.springframework.data.domain.Page;

public interface IMapperDTO<D, T> {
    D toDto(T domain);

    List<D> toDtoList(List<T> domains);

    Page<D> toDtoPage(Page<T> domains);

}