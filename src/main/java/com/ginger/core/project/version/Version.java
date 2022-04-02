package com.ginger.core.project.version;

import com.ginger.core.project.Project;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Version implements Serializable {

    private static final String COMMENT_DEFAULT_FIRST_VERSION = "Version created by default for first version.";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer value;

    @Column
    private String comment;

    @ManyToOne
    private Project project;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public static Version createFirstByProject(Project project) {
        Version version = new Version();
        version.setProject(project);
        version.setValue(1);
        version.setComment(COMMENT_DEFAULT_FIRST_VERSION);
        return version;
    }

}
