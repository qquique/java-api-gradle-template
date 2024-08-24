import com.qquique.jag.domain.entity.User;
import com.qquique.jag.infraestructure.database.repository.UserRepositoryImpl;
import com.qquique.jag.infraestructure.database.repository.exception.RepositoryException;
import org.hibernate.query.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private UserRepositoryImpl userRepository;

    @BeforeEach
    public void setUp() {
        when(sessionFactory.openSession()).thenReturn(session);
        userRepository = new UserRepositoryImpl(sessionFactory);
    }

    @Test
    public void testFindById_UserExists() {
        User user = new User();
        user.setId(1L);
        when(session.find(User.class, 1L)).thenReturn(user);

        User result = userRepository.findById(1L).orElse(null);

        assertEquals(user, result);
    }

    @Test
    public void testFindById_UserDoesNotExist() {
        when(session.find(User.class, 1L)).thenReturn(null);

        User result = userRepository.findById(1L).orElse(null);

        assertNull(result);
    }

    @Test
    public void testFindById_DatabaseException() {
        when(session.find(User.class, 1L)).thenThrow(new RepositoryException("Database error", null));

        assertThrows(RepositoryException.class, () -> userRepository.findById(1L));
    }

    @Test
    public void testSave_NewUser() {
        User user = new User();
        user.setUsername("Test User");
        user.setPassword("Password");
        Transaction mockTransaction = mock(Transaction.class);
        when(session.beginTransaction()).thenReturn(mockTransaction);

        userRepository.save(user);

        verify(session).persist(user);
    }

    @Test
    public void testDeleteById_UserExists() {
        User user = new User();
        user.setId(1L);
        when(session.find(User.class, 1L)).thenReturn(user);
        Transaction mockTransaction = mock(Transaction.class);
        when(session.beginTransaction()).thenReturn(mockTransaction);

        userRepository.deleteById(1L);

        verify(sessionFactory).openSession();
        verify(session).find(User.class, 1L);
        verify(session).remove(user);
    }
}