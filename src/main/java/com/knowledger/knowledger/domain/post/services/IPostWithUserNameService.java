package com.knowledger.knowledger.domain.post.services;

import com.knowledger.knowledger.domain.post.Post;

public interface IPostWithUserNameService {
    Post enrichWithUserName(Post post);
}
