//package com.Spring.elitedemo1.Services;
//
//import com.Spring.elitedemo1.Model.Otp;
//import com.Spring.elitedemo1.Repository.otpRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//import java.security.SecureRandom;
//import java.time.LocalDateTime;
//import java.util.Random;
//
//@Service
//public class OtpService {
//
//    @Autowired
//    private otpRepo otpRepository;
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    // Generate OTP and send email
//    public void generateOtp(String email) {
//
//        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
//                + "abcdefghijklmnopqrstuvwxyz"
//                + "0123456789"
//                + "@#$%&*!";
//
//        int otpLength = 6;
//        SecureRandom random = new SecureRandom();
//        StringBuilder otp1 = new StringBuilder();
//
//        for (int i = 0; i < otpLength; i++) {
//            otp1.append(chars.charAt(random.nextInt(chars.length())));
//        }
//
//        String code = otp1.toString();
//
//        // store OTP against email (DB / Map / Cache)
//        // send OTP via email
//
//
//        // Save OTP
//        Otp otp = new Otp();
//        otp.setEmail(email);
//        otp.setCode(code);
//        otp.setExpiryTime(LocalDateTime.now().plusMinutes(10)); // valid for 10 min
//        otpRepository.deleteByEmail(email); // delete old OTP if exists
//        otpRepository.save(otp);
//
//        // Send Email
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject("Your OTP for Password Reset");
//        message.setText("Your OTP is: " + code + ". It is valid for 10 minutes.");
//        mailSender.send(message);
//    }
//
//    // Verify OTP
//    public boolean verifyOtp(String email, String code) {
//        return otpRepository.findByEmailAndCode(email, code)
//                .filter(otp -> otp.getExpiryTime().isAfter(LocalDateTime.now()))
//                .isPresent();
//    }
//
//    public void clearOtp(String email) {
//        otpRepository.deleteByEmail(email);
//    }
//}
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
