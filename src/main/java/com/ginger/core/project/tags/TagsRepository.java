package com.ginger.core.project.tags;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags, Long> {

    List<Tags> findByIdIn(Collection<Long> tagIds);
}
