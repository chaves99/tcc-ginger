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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
        List<Project> projects = this.projectRepository
                .findAll(Example.of(Project.builder().user(User.builder().id(id).build()).build()));
        if (projects.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            this.projectRepository.deleteById(id);
        } catch (Exception e) {
            log.debug("delete - Exception:{}", e);
            return ResponseEntity //
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) //
                    .build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("create")
    public ResponseEntity<Project> create(@RequestBody ProjectCreateInput input) {
        Project project = Project.from(input);
        project.setUser(userRepository.getById(input.getUserId()));
        project.setTags(tagsRepository.findByIdIn(input.getTagIds()));
        project = projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

}
