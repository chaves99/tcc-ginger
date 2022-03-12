package com.ginger.core.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreateInput {

    private Long userId;

    private String name;

    private String description;

    private List<Long> tagIds;
}
