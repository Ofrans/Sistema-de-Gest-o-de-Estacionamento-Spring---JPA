package com.compasso.demo_park_api.web.controller;

import com.compasso.demo_park_api.entity.User;
import com.compasso.demo_park_api.service.UserService;
import com.compasso.demo_park_api.web.dto.UserCreateDTO;
import com.compasso.demo_park_api.web.dto.UserPasswordDto;
import com.compasso.demo_park_api.web.dto.UserResponseDto;
import com.compasso.demo_park_api.web.dto.mapper.UserMapper;
import com.compasso.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Contains all operations related to the resources for registering, editing and reading a user.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user", description = "Resource to create a new user",
            responses = {
                @ApiResponse(responseCode = "201", description = "Resource created successfully",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "User and email already registered",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                            @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDTO createDTO){
        User user1 =  userService.save(UserMapper.toUser(createDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user1));
    }

    @Operation(summary = "Retrieve a user by id", description = "Retrieve a user by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
        User user1 =  userService.searchById(id);
        return ResponseEntity.ok(UserMapper.toDto(user1));
    }

    @Operation(summary = "Update password", description = "Update password",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Update password successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Password do not match",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid or poorly formatted fields",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto dto){
        User user1 =  userService.editPassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List all registered users", description = "List all registered users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List all registered users",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class))))
            })
    @GetMapping
    public ResponseEntity <List<UserResponseDto>> getAll(){
        List<User> user1 =  userService.searchAll();
        return ResponseEntity.ok(UserMapper.toListDto(user1));
    }
}
