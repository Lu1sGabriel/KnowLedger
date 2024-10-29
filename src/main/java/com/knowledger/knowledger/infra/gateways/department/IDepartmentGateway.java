package com.knowledger.knowledger.infra.gateways.department;

import com.knowledger.knowledger.domain.post.department.Department;

import java.util.List;

public interface IDepartmentGateway {
    List<Department> getAll();
}