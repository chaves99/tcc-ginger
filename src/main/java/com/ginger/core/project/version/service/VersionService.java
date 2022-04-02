package com.ginger.core.project.version.service;

import com.ginger.core.project.Project;
import com.ginger.core.project.version.Version;

import java.util.List;

public interface VersionService {

    List<Version> findAllVersionByProject(Long projectId);

    Version createFirstVersionByProject(Project project);
}
