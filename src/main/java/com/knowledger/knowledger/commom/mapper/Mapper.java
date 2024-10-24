package com.knowledger.knowledger.commom.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
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

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        if (dtoClass.isRecord()) {
            configureRecordMapping(dtoClass);
        }
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

    @Override
    public List<D> toDtoList(List<T> domains) {
        return domains.stream()
                .map(this::toDto)
                .toList();
    }

    private void configureRecordMapping(Class<D> recordClass) {
        modelMapper.typeMap(domainClass, recordClass).setProvider(request -> {
            try {
                Constructor<?> constructor = recordClass.getDeclaredConstructors()[0];

                Object[] args = Arrays.stream(constructor.getParameters())
                        .map(parameter -> getSourceFieldValue(request.getSource(), parameter))
                        .toArray();

                return (D) constructor.newInstance(args);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao instanciar record: " + recordClass.getName(), e);
            }
        });
    }

    private Object getSourceFieldValue(Object source, Parameter parameter) {
        try {
            var field = source.getClass().getDeclaredField(parameter.getName());
            field.setAccessible(true);
            return field.get(source);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter o valor do campo: " + parameter.getName(), e);
        }
    }

}