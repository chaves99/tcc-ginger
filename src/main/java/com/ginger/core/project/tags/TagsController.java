package com.ginger.core.project.tags;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "tags")
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagsController {

    TagsRepository tagsRepository;

    @GetMapping("")
    public ResponseEntity<List<Tags>> getAll() {
        List<Tags> tagsList = tagsRepository.findAll();
        return new ResponseEntity<List<Tags>>(tagsList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Tags> create(@RequestBody TagsPayload tag) {
        Tags tags = tagsRepository.save(tag.toTags());
        return ResponseEntity.status(HttpStatus.CREATED).body(tags);
    }


}
