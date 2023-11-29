package rikkei.academy.model.entity;

import lombok.*;
import rikkei.academy.util.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    private int userId ;
    private String userName ;
    private String email ;
    private String password ;
    private Boolean status ;
    private Role role = Role.USER;
}
