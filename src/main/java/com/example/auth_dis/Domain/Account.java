package com.example.auth_dis.Domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Account {
    @Id
//    @NotBlank(message = "닉네임을 작성해주세요.")
    @Column(nullable = false, unique = true, length = 30)
    private String username;

    //    @NotBlank(message = "이메일을 작성해주세요."id)
//    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Column(nullable = false, unique = true, length = 50)
    private String email;

//    @NotBlank(message = "비밀번호를 작성해주세요")

    private String password;

    private String role;
}
