package com.stackroute.userauthenticationservice.service;

import com.stackroute.userauthenticationservice.entity.Role;
import com.stackroute.userauthenticationservice.repository.RoleRepository;
import com.stackroute.userauthenticationservice.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    List<Role> roles;

    @BeforeEach
    public void setUp() {
        roles = Stream.of(new Role(1, "OWNER"), new Role(2, "CUSTOMER")).collect(Collectors.toList());
    }

    @AfterEach
    public void tearDown() {
        roles = null;
    }

    @Test
    void initRolesTest() {
        when(roleRepository.saveAll(roles)).thenReturn(roles);
        assertEquals(roles, roleService.initRoles(roles));

        verify(roleRepository, times(1)).saveAll(any());
    }
}
