package com.supermetrics.dto;

import java.util.List;
import java.util.Map;

public class PostDto {

	private Map<String, Integer> avgCharacterLengthOfPostsPerMonth;
	private Map<String, String> longestPostByCharacterLengthPerMonth;
	private Map<String, Integer> totalPostsSplitByWeekNumber;

	private Map<String, Integer> avgNumberOfPostsPerUserPerMonth;

	public Map<String, Integer> getAvgCharacterLengthOfPostsPerMonth() {
		return avgCharacterLengthOfPostsPerMonth;
	}

	public void setAvgCharacterLengthOfPostsPerMonth(Map<String, Integer> avgCharacterLengthOfPostsPerMonth) {
		this.avgCharacterLengthOfPostsPerMonth = avgCharacterLengthOfPostsPerMonth;
	}

	public Map<String, String> getLongestPostByCharacterLengthPerMonth() {
		return longestPostByCharacterLengthPerMonth;
	}

	public void setLongestPostByCharacterLengthPerMonth(Map<String, String> longestPostByCharacterLengthPerMonth) {
		this.longestPostByCharacterLengthPerMonth = longestPostByCharacterLengthPerMonth;
	}

	public Map<String, Integer> getTotalPostsSplitByWeekNumber() {
		return totalPostsSplitByWeekNumber;
	}

	public void setTotalPostsSplitByWeekNumber(Map<String, Integer> totalPostsSplitByWeekNumber) {
		this.totalPostsSplitByWeekNumber = totalPostsSplitByWeekNumber;
	}

	public Map<String, Integer> getAvgNumberOfPostsPerUserPerMonth() {
		return avgNumberOfPostsPerUserPerMonth;
	}

	public void setAvgNumberOfPostsPerUserPerMonth(Map<String, Integer> avgNumberOfPostsPerUserPerMonth) {
		this.avgNumberOfPostsPerUserPerMonth = avgNumberOfPostsPerUserPerMonth;
	}

	

}