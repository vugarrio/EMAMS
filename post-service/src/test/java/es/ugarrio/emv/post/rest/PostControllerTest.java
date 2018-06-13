package es.ugarrio.emv.post.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import es.ugarrio.emv.post.domain.Post;
import es.ugarrio.emv.post.service.PostService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PostService postService;
	
	private static final String URL = "/api/posts";
	
	private Pageable pageRequest;
	
	@Before
	public void septup() {
		Sort sort = new Sort(Sort.Direction.ASC, "title");
        pageRequest = new PageRequest(0, 10, sort);
	}
	
	@Test
	public void getPostTest() throws Exception {
		given(postService.find("1")).willReturn(Optional.of(createPost()));		
		mvc.perform(get(URL + "/1").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is("1")))
			.andExpect(jsonPath("$.title", is("Titulo 1")));
		
		// Tambien se puede hacer asi
		Optional<Post> post = postService.find("1");
		assertThat(post).isEqualTo(Optional.of(createPost()));
		
	}
	
	
	@Test
	public void postNotFoundTest() throws Exception {
		mvc.perform(get(URL + "/2").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isNotFound());
	}
	
	
	@Test
	public void getAllPostsTest() throws Exception {
		List<Post> posts = Arrays.asList(createPost());		
		//Page<Post> page = new PageImpl<>(getUsersList(),  new PageRequest(0, 10), 1);
		
		Page<Post> page = new PageImpl<>(posts, pageRequest, 1);
        
		given( postService.findAll(any(Pageable.class))).willReturn(page);
		
		
		mvc.perform(get(URL).param("page", "0")
                .param("size", "10")
                .param("sort", "title")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
//				.andExpect(jsonPath("$.content[0].id", is("1")))
//				.andExpect(jsonPath("$.content[0].title", is("Titulo 1")));
//		
	}

	private Post createPost() {
		Post post = new Post();
		post.setId("1");
		post.setTitle("Titulo 1");
		post.setText("Es el test 1");
		return post;
	}
	
	///// Ejemplo post:
	//// https://github.com/hantsy/angularjs-springmvc-sample/blob/master/src/test/java/com/hantsylabs/restexample/springmvc/test/web/MockPostControllerTest.java
	
	
	// Tambien mirar:
	// https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/query-methods/src/test/java/net/petrikainulainen/springdata/jpa/web/TodoSearchControllerTest.java
	// https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/query-methods/src/test/java/net/petrikainulainen/springdata/jpa/web/TodoControllerTest.java
	
	
	// https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/query-methods/src/test/java/net/petrikainulainen/springdata/jpa/web/TodoSearchControllerTest.java
	
	// Hacer asi:  https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/query-methods/src/test/java/net/petrikainulainen/springdata/jpa/web/TodoControllerTest.java
	
	
	// Test a nivel de Integracion:  https://github.com/ryanmccormick/spring-boot-rest-best-practices/tree/master/src/test/java/com/example
	
	
}
