package com.ccwsz.server.dao.dock.course.video;

import com.ccwsz.server.dao.entity.VideoWatchEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoWatchRepository extends CrudRepository<VideoWatchEntity, Integer> {
    //通过用户id和课程id查找观看列表，以构造个性化观看信息
    List<VideoWatchEntity> findByUserIdAndCourseId(long userId, long courseId);
}
