package com.ginger.core.project.version.service.impl;

import com.ginger.core.project.Project;
import com.ginger.core.project.version.Version;
import com.ginger.core.project.version.VersionRepository;
import com.ginger.core.project.version.service.VersionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VersionServiceImpl implements VersionService {

    VersionRepository versionRepository;

    @Override
    public List<Version> findAllVersionByProject(Long projectId) {
        return versionRepository.findByProject_id(projectId);
    }

    @Override
    public Version createFirstVersionByProject(Project project) {
        return versionRepository.save(Version.createFirstByProject(project));
    }
}
