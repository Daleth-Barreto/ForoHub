package forohub.API.controller;

import forohub.API.domain.topic.CreateTopicService;
import forohub.API.domain.topic.DTOS.DtoRegisterTopic;
import forohub.API.domain.topic.DTOS.DtoTopicList;
import forohub.API.domain.topic.DTOS.DtoUpdateTopic;
import forohub.API.domain.topic.Topic;
import forohub.API.domain.topic.TopicRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Topic", description = "Operaciones CRUD en la entidad topic")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CreateTopicService createTopicService;

    @GetMapping
    @Operation(summary = "Obtiene todos los topics")
    public ResponseEntity<Page<DtoTopicList>> listTopics(@PageableDefault(size = 5) Pageable pagination) {
        Page<DtoTopicList> topics = topicRepository.findByActiveTrue(pagination)
                .map(this::convertToDto);
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene el registro de un topic con ID")
    public ResponseEntity<DtoTopicList> findTopicById(@PathVariable Long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);

        return optionalTopic
                .map(topic -> ResponseEntity.ok(convertToDto(topic)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registra un nuevo topic en la base de datos")
    public ResponseEntity<DtoTopicList> create(@RequestBody @Valid DtoRegisterTopic dtoRegisterTopic,
                                               UriComponentsBuilder uriComponentsBuilder) {
        DtoTopicList createdTopic = createTopicService.create(dtoRegisterTopic);

        URI location = uriComponentsBuilder.path("/topicos/{id}")
                .buildAndExpand(createdTopic.id())
                .toUri();
        return ResponseEntity.created(location).body(createdTopic);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Actualiza los datos de un topic existente")
    public ResponseEntity<DtoTopicList> updateTopic(@RequestBody @Valid DtoUpdateTopic dtoUpdateTopic) {
        Optional<Topic> optionalTopic = topicRepository.findById(dtoUpdateTopic.id());

        if (optionalTopic.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topic topic = optionalTopic.get();
        topic.updateData(dtoUpdateTopic);
        return ResponseEntity.ok(convertToDto(topic));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Marca un topic registrado como inactivo")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);

        if (optionalTopic.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topic topic = optionalTopic.get();
        topic.deactivateTopic();
        return ResponseEntity.noContent().build();
    }

    // Método auxiliar para convertir Topic a DtoTopicList
    private DtoTopicList convertToDto(Topic topic) {
        return new DtoTopicList(topic);
    }
}
