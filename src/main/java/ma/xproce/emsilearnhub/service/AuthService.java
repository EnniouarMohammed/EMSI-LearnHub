package ma.xproce.emsilearnhub.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.xproce.emsilearnhub.dto.RegisterRequest;
import ma.xproce.emsilearnhub.exceptions.SpringException;
import ma.xproce.emsilearnhub.model.NotificationEmail;
import ma.xproce.emsilearnhub.model.User;
import ma.xproce.emsilearnhub.model.VerificationToken;
import ma.xproce.emsilearnhub.repository.UserRepository;
import ma.xproce.emsilearnhub.repository.VerificationTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailService mailService;

    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to EMSI LearnHub, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }


    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringException("Invalid Token")));
    }

    private void fetchUserAndEnable(VerificationToken invalidToken) {
        String username = invalidToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
