package com.ginger.core.project;

import com.ginger.core.project.payload.ProjectCreateInput;
import com.ginger.core.project.service.ProjectService;
import com.ginger.core.project.tags.TagsRepository;
import com.ginger.core.user.User;
import com.ginger.core.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequestMapping(value = "project")
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {

    ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.ok(projectService.getAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Project>> getAllByUser(@PathVariable Long id) {
        List<Project> projects = projectService.findByUserId(id);
        if (projects == null || projects.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
             this.projectService.deleteById(id);
        } catch (Exception e) {
            log.debug("delete - Exception:", e);
            return ResponseEntity //
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) //
                    .build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("create")
    public ResponseEntity<Project> create(@RequestBody ProjectCreateInput input) {
        Project project = projectService.createNew(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @GetMapping("{id}")
    public ResponseEntity<Project> getById(@PathVariable Long id) {
        Optional<Project> project = projectService.findById(id);
        if (project.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(project.get());
    }

}
