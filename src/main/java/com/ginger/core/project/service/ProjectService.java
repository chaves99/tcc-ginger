package com.ginger.core.project.service;

import com.ginger.core.project.Project;
import com.ginger.core.project.payload.ProjectCreateInput;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<Project> getAll();

    List<Project> findByUserId(Long id);

    Optional<Project> findById(Long id);

    void deleteById(Long id);

    Project createNew(ProjectCreateInput input);
}
