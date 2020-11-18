package com.supermetrics.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.supermetrics.model.Post;

import junit.framework.TestCase;

public class ProcessUserPostDataServiceImplTest extends TestCase{
	
	private List<Post> posts = new ArrayList<Post>();
	private ProcessUserPostDataServiceImpl processUserPostDataServiceImpl = new ProcessUserPostDataServiceImpl();
	
	@Override
	protected void setUp() throws Exception {
	
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
	            .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
	            .appendPattern("xxx")
	            .toFormatter();
		
		
		Post post = new Post();
		post.setId("post5faf7e24bda9c_e3d25a82");
		post.setFromName("Lashanda Small");
		post.setFromId("user_12");
		post.setMessage("science dramatic buy field director accident introduction siege seem contraction extraterrestrial offense sample monopoly message write food fit area whip stress make sip wisecrack treasurer accident language policeman resort bill preparation parachute book fireplace plaster prosecute reserve feature reptile approval stain grudge");
        post.setType("status");	
		post.setCreatedTime(LocalDateTime.parse(((CharSequence) "2020-05-29T19:07:12+00:00"), formatter));
		
			
		Post post1 = new Post();
		post1.setId("post5faf7e24bdaa1_e57b4569");
		post1.setFromName("Macie Mckamey");
		post1.setFromId("user_11");
		post1.setMessage("expertise counter falsify absorb authority instal bar coin loan speech deficiency multimedia member age essay slap highway definition important trail tenant flower extension design brave dragon cash short circuit sip stain story building waste broken bullet inn blade marsh shell trail culture tenant smash trick reputation business depression tie factor test damage useful carbon final house wall allocation resource expertise instrument patch depend stain biology corn tidy protection depend ban shout cash point forget charm stride export tick rally opposition");
        post1.setType("status");
        post1.setCreatedTime(LocalDateTime.parse(((CharSequence) "2020-05-29T19:07:12+00:00"), formatter));
		
		
		Post post2 = new Post();
		post2.setId("post5faf7e24bdab8_020cdc45");
		post2.setFromName("Maxie Marceau");
		post2.setFromId("user_8");
		post2.setMessage("complication murder corruption dynamic arrow mathematics magnetic kick opposition fuel rotation indoor hike");
	    post2.setType("status");
		post2.setCreatedTime(LocalDateTime.parse(((CharSequence) "2020-06-24T19:07:12+00:00"), formatter));
		
		Post post3 = new Post();
		post3.setId("post5faf7e24bdab8_020cdc45");
		post3.setFromName("Maxie Marceau");
		post3.setFromId("user_12");
		post3.setMessage("graphic glow chief establish rear cord entitlement duck flawed keep Europe birthday gutter mail nest passage return truth reduction loud begin plain experiment breakfast format grand balance point peasant relevance electron dismissal hiccup important final shaft");
	    post3.setType("status");
		post3.setCreatedTime(LocalDateTime.parse(((CharSequence) "2020-05-14T19:07:12+00:00"), formatter));
		
		Post post4 = new Post();
		post4.setId("post5faf7e24bdab8_020cdc45");
		post4.setFromName("Maxie Marceau");
		post4.setFromId("user_12");
		post4.setMessage("thinker diplomat penny bishop definite skeleton pest");
	    post4.setType("status");
		post4.setCreatedTime(LocalDateTime.parse(((CharSequence) "2020-05-04T19:07:12+00:00"), formatter));
		
		posts.add(post);
		posts.add(post1);
		posts.add(post2);
		posts.add(post3);
		posts.add(post4);
		
	}
	
	@Test
	public void testAvgCharLengthOfPostsPerMonth() {
		Map<String, Integer> expectedResult = new HashMap<>();
		expectedResult.put("MAY", 302);
		expectedResult.put("JUNE", 107);
		
		Map<String, Integer> actualResult = processUserPostDataServiceImpl.avgCharLengthOfPostsPerMonth(posts);
		
		assertEquals("Average character length of posts per month not matching with expected values",
				expectedResult, actualResult);
		
	}
	
	@Test
	public void testTotalPostsSplitByWeekNumber() {
		Map<String, Integer> expectedResult = new HashMap<>();
		expectedResult.put("22", 2);
		expectedResult.put("26", 1);
		expectedResult.put("20", 1);
		expectedResult.put("19", 1);
		
		Map<String, Integer> actualResult = processUserPostDataServiceImpl.totalPostsSplitByWeekNumber(posts);
		
		assertEquals("Total posts split by week number are not matching with expected values", 
				expectedResult, actualResult);
		
	}
	
	@Test
	public void testLongestPostByCharLengthPerMonth() {
		Map<String, String> expectedResult = new HashMap<>();
		expectedResult.put("MAY", "expertise counter falsify absorb authority instal bar coin loan speech deficiency multimedia member age essay slap highway definition important trail tenant flower extension design brave dragon cash short circuit sip stain story building waste broken bullet inn blade marsh shell trail culture tenant smash trick reputation business depression tie factor test damage useful carbon final house wall allocation resource expertise instrument patch depend stain biology corn tidy protection depend ban shout cash point forget charm stride export tick rally opposition");
		expectedResult.put("JUNE", "complication murder corruption dynamic arrow mathematics magnetic kick opposition fuel rotation indoor hike");
		
		Map<String, String> actualResult = processUserPostDataServiceImpl.longestPostByCharLengthPerMonth(posts);
		
		assertEquals("Longest post by character length per month is not matching with expected result", 
				expectedResult, actualResult);
		
	}
	
	@Test
	public void testAverageNumberOfPostsPerUserPerMonth() {
		Map<String, Integer> expectedResult = new HashMap<>();
		expectedResult.put("MAY", 2);
		expectedResult.put("JUNE", 1);
		
		Map<String, Integer> actualResult = processUserPostDataServiceImpl.averageNumberOfPostsPerUserPerMonth(posts);
		
		assertEquals("Average number of posts per user per month is not matching with expected result", 
				expectedResult, actualResult);
		
	}

}
