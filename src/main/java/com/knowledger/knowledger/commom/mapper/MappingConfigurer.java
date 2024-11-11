package com.knowledger.knowledger.commom.mapper;

import org.modelmapper.ModelMapper;

public interface MappingConfigurer<E, T> {
    void configure(ModelMapper modelMapper);
}
