package com.senthan.shopeasy.Service.image;

import com.senthan.shopeasy.Repository.ImageRepository;
import com.senthan.shopeasy.Service.product.IProductService;
import com.senthan.shopeasy.dto.ImageDto;
import com.senthan.shopeasy.exception.ResourceNotFoundException;
import com.senthan.shopeasy.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IProductService productService;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image is Not found!"));
    }

    @Override
    public void deleteImageById(Long id) {
            imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                    () -> {
                    throw new ResourceNotFoundException("Image is not found!");
                    });
    }



    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        List<ImageDto> savedImages = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(productService.getProductById(productId));

                String buildUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildUrl + image.getId();
                Image savedImage =  imageRepository.save(image);

                savedImage.setDownloadUrl(buildUrl + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImages.add(imageDto);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return savedImages;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
