package com.Spring.elitedemo1.Services;
import com.Spring.elitedemo1.Model.User;
import com.Spring.elitedemo1.Repository.userRepo;
import com.Spring.elitedemo1.Util.JwtUtil;
import com.Spring.elitedemo1.dto.AuthResponse;
import com.Spring.elitedemo1.dto.LoginRequest;
import com.Spring.elitedemo1.dto.SignUpResponse;
import com.Spring.elitedemo1.dto.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticationServices {

    @Autowired
    private userRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtUtil jwtUtil;

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email is required"
            );
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid email format"
            );
        }
    }

    // ✅ SIGNUP
    public SignUpResponse signup(SignupRequest request) {

        // 1️⃣ Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already registered"
            );
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

// ✅ Return ID, Name, Email
        return new SignUpResponse(
                user.getLuserid(),
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );
    }



    public AuthResponse login(LoginRequest request) {

       try {
            authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                           request.getPassword()
                    )
            );
        } catch (Exception e)
       {
           throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password"
            );
        }


        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "User not found"
                ));

        // 3️⃣ Generate JWT AFTER login
        String token = jwtUtil.generateToken(user.getEmail(), user.getLuserid());

        return new AuthResponse( user.getLuserid(),token ,user.getName(),user.getEmail());
    }


    public User getUserById(String id)
    {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
