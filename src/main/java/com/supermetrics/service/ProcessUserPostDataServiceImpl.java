package com.supermetrics.service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermetrics.dto.PostDto;
import com.supermetrics.model.Post;

public class ProcessUserPostDataServiceImpl implements ProcessUserPostDataService {

	@Override
	public void getUserPostsStatsAndGenerateJSONOutput(List<Post> posts) {

		PostDto postDto = new PostDto();
		postDto.setAvgCharacterLengthOfPostsPerMonth(this.avgCharLengthOfPostsPerMonth(posts));
		postDto.setAvgNumberOfPostsPerUserPerMonth(this.averageNumberOfPostsPerUserPerMonth(posts));
		postDto.setLongestPostByCharacterLengthPerMonth(this.longestPostByCharLengthPerMonth(posts));
		postDto.setTotalPostsSplitByWeekNumber(this.totalPostsSplitByWeekNumber(posts));

		generateJSONOutputFile(postDto);
	}

	public Map<String, Integer> avgCharLengthOfPostsPerMonth(List<Post> posts) {

		Map<String, Integer> avgPostLengthPerMonth = new HashMap<>();
		Map<String, Integer> userDataMap = new HashMap<>();
		Map<String, Integer> numberOfPostsPerMonth = new HashMap<>();

		for (Post post : posts) {

			LocalDateTime createdTime = post.getCreatedTime();
			String postMonth = createdTime.getMonth().toString();
			String message = post.getMessage();
			int messageLength = message.length();

			if (numberOfPostsPerMonth.containsKey(postMonth)) {
				numberOfPostsPerMonth.put(postMonth, numberOfPostsPerMonth.get(postMonth) + 1);

			} else {
				numberOfPostsPerMonth.put(postMonth, 1);
			}

			if (userDataMap.containsKey(postMonth)) {
				userDataMap.put(postMonth, userDataMap.get(postMonth) + messageLength);

			} else {
				userDataMap.put(postMonth, messageLength);
			}

		}

		Iterator iterator = numberOfPostsPerMonth.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();

			int avgLength = ((userDataMap.get(entry.getKey())).intValue()) / (Integer) entry.getValue();

			avgPostLengthPerMonth.put(entry.getKey().toString(), avgLength);
			iterator.remove(); // avoids a ConcurrentModificationException
		}

		return avgPostLengthPerMonth;
	}

	public Map<String, String> longestPostByCharLengthPerMonth(List<Post> posts) {

		Map<String, String> longestPostPerMonth = new HashMap<>();

		for (Post post : posts) {

			LocalDateTime createdTime = post.getCreatedTime();
			String postMonth = createdTime.getMonth().toString();
			String message = post.getMessage();
			int messageLength = message.length();

			if (longestPostPerMonth.containsKey(postMonth)) {
				if (longestPostPerMonth.get(postMonth).length() < messageLength)
					longestPostPerMonth.put(postMonth, post.getMessage());
			} else {
				longestPostPerMonth.put(postMonth, post.getMessage());
			}
		}

		return longestPostPerMonth;
	}

	public Map<String, Integer> totalPostsSplitByWeekNumber(List<Post> posts) {

		Map<String, Integer> totalPostsPerWeek = new HashMap<>();

		for (Post post : posts) {

			LocalDateTime createdTime = post.getCreatedTime();
			WeekFields weekFields = WeekFields.of(Locale.getDefault());
			String weekNumber = String.valueOf(createdTime.get(weekFields.weekOfWeekBasedYear()));

			if (totalPostsPerWeek.containsKey(weekNumber)) {
				totalPostsPerWeek.put(String.valueOf(weekNumber), totalPostsPerWeek.get(weekNumber) + 1);
			} else {
				totalPostsPerWeek.put(String.valueOf(weekNumber), 1);
			}

		}

		return totalPostsPerWeek;
	}

	public Map<String, Integer> averageNumberOfPostsPerUserPerMonth(List<Post> posts) {
		Map<String, Integer> avgPostsPerUserPerMonth = new HashMap<>();
		Map<String, Map<String, Integer>> totalPostsPerUserPerMonth = new HashMap<>();
		Map<String, Integer> userPost = new HashMap<>();

		for (Post post : posts) {

			String userId = post.getFromId();
			LocalDateTime createdTime = post.getCreatedTime();
			String postMonth = createdTime.getMonth().toString();

			if (totalPostsPerUserPerMonth.containsKey(postMonth)) {
				if (userPost.containsKey(userId)) {
					userPost.put(userId, (userPost.get(userId)) + 1);

				} else {
					userPost.put(userId, 1);
				}
				Map<String, Integer> userPostTemp = totalPostsPerUserPerMonth.get(postMonth);
				userPostTemp.put(userId, (userPost.get(userId)) + 1);
				totalPostsPerUserPerMonth.put(postMonth, userPostTemp);
			} else {
				Map<String, Integer> tempMap = new HashMap<>();
				tempMap.put(userId, 1);
				totalPostsPerUserPerMonth.put(postMonth, tempMap);
			}

		}

		// Calculate average posts per user per month
		for (String month : totalPostsPerUserPerMonth.keySet()) {
			Map<String, Integer> map = totalPostsPerUserPerMonth.get(month);
			Iterator iterator = map.entrySet().iterator();

			int postsCount = 0;
			int userCount = map.size();

			for (int count : map.values()) {
				postsCount = postsCount + count;
			}

			int avg = postsCount / userCount;
			avgPostsPerUserPerMonth.put(month, avg);

		}
		return avgPostsPerUserPerMonth;

	}

	private static void generateJSONOutputFile(PostDto postDto) {
		ObjectMapper obj = new ObjectMapper();

		try {
			String postDtoJsonData = obj.writeValueAsString(postDto);

			FileWriter file = new FileWriter("JSONOutput.txt");
			file.write(postDtoJsonData);
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
