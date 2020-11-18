package com.supermetrics;

import java.util.ArrayList;
import java.util.List;

import com.supermetrics.client.SupermetricsSocialNetworkClient;
import com.supermetrics.model.Post;
import com.supermetrics.service.ProcessUserPostDataService;
import com.supermetrics.service.ProcessUserPostDataServiceImpl;

public class SupermetricsSocialNetwork {

	private final static int TOTAL_PAGES = 10;

	public static void main(String[] args) {
		SupermetricsSocialNetworkClient supermetricsSocialNetworkClient = new SupermetricsSocialNetworkClient();

		String slToken = supermetricsSocialNetworkClient.registerSLToken();

		List<Post> userPosts = new ArrayList<>();
		for (int i = 1; i <= TOTAL_PAGES; i++) {
			userPosts.addAll(supermetricsSocialNetworkClient.fetchAllUserPosts(slToken, i));
		}

		ProcessUserPostDataService processUserPostDataService = new ProcessUserPostDataServiceImpl();
		processUserPostDataService.getUserPostsStatsAndGenerateJSONOutput(userPosts);
<<<<<<< HEAD
=======
		/*echoing if the app ran successfully
		System.out.println("data processing done! check the output file JSONOutput.txt");*/
>>>>>>> 56c8ce100951602787e014d0ff67f52e68e891a4

	}

}
