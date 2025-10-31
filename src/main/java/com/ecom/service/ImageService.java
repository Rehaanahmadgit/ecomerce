package com.ecom.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageService {

    private final Cloudinary cloudinary;

    public ImageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // returns uploaded image URL
    public String upload(MultipartFile file, String folder) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        Map options = ObjectUtils.asMap(
                "folder", folder != null ? folder : "my_app", // optional folder in Cloudinary
                "overwrite", true,
                "resource_type", "image"
        );

        Map result = cloudinary.uploader().upload(file.getBytes(), options);
        return result.get("secure_url").toString(); // secure_url is https URL
    }
}