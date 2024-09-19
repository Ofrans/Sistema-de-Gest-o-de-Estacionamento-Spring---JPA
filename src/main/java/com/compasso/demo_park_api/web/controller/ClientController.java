package com.compasso.demo_park_api.web.controller;

import com.compasso.demo_park_api.entity.Client;
import com.compasso.demo_park_api.jwt.JwtUserDetails;
import com.compasso.demo_park_api.service.ClientService;
import com.compasso.demo_park_api.service.UserService;
import com.compasso.demo_park_api.web.dto.ClientCreateDto;
import com.compasso.demo_park_api.web.dto.ClientResponseDto;
import com.compasso.demo_park_api.web.dto.UserResponseDto;
import com.compasso.demo_park_api.web.dto.mapper.ClientMapper;
import com.compasso.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Clients", description = "Contains all operations related to a client's resource")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;


    @Operation(summary = "Create new client", description = "Resource to create a new client linked to a registered user" +
            "Request requires use of a bearer token. Restricted access to Role='CLIENT'",
            responses = {
                @ApiResponse(responseCode = "201", description = "Resource created successfully",
                        content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ClientResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Client CPF is already registered in the system",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Appeal not processed due to lack of data or invalid data",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Resource does not allow the ADMIN profile",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> create(@RequestBody @Valid ClientCreateDto dto,
                                                    @AuthenticationPrincipal JwtUserDetails userDetails){
        Client client = ClientMapper.toClient(dto);
        client.setUser(userService.searchById(userDetails.getId()));
        clientService.save(client);
        return ResponseEntity.status(201).body(ClientMapper.toDto(client));
    }
}
