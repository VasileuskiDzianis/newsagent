package by.htp.newsagent.controller;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import by.htp.newsagent.controller.form.Location;
import by.htp.newsagent.controller.form.LocationWebModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("test-servlet-context.xml")
@WebAppConfiguration
public class HomeControllerTest extends HomeController {
	
	@Autowired
    private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testHome() throws Exception {
		this.mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("about"))
        .andExpect(forwardedUrl("/WEB-INF/layouts/newsagent.jsp"))
		.andExpect(model().attribute("locationModel", allOf(
				Matchers.<LocationWebModel>hasProperty("currentLocation", equalTo(Location.ABOUT)), 
				Matchers.<LocationWebModel>hasProperty("previousLocation", equalTo(Location.NEWS))))
		);
		
		this.mockMvc.perform(get("/about"))
		.andExpect(status().isOk())
		.andExpect(view().name("about"))
		.andExpect(forwardedUrl("/WEB-INF/layouts/newsagent.jsp"))
		.andExpect(model().attribute("locationModel", allOf(
				Matchers.<LocationWebModel>hasProperty("currentLocation", equalTo(Location.ABOUT)), 
				Matchers.<LocationWebModel>hasProperty("previousLocation", equalTo(Location.NEWS))))
				);
	}
}
