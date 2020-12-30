package com.example.auth_dis.service.user;

import com.example.auth_dis.paylod.*;

public interface UserService {
    void SIGN_IN(UserResponse user);
    StatusResponse CheckId(IdResponse id);
    UserIdResponse GET_INFO_BY_ACCESS(String AccessToken);
    UserNameResponse GetUserName(String AccessToken);

}
