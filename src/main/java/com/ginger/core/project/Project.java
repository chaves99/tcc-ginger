package com.ginger.core.project;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ginger.core.comment.Comment;
import com.ginger.core.project.payload.ProjectCreateInput;
import com.ginger.core.project.tags.Tags;
import com.ginger.core.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @OneToMany
    private List<Comment> comments;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    private User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany
    private List<Tags> tags;

    public static Project from(ProjectCreateInput input) {
        Project project = new Project();
        project.setName(input.getName());
        project.setUser(User.builder().id(input.getUserId()).build());
        project.setTags(input.getTagIds().stream().map(t -> Tags.builder().id(t).build())
                .collect(Collectors.toList()));
        return project;
    }
}
