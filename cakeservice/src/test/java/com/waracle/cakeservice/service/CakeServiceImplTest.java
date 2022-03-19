package com.waracle.cakeservice.service;

import com.waracle.cakeservice.exceptions.DatabaseOperationException;
import com.waracle.cakeservice.model.Cake;
import com.waracle.cakeservice.model.DataResponse;
import com.waracle.cakeservice.repository.CakeRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.waracle.cakeservice.utils.ResponseUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CakeServiceImplTest {
    @InjectMocks
    private CakeServiceImpl service;

    @Mock
    private CakeRepo repo;

    @Test
    void getCakesByClientName() {
        when(repo.findAllCakesByClientName(anyString())).thenReturn(Arrays.asList(getCake("Cake1"), getCake("Cake1")));

        DataResponse actual = service.getCakesByClientName("Cake1");

        assertNotNull(actual);
        assertEquals(2, actual.getCakes().size());
    }

    @Test
    void getCakesByClientNameNotExistingCakeInDB() {
        when(repo.findAllCakesByClientName(anyString())).thenReturn(new ArrayList<>());

        DataResponse actual = service.getCakesByClientName("Cake2");

        assertNotNull(actual);
        assertEquals(0, actual.getCakes().size());
    }

    @Test
    void getCakesByClientNameDBException() {
        when(repo.findAllCakesByClientName(anyString())).thenThrow(new PersistenceException());

        assertThrows(DatabaseOperationException.class, () -> service.getCakesByClientName("Ravi"));
    }

    @Test
    void getCakesForAllClients() {
        when(repo.findAll()).thenReturn(Arrays.asList(getCake("Cake1"), getCake("Cake1")));

        DataResponse actual = service.getCakesForAllClients();

        assertNotNull(actual);
        assertEquals(2, actual.getCakes().size());
    }

    @Test
    void addCake() {
        when(repo.save(any())).thenReturn(getCake("Cake1"));
        Cake actual = service.addCake(getCake(" "));

        assertNotNull(actual);
        assertEquals("Cake1", actual.getName());
    }
}