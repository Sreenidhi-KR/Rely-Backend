package com.example.backend.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.example.backend.Bean.RefreshTokenDoctor;
import com.example.backend.Repository.DoctorRepository;
import com.example.backend.Repository.RefreshTokenDoctorRepository;
import com.example.backend.Security.Exception.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.backend.Bean.RefreshToken;
import com.example.backend.Repository.RefreshTokenRepository;
import com.example.backend.Repository.UserRepository;

@Service
public class RefreshTokenService {
    @Value("${had.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private RefreshTokenDoctorRepository refreshTokenDoctorRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public Optional<RefreshTokenDoctor> findDoctorByToken(String token) { return refreshTokenDoctorRepository.findByToken(token); }

    public RefreshToken createRefreshToken(int userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findUserById(userId));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshTokenDoctor createRefreshTokenDoctor(int doctorId){
        RefreshTokenDoctor refreshTokenDoctor = new RefreshTokenDoctor();

        refreshTokenDoctor.setDoctor(doctorRepository.findDocById(doctorId));
        refreshTokenDoctor.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshTokenDoctor.setToken(UUID.randomUUID().toString());

        refreshTokenDoctor = refreshTokenDoctorRepository.save(refreshTokenDoctor);
        return  refreshTokenDoctor;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    public RefreshTokenDoctor verifyExpirationDoctor(RefreshTokenDoctor token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenDoctorRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}