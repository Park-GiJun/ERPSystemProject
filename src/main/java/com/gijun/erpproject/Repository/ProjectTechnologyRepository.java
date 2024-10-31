package com.gijun.erpproject.Repository;

import com.gijun.erpproject.Entity.ProjectTechnology;
import com.gijun.erpproject.Enum.TechnologyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectTechnologyRepository extends JpaRepository<ProjectTechnology, Long> {
    List<ProjectTechnology> findByProjectId(Long projectId);
    List<ProjectTechnology> findByProjectIdAndCategory(Long projectId, TechnologyCategory category);
}
