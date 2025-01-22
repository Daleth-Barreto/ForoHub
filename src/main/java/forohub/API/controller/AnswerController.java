package forohub.API.controller;

import forohub.API.domain.profile.DTOS.DtoListProfile;
import forohub.API.domain.profile.DTOS.DtoRegisterProfile;
import forohub.API.domain.profile.DTOS.DtoUpdateProfile;
import forohub.API.domain.profile.Profile;
import forohub.API.domain.profile.ProfileRepository;
import forohub.API.domain.topic.Topic;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuario", description = "Operaciones CRUD en la entidad usuario-perfil")
@Validated
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping
    @Operation(summary = "Obtiene todos los usuarios")
    public ResponseEntity<Page<DtoListProfile>> listProfiles(@PageableDefault(size = 5) Pageable pageable) {
        Page<DtoListProfile> profiles = profileRepository.findByActiveTrue(pageable)
                .map(this::convertToDto);
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene el registro de un usuario por ID")
    public ResponseEntity<DtoListProfile> findProfileById(@PathVariable Long id) {
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        return optionalProfile
                .map(profile -> ResponseEntity.ok(convertToDto(profile)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registra un usuario en la base de datos")
    public ResponseEntity<DtoListProfile> createProfile(@RequestBody @Valid DtoRegisterProfile dtoRegisterProfile,
                                                        UriComponentsBuilder uriComponentsBuilder) {
        Profile profile = new Profile(null, dtoRegisterProfile.name(), dtoRegisterProfile.email(), true, null, null, null);
        profileRepository.save(profile);
        URI location = uriComponentsBuilder.path("/usuario/{id}")
                .buildAndExpand(profile.getId())
                .toUri();
        return ResponseEntity.created(location).body(convertToDto(profile));
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Actualiza los datos de un usuario")
    public ResponseEntity<DtoListProfile> updateProfile(@RequestBody @Valid DtoUpdateProfile dtoUpdateProfile) {
        Optional<Profile> optionalProfile = profileRepository.findById(dtoUpdateProfile.id());
        if (optionalProfile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Profile profile = optionalProfile.get();
        profile.updateData(dtoUpdateProfile);
        return ResponseEntity.ok(convertToDto(profile));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Marca al usuario como inactivo")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        if (optionalProfile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Profile profile = optionalProfile.get();
        profile.deactivateProfile();
        return ResponseEntity.noContent().build();
    }

    // Método auxiliar para convertir Profile a DtoListProfile
    private DtoListProfile convertToDto(Profile profile) {
        return new DtoListProfile(
                profile.getId(),
                profile.getName(),
                profile.getEmail(),
                profile.getTopicList().isEmpty() ? null :
                        profile.getTopicList().stream()
                                .map(Topic::getTitle)
                                .collect(Collectors.toList())
        );
    }
}
