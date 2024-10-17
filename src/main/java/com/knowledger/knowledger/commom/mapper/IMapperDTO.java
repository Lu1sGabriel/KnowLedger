package com.knowledger.knowledger.commom.mapper;

public interface IMapperDTO<D, T> {
    D toDto(T domain);
}
