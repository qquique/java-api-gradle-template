import com.qquique.jag.application.dto.UserDTO;
import com.qquique.jag.application.mapper.UserMapper;
import com.qquique.jag.application.service.UserService;
import com.qquique.jag.application.service.exception.ServiceException;
import com.qquique.jag.domain.entity.User;
import com.qquique.jag.infraestructure.database.repository.UserRepositoryImpl;
import com.qquique.jag.infraestructure.database.repository.exception.RepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    public void testGetAllUsers_Success() {
        List<User> userList = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(userList);

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(userList.size(), result.size());
        verify(userRepository).findAll();
        // You might want to add more specific assertions based on mapping logic
    }

    @Test
    public void testGetAllUsers_RepositoryException() {
        when(userRepository.findAll()).thenThrow(new RepositoryException("Database error",null));

        assertThrows(ServiceException.class, () -> userService.getAllUsers());
        verify(userRepository).findAll();
    }

    @Test
    public void testGetUserById_Success() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        // Add more assertions based on mapping logic
        verify(userRepository).findById(1L);
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserDTO result = userService.getUserById(1L);

        assertNull(result);
        verify(userRepository).findById(1L);
    }

    @Test
    public void testGetUserById_RepositoryException() {
        when(userRepository.findById(1L)).thenThrow(new RepositoryException("Database error", null));

        assertThrows(ServiceException.class, () -> userService.getUserById(1L));
        verify(userRepository).findById(1L);
    }

}