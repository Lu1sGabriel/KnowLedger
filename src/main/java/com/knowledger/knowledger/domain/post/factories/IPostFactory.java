package com.knowledger.knowledger.domain.post.factories;

import com.knowledger.knowledger.domain.post.Post;
import com.knowledger.knowledger.infra.persistence.user.UserEntity;

public interface IPostFactory {

    Post create(UserEntity user, Long postTypeId, Long postStatusId, String title, String content);

}
