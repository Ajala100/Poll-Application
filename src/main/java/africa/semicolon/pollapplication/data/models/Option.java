package africa.semicolon.pollapplication.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotBlank(message = "Option value cannot be blank")
    private String value;

    @JsonIgnore
    private Integer count;

    @OneToMany
    @JsonIgnore
    private List<AppUser> selectedBy;

}
