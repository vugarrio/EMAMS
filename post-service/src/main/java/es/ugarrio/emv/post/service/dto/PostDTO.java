package es.ugarrio.emv.post.service.dto;


import es.ugarrio.emv.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private String id;

    @NotNull
    @Size(max = 256)
    private String title;

    private String text;

    @NotNull
    private Long userId;
}
