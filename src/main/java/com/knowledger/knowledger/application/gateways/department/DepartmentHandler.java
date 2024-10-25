package com.knowledger.knowledger.application.gateways.department;

import com.knowledger.knowledger.commom.mapper.IMapper;
import com.knowledger.knowledger.domain.department.Department;
import com.knowledger.knowledger.infra.gateways.department.IDepartmentGateway;
import com.knowledger.knowledger.infra.persistence.department.DepartmentEntity;
import com.knowledger.knowledger.infra.persistence.department.IDepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentHandler implements IDepartmentGateway {

    private final IDepartmentRepository _departmentRepository;
    private final IMapper<DepartmentEntity, Department> _iMapper;

    public DepartmentHandler(IDepartmentRepository departmentRepository, IMapper<DepartmentEntity, Department> iMapper) {
        _departmentRepository = departmentRepository;
        _iMapper = iMapper;
    }

    @Override
    public List<Department> getAll() {
        var departmentEntities = _departmentRepository.findAll();
        return _iMapper.toDomainList(departmentEntities);
    }

}