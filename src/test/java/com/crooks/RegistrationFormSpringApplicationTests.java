package com.crooks;

import com.crooks.entities.User;
import com.crooks.services.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RegistrationFormSpringApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegistrationFormSpringApplicationTests {

	@Autowired
	WebApplicationContext wac;

	@Autowired
	UserRepository userRepo;

	MockMvc mockMvc;


	@Before
	public void before(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //initializes the mvc
	}

	@Test
	public void atestAddUser() throws Exception {
		User user = new User();
		user.setUsername("Alice");
		user.setAddress("1338 Ronald Lane");
		user.setEmail("Alice@TIYO.com");

		ObjectMapper om = new ObjectMapper();		//Object mapper is needed to convert the user object
		String json = om.writeValueAsString(user);  //Converting the user into a json string
		mockMvc.perform(
				MockMvcRequestBuilders.post("/user")  // runs a fake POST route using the content added below
					.content(json)
					.contentType("application/json")  // need to specify type of information being passed through.
		);

		Assert.assertTrue(userRepo.count()==1);
	}

	@Test
	public void btestEditUser() throws Exception {
		User user = userRepo.findOne(1);
		user.setUsername("Bob");
		user.setEmail("Bob@TIYO.com");

		ObjectMapper om = new ObjectMapper();		//Object mapper is needed to convert the user object
		String json = om.writeValueAsString(user);  //Converting the user into a json string
		mockMvc.perform(
				MockMvcRequestBuilders.put("/user")  // runs a fake PUT route using the content added below
						.content(json)
						.contentType("application/json")  // need to specify type of information being passed through.
		);
		User editedUser = userRepo.findOne(1);
		Assert.assertTrue(editedUser.getUsername().equals(user.getUsername()));


	}

	@Test
	public void cTestGet() throws Exception {
		ResultActions ra =  mockMvc.perform(		//Capture result of the Get route in "ra"
				MockMvcRequestBuilders.get("/user")
		);
		MvcResult result = ra.andReturn();			//Dig into the results to find...
		MockHttpServletResponse response =  result.getResponse();  //The servers response which holds
		String json = response.getContentAsString();           //a Json  String

		ObjectMapper om = new ObjectMapper();
		ArrayList<HashMap<String,String>> userMaps = om.readValue(json, ArrayList.class); //Converting the Json String to something that can be used in java

		Assert.assertTrue(userMaps.size()==1 && userMaps.get(0).get("username").equals("Bob"));
	}

	@Test
	public void dtestDeleteUser() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/user/1")
		);
		Assert.assertTrue(userRepo.count()==0);

	}






}
