package com.ginger.core.project.tags;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagsPayload {

    private String description;

    public Tags toTags() {
        return Tags.builder().description(this.getDescription()).build();
    }


}
