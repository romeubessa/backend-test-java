package com.fcamara.parking.management.controllers;

import com.fcamara.parking.management.dtos.requests.EstablishmentRequestDTO;
import com.fcamara.parking.management.dtos.responses.ErrorResponseDTO;
import com.fcamara.parking.management.dtos.responses.EstablishmentResponseDTO;
import com.fcamara.parking.management.dtos.responses.VehicleResponseDTO;
import com.fcamara.parking.management.models.Establishment;
import com.fcamara.parking.management.services.EstablishmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/establishments")
@RequiredArgsConstructor
@Tag(name = "Establishment Management", description = "API for managing establishment registrations, updates, and removals within the parking system.")
public class EstablishmentController {

    private final EstablishmentService establishmentService;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Create a new establishment", description = "Creates a new establishment with the provided details. All fields are mandatory.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created establishment",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EstablishmentResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = EstablishmentResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid input data or validation failed",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    })
    })
    public ResponseEntity<EstablishmentResponseDTO> createEstablishment(@RequestBody @Valid EstablishmentRequestDTO establishmentRequestDTO) {
        var establishment = establishmentService.save(Establishment.toModel(establishmentRequestDTO));

        return ResponseEntity.ok(EstablishmentResponseDTO.toDTO(Collections.singletonList(establishment)));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get all establishments", description = "Fetches all the establishments from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all establishments",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EstablishmentResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = EstablishmentResponseDTO.class))
                    })
    })
    public ResponseEntity<EstablishmentResponseDTO> getAllEstablishments() {
        var establishments = establishmentService.getAll();

        return ResponseEntity.ok(EstablishmentResponseDTO.toDTO(establishments));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get establishment by ID", description = "Fetches a specific establishment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched establishment",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EstablishmentResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = EstablishmentResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Establishment not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    })
    })
    public ResponseEntity<EstablishmentResponseDTO> getEstablishmentById(@PathVariable String id) {
        var establishment = establishmentService.getById(id);

        return ResponseEntity.ok(EstablishmentResponseDTO.toDTO(Collections.singletonList(establishment)));
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Delete establishment by ID", description = "Delete a specific establishment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted establishment", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Establishment not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    })
    })
    public ResponseEntity<Void> deleteEstablishmentById(@PathVariable String id) {
        establishmentService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Update establishment by ID", description = "Updates a specific establishment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated establishment",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = VehicleResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid input data or validation failed",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Establishment not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    })
    })
    public ResponseEntity<EstablishmentResponseDTO> updateEstablishmentById(@PathVariable String id, @RequestBody @Valid EstablishmentRequestDTO establishmentRequestDTO) {
        var establishment = establishmentService.update(id, Establishment.toModel(establishmentRequestDTO));

        return ResponseEntity.ok(EstablishmentResponseDTO.toDTO(Collections.singletonList(establishment)));
    }
}
