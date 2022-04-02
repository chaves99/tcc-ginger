package com.ginger.core.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ginger.core.comment.Comment;
import com.ginger.core.project.payload.ProjectCreateInput;
import com.ginger.core.project.tags.Tags;
import com.ginger.core.project.version.Version;
import com.ginger.core.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @OneToMany
    private List<Comment> comments;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    private User user;

    @ManyToOne
    private User orientador;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "project"})
    @ManyToMany
    @JoinTable(name = "project_tags",
            joinColumns = @JoinColumn(name = "id_project", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_tags"))
    private List<Tags> tags;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @JsonIgnoreProperties
    private List<Version> versions = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public static Project from(ProjectCreateInput input) {
        Project project = new Project();
        project.setName(input.getName());
        project.setDescription(input.getDescription());
        project.setUser(User.builder().id(input.getUserId()).build());
        project.setTags(input.getTagIds().stream().map(t -> Tags.builder().id(t).build())
                .collect(Collectors.toList()));
        return project;
    }

    public Optional<Version> getLastVersion() {
        if (getVersions() == null) return Optional.empty();
        return getVersions()
                .stream()
                .max(Comparator.comparing(Version::getValue));
    }

    public void addVersion(Version version) {
        if (versions != null) {
            versions = new ArrayList<>();
        }
        versions.add(version);
    }

}
