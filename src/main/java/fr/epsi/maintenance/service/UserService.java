package fr.epsi.maintenance.service;

import fr.epsi.maintenance.dto.UserDto;

public interface UserService {
    boolean createUser(UserDto userDto);
}
