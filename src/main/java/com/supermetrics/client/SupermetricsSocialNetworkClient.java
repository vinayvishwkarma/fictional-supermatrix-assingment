package com.supermetrics.client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.supermetrics.model.Post;

public class SupermetricsSocialNetworkClient {

	private static final String GET_USER_POSTS_URL = "https://api.supermetrics.com/assignment/posts";
	private static final String REGISTER_TOKEN_URL = "https://api.supermetrics.com/assignment/register";
	private static final String PARAM_1 = "sl_token";
	private static final String PARAM_2 = "page";
	private static HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	private static DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			.append(DateTimeFormatter.ISO_LOCAL_DATE_TIME).appendPattern("xxx").toFormatter();
	private static JSONParser parser = new JSONParser();

	public String registerSLToken() {
		Map<String, String> apiProperties = null;
		String slToken = null;
		try {
			apiProperties = this.loadAPIProperties();
			slToken = this.loadTokenFromPropertyFile();

			String json = new StringBuilder().append("{").append("\"client_id\"").append(":")
					.append("\"" + apiProperties.get("clientId") + "\"").append(",").append("\"email\"").append(":")
					.append("\"" + apiProperties.get("email") + "\"").append(",").append("\"name\"").append(":")
					.append("\"" + apiProperties.get("name") + "\"").append("}").toString();

			if (!isTokenValid(slToken)) {
				HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json))
						.uri(URI.create(REGISTER_TOKEN_URL)).header("Content-Type", "application/json").build();

<<<<<<< HEAD
				HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
=======
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			/*testing if old token is expired and need to register new token 
			System.out.println("registering new token ");*/
>>>>>>> 56c8ce100951602787e014d0ff67f52e68e891a4

				if (response.statusCode() != 200) {
					throw new RuntimeException("Failed to process the request. Status code: " + response.statusCode());
				}

				slToken = extractSLTokenFromResponse(response.body());
				this.updateTokenInPropertyFile(slToken);
			}

		} catch (IOException | InterruptedException | ParseException e) {
			e.printStackTrace();
		}
<<<<<<< HEAD

=======
		
		/*testing purpose printing the logs if token is valid
		else {
			System.out.println(" old token is working fine ");

		}*/
		
>>>>>>> 56c8ce100951602787e014d0ff67f52e68e891a4
		return slToken;
	}

	private String extractSLTokenFromResponse(String response) throws ParseException {
		JSONObject responseData = (JSONObject) parser.parse(response);
		JSONObject data = (JSONObject) responseData.get("data");
		return (String) data.get("sl_token");
	}

	public List<Post> fetchAllUserPosts(String token, Integer page) {

		String json = new StringBuilder().append(GET_USER_POSTS_URL).append("?").append(PARAM_1).append("=")
				.append(token).append("&").append(PARAM_2).append("=").append(Integer.toString(page)).toString();

		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(json)).build();
		List<Post> posts = new ArrayList<Post>();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			posts = this.getPostsFromResponse(response.body());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return posts;

	}

	private List<Post> getPostsFromResponse(String response) {

		List<Post> userPosts = new ArrayList<>();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) parser.parse(response);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject data = (JSONObject) jsonObject.get("data");
		JSONArray posts = (JSONArray) data.get("posts");

		for (int i = 0; i < posts.size(); i++) {
			JSONObject post = (JSONObject) posts.get(i);
			Post p = new Post();
			p.setId((String) post.get("id"));
			p.setFromName((String) post.get("from_name"));
			p.setFromId((String) post.get("from_id"));
			p.setMessage((String) post.get("message"));
			p.setType((String) post.get("type"));
			p.setCreatedTime(LocalDateTime.parse((CharSequence) post.get("created_time"), formatter));
			userPosts.add(p);

		}
		return userPosts;
	}

	private Map<String, String> loadAPIProperties() throws IOException {

		Map<String, String> apiProperties = new HashMap<>();
		InputStream inputstream = null;

		try {
			Properties properties = new Properties();

			inputstream = new FileInputStream("api-parameter.properties");
			properties.load(inputstream);

			apiProperties.put("clientId", properties.getProperty("client_id"));
			apiProperties.put("email", properties.getProperty("email"));
			apiProperties.put("name", properties.getProperty("name"));

			inputstream.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			inputstream.close();
		}

		return apiProperties;

	}

	private String loadTokenFromPropertyFile() throws IOException {

		InputStream inputstream = null;
		String token = null;

		Properties properties = new Properties();

		try {
			inputstream = new FileInputStream("token.properties");
			properties.load(inputstream);
			token = properties.getProperty("sl_token");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			inputstream.close();
		}

		return token;

	}

	private boolean updateTokenInPropertyFile(String token) throws IOException {

		boolean success = false;
		FileOutputStream outputStream = null;

		Properties properties = new Properties();
		try {
			outputStream = new FileOutputStream("token.properties");
			properties.setProperty("sl_token", token);
			properties.store(outputStream, null);
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			outputStream.close();
		}

		return success;

	}

	private static boolean isTokenValid(String token) {
		boolean isValid = false;

		String json = new StringBuilder().append(GET_USER_POSTS_URL).append("?").append(PARAM_1).append("=")
				.append(token).append("&").append(PARAM_2).append("=").append(Integer.toString(1)).toString();

		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(json)).build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

<<<<<<< HEAD
		if (response.statusCode() == 200) {
=======
		/*JSONObject responseData = (JSONObject) parser.parse(response.body());
		JSONObject error = (JSONObject) responseData.get("error");
		String message= (String) error.get("message");
		
		System.out.println(" checking token isValidateToken");*/
		
		if (response.statusCode() != 500) {
>>>>>>> 56c8ce100951602787e014d0ff67f52e68e891a4
			isValid = true;
		}

		return isValid;

	}

}
