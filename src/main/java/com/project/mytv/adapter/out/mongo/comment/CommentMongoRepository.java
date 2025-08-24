package com.project.mytv.adapter.out.mongo.comment;


import org.springframework.data.domain.Limit;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CommentMongoRepository extends CrudRepository<CommentDocument, String> {

    List<CommentDocument> findAllByVideoIdAndParentIdAndPublishedAtLessThanEqualOrderByPublishedAtDesc(
            String videoId, String parentId, LocalDateTime offset, Limit limit);

    List<CommentDocument> findAllByParentIdAndPublishedAtLessThanEqualOrderByPublishedAtDesc(
            String parentId, LocalDateTime offset, Limit limit);
}
