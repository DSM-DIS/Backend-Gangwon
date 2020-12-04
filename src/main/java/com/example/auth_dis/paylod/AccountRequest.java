package com.example.auth_dis.paylod;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

}
