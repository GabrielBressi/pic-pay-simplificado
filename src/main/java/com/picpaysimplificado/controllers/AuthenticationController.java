package com.picpaysimplificado.controllers;

import com.picpaysimplificado.dtos.AuthDTO;
import com.picpaysimplificado.dtos.LoginResponseDTO;
import com.picpaysimplificado.dtos.RegisterDTO;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.infra.security.TokenService;
import com.picpaysimplificado.models.users.UserModel;
import com.picpaysimplificado.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO data) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserDTO data) {
        if(this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserModel newUser = new UserModel(
                data.firstname(),
                data.lastname(),
                data.document(),
                data.email(),
                encryptedPassword,
                data.balance(),
                data.role()
        );

        this.userRepository.save(newUser);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


}
