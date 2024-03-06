package com.tranhunghoan.Springboot.ecommercebackend.service;

import com.tranhunghoan.Springboot.ecommercebackend.api.exception.EmailFailureException;
import com.tranhunghoan.Springboot.ecommercebackend.api.exception.UserAlreadyExistsException;
import com.tranhunghoan.Springboot.ecommercebackend.api.exception.UserNotVerifiedException;
import com.tranhunghoan.Springboot.ecommercebackend.api.model.LoginBody;
import com.tranhunghoan.Springboot.ecommercebackend.api.model.RegistrationBody;
import com.tranhunghoan.Springboot.ecommercebackend.model.LocalUser;
import com.tranhunghoan.Springboot.ecommercebackend.model.VerificationToken;
import com.tranhunghoan.Springboot.ecommercebackend.model.dao.LocalUserDAO;
import com.tranhunghoan.Springboot.ecommercebackend.model.dao.VerificationTokenDAO;
import com.tranhunghoan.Springboot.ecommercebackend.model.enumerate.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class UserService {
    private final LocalUserDAO localUserDAO;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;
//    private EmailService emailService;
    private final VerificationTokenDAO verificationTokenDAO;

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException {

        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with username " + registrationBody.getUsername() + " already exists");
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFirstname(registrationBody.getFirstname());
        user.setLastname(registrationBody.getLastname());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        user.setRole(Role.USER);
        VerificationToken verificationToken = createVerificationToken(user);
//        emailService.sendVerificationEmail(verificationToken);
        return localUserDAO.save(user);
    }
    public  String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if(opUser.isPresent())
        {
            LocalUser user = opUser.get();
            if(encryptionService.verifyPassword(loginBody.getPassword(),user.getPassword()))
            {
                if(user.isEmailVerified()){
                    return jwtService.generateJWT(user);
                }else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.size() ==0 || verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
                    if (resend) {
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenDAO.save(verificationToken);
//                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }

            }
        }
        return  null;
    }
    public VerificationToken createVerificationToken (LocalUser user){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }
    @Transactional
    public boolean verifyUser(String token){
        Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
        if (opToken.isPresent())
        {
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if (!user.isEmailVerified()){
                user.setEmailVerified(true);
                localUserDAO.save(user);
                verificationTokenDAO.deleteByUser(user);
                return true;
            }
        }
        return false;
    }
}
