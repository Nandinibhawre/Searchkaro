
package com.Spring.elitedemo1.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    private final WebClient webClient = WebClient.create("https://api.brevo.com/v3");

    // email -> OTP data
    private final Map<String, OtpData> otpStore = new ConcurrentHashMap<>();

    private static final String CHARSET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";

    private static final SecureRandom random = new SecureRandom();

    // ✅ Generate OTP
    public void generateOtp(String email) {
        String otp = generateSecureOtp(8); // 8 chars
        Instant expiry = Instant.now().plusSeconds(5 * 60); // 5 minutes

        otpStore.put(email, new OtpData(otp, expiry));
        sendOtpEmail(email, otp);
    }

    // ✅ Verify OTP
    public boolean verifyOtp(String email, String otp) {
        OtpData data = otpStore.get(email);
        if (data == null) return false;
        if (Instant.now().isAfter(data.expiry())) return false;
        return data.otp().equals(otp);
    }

    // ✅ Clear OTP
    public void clearOtp(String email) {
        otpStore.remove(email);
    }

    // 🔐 Secure OTP generator
    private String generateSecureOtp(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(CHARSET.charAt(random.nextInt(CHARSET.length())));
        }
        return sb.toString();
    }

    // 📧 Send email via Brevo API
    private void sendOtpEmail(String toEmail, String otp) {
        webClient.post()
                .uri("/smtp/email")
                .header("api-key", apiKey)
                .bodyValue(Map.of(
                        "sender", Map.of(
                                "email", senderEmail,
                                "name", senderName
                        ),
                        "to", new Object[]{
                                Map.of("email", toEmail)
                        },
                        "subject", "Your Password Reset OTP",
                        "htmlContent",
                        "<h2>Elite Security</h2>" +
                                "<p>Your OTP is:</p>" +
                                "<h1>" + otp + "</h1>" +
                                "<p>Valid for 5 minutes.</p>"
                ))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // 🔒 OTP record
    private record OtpData(String otp, Instant expiry) {}
}
