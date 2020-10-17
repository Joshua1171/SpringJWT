package com.software.SecurityJWT.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityMixin {
    @JsonCreator
    SimpleGrantedAuthorityMixin(@JsonProperty("authority")String role){}
}
