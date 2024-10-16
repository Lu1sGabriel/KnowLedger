package com.knowledger.knowledger.commom.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper<D, E, T> implements IMapper<D, E, T> {
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
    public T toDomain(E entity) {
        return modelMapper.map(entity, domainClass);
    }

    @Override
    public E toEntity(T domain) {
        return modelMapper.map(domain, entityClass);
    }

}
