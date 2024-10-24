package com.knowledger.knowledger.infra.controller.department;

import com.knowledger.knowledger.application.usecases.department.DepartmentGetAll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private DepartmentGetAll departmentGetAll;

    @GetMapping(name = "/getAll")
    public ResponseEntity<List<DepartmentDetailDTO>> getAll() {
        var departments = departmentGetAll.getAll();
        return ResponseEntity.ok().body(departments);
    }

}