package com.ginger.core.project;

import java.util.List;
import java.util.Optional;

import com.ginger.core.project.payload.ProjectCreateInput;
import com.ginger.core.project.tags.TagsRepository;
import com.ginger.core.user.User;
import com.ginger.core.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequestMapping(value = "project")
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {

    ProjectRepository projectRepository;

    UserRepository userRepository;

    TagsRepository tagsRepository;

    @GetMapping
    public ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.ok(projectRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<List<Project>> getAllByUser(@PathVariable Long id) {
        Example<Project> example = Example.of(Project.builder().user(User.builder().id(id).build()).build());
        List<Project> projects = this.projectRepository.findAll(example);
        System.err.println(projects);
        if (projects.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(projects);
    }

    @PostMapping("create")
    public ResponseEntity<Project> create(@RequestBody ProjectCreateInput input) {
        Project project = new Project();
        project.setName(input.getName());
        project.setUser(userRepository.getById(input.getUserId()));
        project.setTags(tagsRepository.findByIdIn(input.getTagIds()));
        project = projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

}
