package com.example.easyrent.service;

import com.example.easyrent.model.Property;
import com.example.easyrent.model.PropertyPhoto;
import com.example.easyrent.model.User;
import com.example.easyrent.repository.PropertyPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoService
{

    private final PropertyPhotoRepository propertyPhotoRepository;
    private final UserService userService;
    private final UploadObjectFromMemory uploadObjectFromMemory;

    public boolean removePhoto(String token, Integer photoId) throws Exception
    {
        PropertyPhoto photo = propertyPhotoRepository.getPropertyPhotoById(photoId);
        User owner = userService.getUserFromToken(token);
        for(Property property: owner.getProperties())
        {
            if(property.getPropertyPhotos().contains(photo))
            {
                String photoName = photo.getPhoto();
                property.removePropertyPhoto(photo);
                propertyPhotoRepository.delete(photo);
                uploadObjectFromMemory.removeImageFromGCS(photoName);
                return true;
            }
        }
        return false;
    }
}
