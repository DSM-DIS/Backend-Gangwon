package com.example.auth_dis.Domain.user;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    private String email;

    @Column(name = "username")
    private String name;
    
    @Column(name = "password")
    private String password;

}
