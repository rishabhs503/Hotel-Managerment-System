package com.stackroute.hotelpropertyservice.service;

import com.stackroute.hotelpropertyservice.Configuration.Producer;
import com.stackroute.hotelpropertyservice.exception.HotelAlreadyExistException;
import com.stackroute.hotelpropertyservice.exception.NoHotelFoundWithThisAddress;
import com.stackroute.hotelpropertyservice.exception.NoHotelFoundWithThisId;
import com.stackroute.hotelpropertyservice.exception.NoHotelFoundWithThisName;
import com.stackroute.hotelpropertyservice.model.Address;
import com.stackroute.hotelpropertyservice.model.Hotel;
import com.stackroute.hotelpropertyservice.model.Images;
import com.stackroute.hotelpropertyservice.payload.EmailDto;
import com.stackroute.hotelpropertyservice.payload.FeedbacksDto;
import com.stackroute.hotelpropertyservice.payload.SmsDto;
import com.stackroute.hotelpropertyservice.payload.UserDto;
import com.stackroute.hotelpropertyservice.repository.HotelRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author vipin
 */
@Service
public class HotelServiceImpl implements HotelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelServiceImpl.class);

    private final Producer producer;

    private final HotelRepository hotelRepository;

    private final AddressService addressService;

    private final RestTemplate restTemplate;

    @Autowired
    public HotelServiceImpl(Producer producer, HotelRepository hotelRepository, AddressService addressService, RestTemplate restTemplate) {
        this.producer = producer;
        this.hotelRepository = hotelRepository;
        this.addressService = addressService;
        this.restTemplate = restTemplate;
    }

    @Value("${user.getDetails}")
    private String userUrl;

    @Value("${feedback.getFeedbacks}")
    private String feedbackUrl;

    /**
     * @param hotelData
     * @return
     * @throws IOException
     */
    @Override
    public ResponseEntity<String> registerHotel(Hotel hotelData, String addressId, int userId) throws HotelAlreadyExistException {

        if (hotelRepository.findById(hotelData.getId()).isPresent()) {
            throw new HotelAlreadyExistException(hotelData.getId());
        } else {
            // save address in address collection
            Optional<Address> optional = addressService.getAddressById(addressId);

            // provide address reference in hotel collection
            if (!optional.isEmpty())
                hotelData.setAddress(optional.get());

            // fetching user details using rest template
            UserDto user = restTemplate.getForObject(userUrl + userId, UserDto.class);
            hotelData.setUser(user);
            // save details in hotel collection
            Hotel hotelDetails = hotelRepository.save(hotelData);

            // call dto function for sms & email
            sendNotificationOnRegisterForEmailAndSmsService(hotelDetails);

            return new ResponseEntity<String>("Successfully register : Hotel Id = " + hotelDetails.getId(), HttpStatus.OK);

        }
    }


    private void sendNotificationOnRegisterForEmailAndSmsService(Hotel hotelDetails) {
        EmailDto emailDto = new EmailDto();
        emailDto.setHotelName(hotelDetails.getName());
        emailDto.setEmail(hotelDetails.getUser().getEmailId());
        emailDto.setFirstName(hotelDetails.getUser().getFirstName());

        System.out.println(emailDto);

        SmsDto smsDto = new SmsDto();
        smsDto.setHotelName(hotelDetails.getName());
        smsDto.setFirstName(hotelDetails.getUser().getFirstName());
        smsDto.setContact_number(hotelDetails.getUser().getMobileNumber());

        System.out.println(smsDto);

        // for calling RabbitMq Message
        producer.sendMessageToRabbitMqForEmailNew(emailDto);
        producer.sendMessageToRabbitMqForSmsNew(smsDto);
    }

    /**
     * @param updatedhotelDetails
     * @param hotelId
     * @return
     */
    @Override
    public ResponseEntity<String> updateHotelDetails(Hotel updatedhotelDetails, Integer hotelId) throws NoHotelFoundWithThisId {
        // check hotel data is present or not
        Hotel hotelData = hotelRepository.findById(hotelId).orElse(null);
        if (hotelData == null) {
            throw new NoHotelFoundWithThisId();
        } else {
            // update hotel name
            if (updatedhotelDetails.getName() != null) {
                hotelData.setName(updatedhotelDetails.getName());
            }
            if (!updatedhotelDetails.getAminities().isEmpty()) {
                hotelData.setAminities(updatedhotelDetails.getAminities());
            }
            // update hotel details
            Hotel updatedDetails = hotelRepository.save(hotelData);

            sendNotificationOnUpdateForEmailAndSmsService(updatedDetails);
            return new ResponseEntity<>("Successfully data updated!!!", HttpStatus.OK);
        }
    }

    private void sendNotificationOnUpdateForEmailAndSmsService(Hotel updatedDetails) {
        EmailDto emailDto = new EmailDto();
        emailDto.setHotelName(updatedDetails.getName());
        emailDto.setEmail(updatedDetails.getUser().getEmailId());
        emailDto.setFirstName(updatedDetails.getUser().getFirstName());

        SmsDto smsDto = new SmsDto();
        smsDto.setHotelName(updatedDetails.getName());
        smsDto.setFirstName(updatedDetails.getUser().getFirstName());
        smsDto.setContact_number(updatedDetails.getUser().getMobileNumber());

        // for calling RabbitMq Message
        producer.sendMessageToRabbitMqForEmailUpdate(emailDto);
        producer.sendMessageToRabbitMqForSmsUpdate(smsDto);
    }


    /**
     * @param address
     * @return
     */
    @Override
    public List<Hotel> getHotelsByLocation(String address) throws NoHotelFoundWithThisAddress {

        if (address != null) {
            List<Hotel> hotelData = hotelRepository.findByAddressId(new ObjectId(address));
            if (hotelData.isEmpty()) {
                throw new NoHotelFoundWithThisAddress();
            }
            return hotelData;
        }
        return null;
    }

    /**
     * @param hotelName
     * @return
     */
    @Override
    public List<Hotel> getHotelsByName(String hotelName) throws NoHotelFoundWithThisName {
        if (hotelName != null) {
            List<Hotel> hotelData = hotelRepository.findByName(hotelName);
            if (hotelData.isEmpty()) {
                throw new NoHotelFoundWithThisName();
            }
            return hotelData;
        }
        return null;
    }

    /**
     * @param hotelId
     * @return
     */
    @Override
    public Optional<Hotel> getHotelById(Integer hotelId) throws NoHotelFoundWithThisId {
        if(hotelRepository.findById(hotelId).isEmpty()){
            throw new NoHotelFoundWithThisId();
        }
        return hotelRepository.findById(hotelId);
    }

    @Override
    public HashMap<String,?> getHotelAndFeedbackByHotelId(Integer hotelId) throws NoHotelFoundWithThisId {

        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
        HashMap<String, Object> getHotelDataWithFeedback = new HashMap<>();

        if (hotel == null) {
            throw new NoHotelFoundWithThisId();
        } else {
            // fetching user details using rest template
            HashMap<String,?> feedbackWithAverageRatingDto = restTemplate.getForObject(feedbackUrl + hotel.getId(), HashMap.class);
            getHotelDataWithFeedback.put("Hotel", hotel);
            getHotelDataWithFeedback.put("FeeddbacksWithRatings", feedbackWithAverageRatingDto);

        }
        return getHotelDataWithFeedback;
    }

    @Override
    public boolean saveImagesPath(Integer hotelId, Images paths) {
        // check hotel data is present or not
        Optional<Hotel> hotelData = hotelRepository.findById(hotelId);

        // update hotel user idea
        if (!hotelData.isEmpty()) {
            hotelData.get().setImages(paths);
        } else {
            return false;
        }
        hotelRepository.save(hotelData.get());
        return true;
    }

    @Override
    public boolean deleteHotelById(Integer hotelId) throws NoHotelFoundWithThisId {

        boolean status = false;
        // check hotel data is present or not
        Optional<Hotel> hotelData = hotelRepository.findById(hotelId);

        // update hotel user idea
        if (!hotelData.isEmpty()) {
            hotelRepository.deleteById(hotelId);
            sendNotificationOnDeleteForEmailAndSmsService(hotelData.get());
            status = true;
        } else {
            status = false;
            throw new NoHotelFoundWithThisId();
        }

        return status;
    }

    private void sendNotificationOnDeleteForEmailAndSmsService(Hotel updatedDetails) {
        EmailDto emailDto = new EmailDto();
        emailDto.setHotelName(updatedDetails.getName());
        emailDto.setEmail(updatedDetails.getUser().getEmailId());
        emailDto.setFirstName(updatedDetails.getUser().getFirstName());

        SmsDto smsDto = new SmsDto();
        smsDto.setHotelName(updatedDetails.getName());
        smsDto.setFirstName(updatedDetails.getUser().getFirstName());
        smsDto.setContact_number(updatedDetails.getUser().getMobileNumber());

        // for calling RabbitMq Message
        producer.sendMessageToRabbitMqForEmailDelete(emailDto);
        producer.sendMessageToRabbitMqForSmsDelete(smsDto);
    }

}
