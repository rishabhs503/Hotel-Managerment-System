package com.stackroute.hotelpropertyservice.service;

import com.stackroute.hotelpropertyservice.model.Images;
import com.stackroute.hotelpropertyservice.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    private final ImageRepository hotelImageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.hotelImageRepository = imageRepository;
    }

    /**
     * @param hotelId
     * @param images
     * @return
     */
    @Override
    public Images saveImages(Integer hotelId, MultipartFile[] images) {

        Images hotelImages = new Images();
        List<String> urls = new ArrayList<>();

        // extract images => image & upload it
        Arrays.asList(images)
                .stream()
                .forEach(image -> urls.add(this.uploadFile(hotelId, image)));

        // set the list of urls of image
        hotelImages.setImagePaths(urls);
        hotelImages.setHotelId(hotelId);

        return hotelImageRepository.save(hotelImages);
    }

    @Override
    public String updateImages(Integer hotelId, MultipartFile[] images) {

        Images isImagesExists = hotelImageRepository.getImagesByHotelId(hotelId);

        if (!isImagesExists.getImagePaths().isEmpty()) {
            // extract images => image & upload it
            Arrays.asList(images)
                    .stream()
                    .forEach(image -> isImagesExists.getImagePaths().add(this.uploadFile(hotelId, image)));

            Images update = hotelImageRepository.save(isImagesExists);
            return update.getHotelImageId();
        } else {
            throw new RuntimeException("No images found for this hotel");
        }
    }


    /**
     * This uploadFiles upload an image on the given location
     * @param hotelId
     * @param file
     * @return
     */
    public String uploadFile(Integer hotelId, MultipartFile file)
    {
        try {
            Path copyLocation = Paths.get(UPLOAD_DIRECTORY+"/"+ hotelId +"/"+ File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.createDirectories(copyLocation);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

            return copyLocation.toString();
        } catch (IOException e) {
            LOGGER.warn("Error for file "+ file.getOriginalFilename() +" found: "+ e.getMessage());
            throw new RuntimeException("Inavlid files");
        }
    }

    /**
     * Get hotelImages object by hotel Id
     * @param hotelId
     * @return
     */
    public Images getImagesByHotelId(Integer hotelId) {
        Images getImages = hotelImageRepository.getImagesByHotelId(hotelId);

        if (getImages != null) {
            return getImages;
        } else {
            LOGGER.error("Inavlid hotel id:  "+ hotelId +" No data found");
            throw new RuntimeException("Inavlid hotel id:  "+ hotelId +" No data found");
        }
    }
}