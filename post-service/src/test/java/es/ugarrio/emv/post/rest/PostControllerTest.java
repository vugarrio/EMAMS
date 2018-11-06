package es.ugarrio.emv.post.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import es.ugarrio.emv.post.service.dto.PostDTO;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import es.ugarrio.emv.post.domain.Post;
import es.ugarrio.emv.post.service.PostService;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PostService postService;
	
	private static final String URL = "/api/posts";
	
	private Pageable pageRequest;	
	private final int PAGE_NUMBER = 0;
    private final int PAGE_SIZE = 5;
    private final String SORT_PARAM = "title";
    
    private ObjectMapper objectMapper = new ObjectMapper();
	
	@Before
	public void septup() {
//		Sort sort = new Sort(Sort.Direction.ASC, "title");
//        pageRequest = new PageRequest(0, 10, sort);
		
		 Sort sort = new Sort(Sort.Direction.ASC, SORT_PARAM);
         pageRequest = PageRequest.of(PAGE_NUMBER, PAGE_SIZE, sort);

         //given(searchService.findBySearchTerm(eq(SEARCH_TERM), isA(Pageable.class))).willReturn(emptyPage);
		
	}
	
	
	/* ********************************** */
	/*     TEST getAllPosts (Pageable)    */    
	/* ********************************** */
	
	@Test
    public void shouldReturnHttpResponseStatusOk() throws Exception {
		given( postService.findAll(isA(Pageable.class))).willReturn(createPage(2));
		mockMvc.perform(get(URL).param("page", String.valueOf(PAGE_NUMBER))
                .param("size", String.valueOf(PAGE_SIZE))
                .param("sort", SORT_PARAM)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
    }
	
	@Test
    public void shouldReturnPageNumberAndPageSizeAsJson() throws Exception {
		given( postService.findAll(isA(Pageable.class))).willReturn(createPage(2));
        mockMvc.perform(get(URL)
        		.param("page", String.valueOf(PAGE_NUMBER))
                .param("size", String.valueOf(PAGE_SIZE))
                .param("sort", SORT_PARAM))
            .andExpect(jsonPath("$.number", is(PAGE_NUMBER)))
            .andExpect(jsonPath("$.size", is(PAGE_SIZE)));
    }
	
	
	 @Test
     public void shouldReturnPTotalElementInformationAsJson() throws Exception {
		 given( postService.findAll(isA(Pageable.class))).willReturn(createPage(2));
         mockMvc.perform(get(URL)
        		 .param("page", String.valueOf(PAGE_NUMBER))
                 .param("size", String.valueOf(PAGE_SIZE))
                 .param("sort", SORT_PARAM))
            .andExpect(jsonPath("$.totalElements", is(2)));
     }
	 
	 
	 @Test
     public void shouldReturnOneTodoEntryAsJson() throws Exception {
		 PostDTO newPost = createPost("1");
		 given( postService.findAll(isA(Pageable.class))).willReturn(createPage(1));
         mockMvc.perform(get(URL)
        		 .param("page", String.valueOf(PAGE_NUMBER))
                 .param("size", String.valueOf(PAGE_SIZE))
                 .param("sort", SORT_PARAM))
	         .andExpect(jsonPath("$.content[*].id", hasItem(newPost.getId())))
	         .andExpect(jsonPath("$.content[*].title", hasItem(newPost.getTitle())))
	         .andReturn();
	 }
	 
	 
	 @Test
     public void WhenNoTodoEntriesAreFound () throws Exception {
		 given( postService.findAll(isA(Pageable.class))).willReturn(createPage(0));
         mockMvc.perform(get(URL)
        		 .param("page", String.valueOf(PAGE_NUMBER))
                 .param("size", String.valueOf(PAGE_SIZE))
                 .param("sort", SORT_PARAM))
         .andExpect(jsonPath("$.content", is(Collections.EMPTY_LIST)))
         .andExpect(jsonPath("$.totalElements", is(0)));
     }

	
	
	
	
	@Test
	public void getPostTest() throws Exception {
		given(postService.find("1")).willReturn(Optional.of(createPost("1")));
		mockMvc.perform(get(URL + "/1").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is("1")))
			.andExpect(jsonPath("$.title", is("Titulo 1")));
		
		// Tambien se puede hacer asi
		Optional<PostDTO> post = postService.find("1");
		assertThat(post).isEqualTo(Optional.of(createPost("1")));
		
	}
	
	
	@Test
	public void getPostNotFoundTest() throws Exception {
		given(postService.find("1")).willReturn(Optional.of(createPost("1")));
		mockMvc.perform(get(URL + "/2").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isNotFound());
	}
	
	
	//@Test /// Falla
	@Ignore
	/*public void createPost() throws Exception {
		given(postService.create(isA(Post.class))).willReturn(createPost(1L));
		
		mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createPost(1L))))
        .andExpect(status().isCreated());
		
	}*/

	private PostDTO createPost(String id) {
		PostDTO post = new PostDTO();
		post.setId(id);
		post.setTitle("Titulo " + id);
		post.setText("Es el test " + id);
		return post;
	}
	
	private Page<PostDTO> createPage(int numResults) {
		List<PostDTO> resultPost = new ArrayList<PostDTO>();
		Page<PostDTO> resultPage = null;
		
		if (numResults > 0 ) {
			for (int x=1; x <= numResults; x++) {
				resultPost.add(createPost(String.valueOf(x)));
			}
			resultPage = new PageImpl<>(resultPost, pageRequest, resultPost.size());
		} else {
			resultPage = new PageImpl<>(Arrays.asList(), pageRequest, 0);
		}
		return resultPage;		
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
