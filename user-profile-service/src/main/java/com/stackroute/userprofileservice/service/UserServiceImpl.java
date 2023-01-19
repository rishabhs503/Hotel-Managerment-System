package com.stackroute.userprofileservice.service;


import com.stackroute.userprofileservice.configuration.Producer;
import com.stackroute.userprofileservice.exception.UserFoundException;
import com.stackroute.userprofileservice.exception.UserNotFoundException;
import com.stackroute.userprofileservice.models.Users;
import com.stackroute.userprofileservice.payload.EmailDto;
import com.stackroute.userprofileservice.payload.SmsDto;
import com.stackroute.userprofileservice.payload.UserDto;
import com.stackroute.userprofileservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Producer producer;

    /**
     * This method allows to Add new User.
     * The registration is done on basis of unique email id.
     * The User will receive a unique Otp which he has to enter for successful registration.
     * @author Swastika Shanker
     */

    @Value("${server.port}")
    private int port;

    @Override
    public Users saveUser(Users user) throws UserFoundException {
        Users newUser = userRepository.findByEmailId(user.getEmailId());

        if (newUser != null) {


            throw new UserFoundException("User already present with " + user.getEmailId());
        } else {

            String otp = generateOtp();

            user.setOtp(otp);

            user.setVerified(false);

            newUser = userRepository.save(user);


           EmailDto emailDto = new EmailDto();
           emailDto.setUserId(newUser.getUserId());
           emailDto.setFirstName(newUser.getFirstName());
           emailDto.setEmail(newUser.getEmailId());
           emailDto.setOtp(newUser.getOtp());
           emailDto.setPort(port);

           producer.sendMessageToRabbitMqForEmail(emailDto);
            SmsDto smsDto = new SmsDto();
            smsDto.setUserId(newUser.getUserId());
            smsDto.setFirstName(newUser.getFirstName());
            smsDto.setMobileNumber(newUser.getMobileNumber());
            smsDto.setOtp(newUser.getOtp());
            smsDto.setPort(port);

           producer.sendMessageToRabbitMqForSMSRegistration(smsDto);


        }

        return newUser;
    }


    /**
     * This method allows to fetch the records of Users from the Database.
     * @author Swastika Shanker
     */

    @Override
    public List<Users> getAllUsers() throws UserNotFoundException{


        List<Users> list = this.userRepository.findAll();

        if (list.size() <= 0){
            throw new UserNotFoundException();
        }else {

            return list;
        }
    }

    /**
     * This method allows to fetch the record of a User by ID from the Database.
     * @author Swastika Shanker
     */

    @Override
    public Users getUserById(int id) throws UserNotFoundException{

        Users user =userRepository.findById(id);


        if (user == null) {
            throw new UserNotFoundException();
        } else {

            return user;
        }
    }


    /**
     * This method allows to delete the record of a User by ID from the Database.
     * @author Swastika Shanker
     */

    @Override
    public Boolean deleteUser(int userId) throws UserNotFoundException {

            boolean flag = false;
        Users user1= userRepository.findById(userId);
            if (user1 == null) {
                throw new UserNotFoundException();
            } else {
                userRepository.deleteById(userId);

                UserDto userDto = new UserDto();
                userDto.setEmail(user1.getEmailId());
                producer.sendMessageToRabbitMqToDeleteUser(userDto.getEmail());

                flag = true;
            }
            return flag;

    }



    /**
     * This method allows to update the record of a User by ID from the Database.
     * @author Swastika Shanker
     */
    @Override
    public Users updateUser(Users user, int userId) throws UserNotFoundException {

        Users user1= userRepository.findById(userId);
        if (user1 == null){

            throw new UserNotFoundException();

        }else {

            user1.setUserId(userId);
            user1.setRole(user1.getRole());

            user1.setFirstName(user.getFirstName());
            user1.setLastName(user.getLastName());
            user1.setEmailId(user1.getEmailId());
            user1.setAddress(user.getAddress());
            user1.setDob(user.getDob());
            user1.setGender(user.getGender());
            user1.setCreatedAt(user.getCreatedAt());
            user1.setMobileNumber(user.getMobileNumber());

            userRepository.save(user1);

            UserDto userDto = new UserDto();
            userDto.setEmail(user1.getEmailId());

            userDto.setPassword(user.getPassword());
            userDto.setRole(user1.getRole());

            producer.sendMessageToRabbitMqToUpdateUser(userDto);
        }


        return user1;
    }


    /**
     * This method is used to verify the otp generated to reset the password.
     *  @author Swastika Shanker
     */

    @Override
    public String updatePassword(Users user, int userId,String otp) {


        Users user1 = userRepository.findById(userId);
        if (user1.getOtp().equals(otp)) {


            user1.setPassword(user.getPassword());
            userRepository.save(user1);

            UserDto userDto = new UserDto();
            userDto.setEmail(user1.getEmailId());
            userDto.setPassword(user1.getPassword());
            userDto.setRole(user1.getRole());
            producer.sendMessageToRabbitMqToUpdateUser(userDto);


           EmailDto emailDto = new EmailDto();
           emailDto.setEmail(user1.getEmailId());
           emailDto.setFirstName(user1.getFirstName());
           producer.sendMessageToRabbitMqForEmailResetSuccess(emailDto);

            return "Otp verified. Password has been reset successfully.";

        } else
            return "Otp invalid. Password reset has failed.";



    }


    /**
     *This method is used for dual factor authentication of the User while registration.
     * After successful registration it sends User data to authentication service.
     * @author Swastika Shanker
     */

    @Override
    public String verifyOtp(int id, String num1) {

        Users newUser = userRepository.findById(id);

        if (num1.equals(newUser.getOtp()))
        {
            newUser.setVerified(true);
            userRepository.save(newUser);

            UserDto userDto = new UserDto();
            userDto.setEmail(newUser.getEmailId());
            userDto.setPassword(newUser.getPassword());
            userDto.setRole(newUser.getRole());

            EmailDto emailDto=new EmailDto();
            emailDto.setFirstName(newUser.getFirstName());
            emailDto.setEmail(newUser.getEmailId());

            producer.sendMessageToRabbitMqToSaveUser(userDto);
            producer.sendMessageToRabbitMqForEmailSuccess(emailDto);

            return "Otp verified.Registration is Successful.";
        }else
            return "Otp Invalid.Registration is failed.";
    }


    /**
     * This method is used when a user clicks on "Forgot password".
     * Unique otp is generated for valid user emailId.
     * @author Swastika Shanker
     */
    @Override
    public String forgotPasswordByEmail(String emailId) throws UserNotFoundException{

        Users newUser = userRepository.findByEmailId(emailId);


            String otpReset = generateOtp();

            newUser.setOtp(otpReset);
            userRepository.save(newUser);

        EmailDto emailDto= new EmailDto();
        emailDto.setEmail(newUser.getEmailId());
        emailDto.setOtp(newUser.getOtp());

        producer.sendMessageToRabbitMqForEmailReset(emailDto);


        return "Otp has been sent.Kindly enter to reset password.";
    }

    /**
     * This method is used when a user clicks on "Forgot password".
     * Unique otp is generated for valid user mobile number.
     * @author Swastika Shanker
     */
    @Override
    public String forgotPasswordByMobile(String mobileNumber) {
        Users newUser = userRepository.findByMobileNumber(mobileNumber);


        String otpReset = generateOtp();

        newUser.setOtp(otpReset);
        userRepository.save(newUser);

        SmsDto smsDto = new SmsDto();
        smsDto.setMobileNumber(newUser.getMobileNumber());
        smsDto.setFirstName(newUser.getFirstName());
        smsDto.setPort(port);
        smsDto.setOtp(newUser.getOtp());

        producer.sendMessageToRabbitMqForSMSForgotPassword(smsDto);

        return "Otp has been sent.Kindly enter to reset password.";


    }


    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphabets = upper + lower ;

    public static String generateOtp() {
        String otp = "";
        int min = 0;
        int max = alphabets.length() - 1;
        for (int i = 0; i < 4; i++) {
            int randomNumber = (int) (Math.random() * (max - min) + min);
            otp  += alphabets.charAt(randomNumber);
        }
        for (int i = 0; i < 4; i++) {
            int randomNumber = (int) (Math.random() * (10 - min) + min);
            otp += digits.charAt(randomNumber);
        }
        List<String> characters = Arrays.asList(otp.split(""));
        Collections.shuffle(characters);
        String afterShuffle = "";
        for (String character : characters)
        {
            afterShuffle += character;
        }

        return afterShuffle;
    }
}

