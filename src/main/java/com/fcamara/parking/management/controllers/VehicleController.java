package com.fcamara.parking.management.controllers;

import com.fcamara.parking.management.dtos.requests.VehicleRequestDTO;
import com.fcamara.parking.management.dtos.responses.ErrorResponseDTO;
import com.fcamara.parking.management.dtos.responses.VehicleResponseDTO;
import com.fcamara.parking.management.models.Vehicle;
import com.fcamara.parking.management.services.VehicleService;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Tag(name = "Vehicle Management", description = "API for managing vehicle registrations, updates, and removals within the parking system.")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Create a new vehicle", description = "Creates a new vehicle with the provided details. All fields are mandatory.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created vehicle",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = VehicleResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid input data or validation failed",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    })
    })
    public ResponseEntity<VehicleResponseDTO> createVehicle(@RequestBody @Valid VehicleRequestDTO vehicleRequestDTO) {
        var vehicle = vehicleService.save(Vehicle.toModel(vehicleRequestDTO));

        return ResponseEntity.ok(VehicleResponseDTO.toDTO(Collections.singletonList(vehicle)));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get all vehicles", description = "Fetches all vehicles from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all vehicles",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = VehicleResponseDTO.class))
                    })
    })
    public ResponseEntity<VehicleResponseDTO> getAllVehicles() {
        var vehicles = vehicleService.getAll();

        return ResponseEntity.ok(VehicleResponseDTO.toDTO(vehicles));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get vehicle by ID", description = "Fetches a specific vehicle by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched vehicle",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = VehicleResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Vehicle not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    })
    })
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable String id) {
        var vehicle = vehicleService.getById(id);

        return ResponseEntity.ok(VehicleResponseDTO.toDTO(Collections.singletonList(vehicle)));
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Delete vehicle by ID", description = "Deletes a specific vehicle by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted vehicle", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Vehicle not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    })
    })
    public ResponseEntity<Void> deleteVehicleById(@PathVariable String id) {
        vehicleService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Update vehicle by ID", description = "Updates a specific vehicle by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated vehicle",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = VehicleResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid input data or validation failed",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Vehicle not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    })
    })
    public ResponseEntity<VehicleResponseDTO> updateVehicleById(@PathVariable String id, @RequestBody @Valid VehicleRequestDTO vehicleRequestDTO) {
        var vehicle = vehicleService.update(id, Vehicle.toModel(vehicleRequestDTO));

        return ResponseEntity.ok(VehicleResponseDTO.toDTO(Collections.singletonList(vehicle)));
    }
}
