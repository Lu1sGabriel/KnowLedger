package com.knowledger.knowledger.commom.mapper;

import org.modelmapper.ModelMapper;

import java.util.List;

public class Mapper<D, E, T> implements IMapper<E, T>, IMapperDTO<D, T> {

    private static final ModelMapper modelMapper = new ModelMapper();

    private final Class<D> dtoClass;
    private final Class<E> entityClass;
    private final Class<T> domainClass;

    public Mapper(Class<D> dtoClass, Class<E> entityClass, Class<T> domainClass) {
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
        this.domainClass = domainClass;
    }

    @Override
    public D toDto(T domain) {
        return modelMapper.map(domain, dtoClass);
    }

    @Override
    public List<D> toDtoList(List<T> domains) {
        return domains.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public T toDomain(E entity) {
        return modelMapper.map(entity, domainClass);
    }

    @Override
    public E toEntity(T domain) {
        return modelMapper.map(domain, entityClass);
    }

    @Override
    public List<T> toDomainList(List<E> entities) {
        return entities.stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<E> toEntityList(List<T> domains) {
        return domains.stream()
                .map(this::toEntity)
                .toList();
    }

}