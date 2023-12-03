package rikkei.academy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rikkei.academy.util.Role;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RespUserDTO {
    private int userId ;
    private String userName ;
    private String email ;
    private Boolean status ;
    private Role role = Role.USER;
}
