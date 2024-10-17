package com.knowledger.knowledger.application.usecases.department;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.domain.department.Department;
import com.knowledger.knowledger.infra.controller.department.DepartmentDetailDTO;
import com.knowledger.knowledger.infra.gateways.department.IDepartmentGateway;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentGetAll {

    private final IDepartmentGateway iDepartmentGateway;
    private final IMapperDTO<DepartmentDetailDTO, Department> iMapper;

    public DepartmentGetAll(IDepartmentGateway iDepartmentGateway, IMapperDTO<DepartmentDetailDTO, Department> iMapper) {
        this.iDepartmentGateway = iDepartmentGateway;
        this.iMapper = iMapper;
    }

    public List<DepartmentDetailDTO> getAll() {
        var departments = iDepartmentGateway.getAll();
        return iMapper.toDtoList(departments);
    }

}