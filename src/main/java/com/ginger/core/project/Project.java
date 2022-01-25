package com.ginger.core.project;

import com.ginger.core.comment.Comment;
import com.ginger.core.project.payload.ProjectCreateInput;
import com.ginger.core.project.tags.Tags;
import com.ginger.core.user.User;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToOne
    private User user;

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
