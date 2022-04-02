package com.ginger.core.project.service.impl;

import com.ginger.core.project.Project;
import com.ginger.core.project.ProjectRepository;
import com.ginger.core.project.payload.ProjectCreateInput;
import com.ginger.core.project.service.ProjectService;
import com.ginger.core.project.tags.TagsRepository;
import com.ginger.core.project.version.Version;
import com.ginger.core.project.version.service.VersionService;
import com.ginger.core.user.User;
import com.ginger.core.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;

    UserRepository userRepository;

    TagsRepository tagsRepository;

    VersionService versionService;

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findByUserId(Long id) {
        List<Project> projects = projectRepository
                .findAll(Example.of(Project.builder().user(User.builder().id(id).build()).build()));
        projects.get(0).getVersions();
        return projects;
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Project createNew(ProjectCreateInput input) {
        Project project = Project.from(input);
        project.setUser(userRepository.getById(input.getUserId()));
        project.setTags(tagsRepository.findByIdIn(input.getTagIds()));
//        project.getVersions().add(Version.createFirstByProject(project));
        project = projectRepository.save(project);
        Version firstVersion = versionService.createFirstVersionByProject(project);
        project.addVersion(firstVersion);
//        projectRepository.saveAndFlush(project);
        return project;
    }
}
