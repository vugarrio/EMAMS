package es.ugarrio.emv.post.service.mapper;

import es.ugarrio.emv.post.domain.Post;
import es.ugarrio.emv.post.service.dto.PostDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDTO postToPostDto(Post post);

    Post postDTOToPost(PostDTO postDTO);

    List<PostDTO> postsToPostDtos(List<Post> post);

//    default Post postFromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        Post post = new Post();
//        post.setId(id);
//        return post;
//    }
}
