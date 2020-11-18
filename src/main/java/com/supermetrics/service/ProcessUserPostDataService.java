package com.supermetrics.service;

import java.util.List;

import com.supermetrics.dto.PostDto;
import com.supermetrics.model.Post;

public interface ProcessUserPostDataService {

	public void getUserPostsStatsAndGenerateJSONOutput(List<Post> posts);

}
