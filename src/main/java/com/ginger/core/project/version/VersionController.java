package com.ginger.core.project.version;

import com.ginger.core.project.version.service.VersionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("project/version/")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VersionController {

    VersionService versionService;

    @GetMapping("{projectId}")
    public ResponseEntity<List<Version>> getAllVersionByProject(@PathVariable Long projectId) {
        List<Version> versions = versionService.findAllVersionByProject(projectId);
        if (versions != null && !versions.isEmpty()) {
            return ResponseEntity.ok(versions);
        }
        return ResponseEntity.notFound().build();
    }
}

