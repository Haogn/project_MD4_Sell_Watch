package rikkei.academy.dto.request;

import rikkei.academy.util.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRegisterDTO {
    @NotEmpty(message = "Tên người dùng không được để rỗng")
    private String userName ;
    @Email(message = "Email không đúng định dạng")
    @NotEmpty(message = "Email không được để rỗng")
    private String email ;
    @NotEmpty(message = "Password không được để rỗng")
    @Size(min = 8 , max = 20, message = "Password ít nhất 8 ký tự , nhiều nhất 20 ký tự")
    private String password ;

    private String confirmPassword ;
    private Boolean status ;
    private Role role = Role.USER;
}
