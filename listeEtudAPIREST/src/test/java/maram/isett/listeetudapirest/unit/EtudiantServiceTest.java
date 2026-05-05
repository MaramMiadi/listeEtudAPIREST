package maram.isett.listeetudapirest.unit;

import maram.isett.listeetudapirest.dto.EtudiantDTO;
import maram.isett.listeetudapirest.entity.Departement;
import maram.isett.listeetudapirest.entity.Etudiant;
import maram.isett.listeetudapirest.exceptions.BusinessException;
import maram.isett.listeetudapirest.exceptions.ResourceNotFoundException;
import maram.isett.listeetudapirest.mapper.EtudiantMapper;
import maram.isett.listeetudapirest.repository.DepartementRepository;
import maram.isett.listeetudapirest.repository.EtudiantRepository;
import maram.isett.listeetudapirest.service.EtudiantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository repository;

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private EtudiantMapper mapper;

    @InjectMocks
    private EtudiantService service;

    @Test
    void shouldReturnAllEtudiants() {
        // given
        when(repository.findAll()).thenReturn(List.of(new Etudiant()));

        // when
        List<EtudiantDTO> result = service.findAll();

        // then
        assertThat(result).hasSize(1);
    }
}
