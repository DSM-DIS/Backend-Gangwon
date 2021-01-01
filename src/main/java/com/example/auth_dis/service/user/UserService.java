package com.example.auth_dis.service.user;

import com.example.auth_dis.paylod.*;

public interface UserService {
    void SIGN_IN(UserRequest user);
    StatusResponse CheckId(IdResponse id);
    StatusResponse CheckUsername(NameResponse username);
    UserIdResponse GET_INFO_BY_ACCESS(String AccessToken);
    UserNameResponse GetUserName(String AccessToken);

}
