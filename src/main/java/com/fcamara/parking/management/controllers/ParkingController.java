package com.fcamara.parking.management.controllers;

import com.fcamara.parking.management.dtos.responses.ErrorResponseDTO;
import com.fcamara.parking.management.dtos.responses.ParkingTransactionResponseDTO;
import com.fcamara.parking.management.services.ParkingTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
@Tag(name = "Parking Management", description = "API for managing vehicle entries, exits, and parking transactions within the parking system.")
public class ParkingController {

    private final ParkingTransactionService parkingTransactionService;

    @PostMapping(value = "/entry", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Register vehicle entry", description = "Registers the entry of a vehicle into the parking lot.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered vehicle entry",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingTransactionResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ParkingTransactionResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid input data or validation failed",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Establishment or vehicle not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    })
    })
    public ResponseEntity<ParkingTransactionResponseDTO> registerEntry(@RequestParam String establishmentId, @RequestParam String plate) {
        var parkingTransaction = parkingTransactionService.registerEntry(establishmentId, plate);

        return ResponseEntity.ok(ParkingTransactionResponseDTO.toDTO(parkingTransaction));
    }

    @PostMapping(value = "/exit", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Register vehicle exit", description = "Registers the exit of a vehicle from the parking lot.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered vehicle exit",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingTransactionResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ParkingTransactionResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid input data or validation failed",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Establishment or vehicle not found, or vehicle is not currently parked",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorResponseDTO.class))
                    })
    })
    public ResponseEntity<ParkingTransactionResponseDTO> registerExit(@RequestParam String establishmentId, @RequestParam String plate) {
        var parkingTransaction = parkingTransactionService.registerExit(establishmentId, plate);

        return ResponseEntity.ok(ParkingTransactionResponseDTO.toDTO(parkingTransaction));
    }
}
