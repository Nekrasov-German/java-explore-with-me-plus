package ru.practicum.service.admin_ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.service.dal.UserRepository;
import ru.practicum.service.dto.NewUserRequest;
import ru.practicum.service.dto.UserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository repository;

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        return null;
    }

    @Override
    @Transactional
    public UserDto createUser(NewUserRequest dto) {
        return null;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {

    }
}
