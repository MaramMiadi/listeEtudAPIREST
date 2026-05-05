package maram.isett.listeetudapirest.service;

import maram.isett.listeetudapirest.dto.EtudiantDTO;
import maram.isett.listeetudapirest.entity.Departement;
import maram.isett.listeetudapirest.entity.Etudiant;
import maram.isett.listeetudapirest.exceptions.BusinessException;
import maram.isett.listeetudapirest.exceptions.ResourceNotFoundException;
import maram.isett.listeetudapirest.mapper.EtudiantMapper;
import maram.isett.listeetudapirest.repository.DepartementRepository;
import maram.isett.listeetudapirest.repository.EtudiantRepository;
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
        Etudiant etudiant = new Etudiant();
        EtudiantDTO dto = new EtudiantDTO();
        when(repository.findAll()).thenReturn(List.of(etudiant));
        when(mapper.toDTO(etudiant)).thenReturn(dto);

        // when
        List<EtudiantDTO> result = service.findAll();

        // then
        assertThat(result).hasSize(1);
        verify(repository).findAll();
    }

    @Test
    void shouldFindById() {
        // given
        Long id = 1L;
        Etudiant etudiant = new Etudiant();
        EtudiantDTO dto = new EtudiantDTO();
        when(repository.findById(id)).thenReturn(Optional.of(etudiant));
        when(mapper.toDTO(etudiant)).thenReturn(dto);

        // when
        EtudiantDTO result = service.findById(id);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        // given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldCreateEtudiant() {
        // given
        EtudiantDTO dto = new EtudiantDTO();
        dto.setCin("12345678");
        dto.setEmail("test@example.com");
        dto.setDepartementId(1L);

        Departement dept = new Departement();
        Etudiant etudiant = new Etudiant();

        when(repository.existsByCin(dto.getCin())).thenReturn(false);
        when(repository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(departementRepository.findById(1L)).thenReturn(Optional.of(dept));
        when(mapper.toEntity(eq(dto), any())).thenReturn(etudiant);
        when(repository.save(any())).thenReturn(etudiant);
        when(mapper.toDTO(etudiant)).thenReturn(dto);

        // when
        EtudiantDTO result = service.create(dto);

        // then
        assertThat(result).isNotNull();
        verify(repository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenCinExists() {
        // given
        EtudiantDTO dto = new EtudiantDTO();
        dto.setCin("12345678");
        when(repository.existsByCin("12345678")).thenReturn(true);

        // then
        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Un étudiant avec ce CIN existe déjà");
    }

    @Test
    void shouldDeleteEtudiant() {
        // given
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);

        // when
        service.delete(id);

        // then
        verify(repository).deleteById(id);
    }
}
