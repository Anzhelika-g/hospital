//package org.example.hospital.service;
//
//import org.example.hospital.dto.UserDTO;
//
//import java.security.NoSuchAlgorithmException;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public interface ValidUser<T extends UserDTO> {
//
//    void addUser(T dto) throws NoSuchAlgorithmException;
//
//    default boolean isEmailValid(String email) {
//        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
//                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
//
//    default boolean isPasswordValid(String password) {
//        if (password.length() < 6) {
//            throw new RuntimeException("Password must be at least 6 characters long");
//        }
//
//        Pattern numberPattern = Pattern.compile("[0-9]");
//        Pattern uppercasePattern = Pattern.compile("[A-Z]");
//        Pattern lowercasePattern = Pattern.compile("[a-z]");
//        Pattern specialCharacterPattern = Pattern.compile("[$&+,:;=?@#|'<>.-^*()%!]");
//
//        Matcher number = numberPattern.matcher(password);
//        Matcher uppercase = uppercasePattern.matcher(password);
//        Matcher lowercase = lowercasePattern.matcher(password);
//        Matcher specialCharacter = specialCharacterPattern.matcher(password);
//
//        return number.find() && uppercase.find() && lowercase.find() && specialCharacter.find();
//    }
//}
//
//// // Check if user already exists by email
////        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
////            throw new IllegalArgumentException("Username is already in use");
////        }
////
////        if (!isEmailValid(userDTO.getEmail())) {
////            throw new RuntimeException("email is invalid");
////        }
////
////        if (!isPasswordValid(userDTO.getPassword())) {
////            throw new RuntimeException("password is invalid");
////        }
////        if (userDTO.getUsername() == null) {
////            throw new RuntimeException("Username must be specified");
////        }
////
////
////
////        // Convert UserDTO to User and encode the password
////        User user = userConverter.convertToEntity(userDTO, new User());
////        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
////
////        // Save to user repository
////        userRepository.save(user);