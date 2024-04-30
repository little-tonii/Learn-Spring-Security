package spring.security.learn.controllers;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import spring.security.learn.dtos.UserDTO;
import spring.security.learn.exceptions.DataNotFoundException;
import spring.security.learn.generics.DataResponse;
import spring.security.learn.models.User;
import spring.security.learn.responses.ErrorResponse;
import spring.security.learn.responses.UserResponse;
import spring.security.learn.responses.ValidationErrorResponse;
import spring.security.learn.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security/learn")
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody Optional<UserDTO> userDTO, BindingResult result) {
        try {
            if (userDTO.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        ErrorResponse.builder()
                                .status("fail")
                                .message("Please send data to request body")
                                .build());
            }
            if (result.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        ValidationErrorResponse.builder()
                                .status("fail")
                                .messages(result.getFieldErrors()
                                        .stream()
                                        .map(error -> error.getDefaultMessage())
                                        .collect(Collectors.toList()))
                                .build());
            }
            User user = userService.findUserByUsername(userDTO.get().getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(
                    DataResponse.builder()
                            .status("success")
                            .data(UserResponse.builder()
                                    .username(user.getUsername())
                                    .roles(user.getRoles())
                                    .build())
                            .build());
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(
                    HttpStatus.NOT_FOUND).body(
                            ErrorResponse
                                    .builder()
                                    .status("fail")
                                    .message("User not found.")
                                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR).body(
                            ErrorResponse.builder()
                                    .status("fail")
                                    .message("Something went wrong.")
                                    .build());
        }
    }
}
