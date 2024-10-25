package com.knowledger.knowledger.application.usecases.department;

import com.knowledger.knowledger.commom.mapper.IMapperDTO;
import com.knowledger.knowledger.domain.department.Department;
import com.knowledger.knowledger.infra.controller.department.DepartmentDetailDTO;
import com.knowledger.knowledger.infra.gateways.department.IDepartmentGateway;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentGetAll {

    private final IDepartmentGateway _iDepartmentGateway;
    private final IMapperDTO<DepartmentDetailDTO, Department> _iMapper;

    public DepartmentGetAll(IDepartmentGateway iDepartmentGateway, IMapperDTO<DepartmentDetailDTO, Department> iMapper) {
        _iDepartmentGateway = iDepartmentGateway;
        _iMapper = iMapper;
    }

    public List<DepartmentDetailDTO> getAll() {
        var departments = _iDepartmentGateway.getAll();
        return _iMapper.toDtoList(departments);
    }

}