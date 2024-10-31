package com.gijun.erpproject.Repository;

import com.gijun.erpproject.Entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    List<TaskComment> findByTaskId(Long taskId);
    List<TaskComment> findByMemberId(Long memberId);
}
