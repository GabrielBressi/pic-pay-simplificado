package com.picpaysimplificado.dtos;

import com.picpaysimplificado.models.users.UserRole;

public record RegisterDTO (String login, String password, UserRole role){
}
