package com.example.easyrent.service;

import com.example.easyrent.dto.request.PropertyAddRequestDto;
import com.example.easyrent.mapper.PropertyMapper;
import com.example.easyrent.model.*;
import com.example.easyrent.repository.CityRepository;
import com.example.easyrent.repository.FeatureRepository;
import com.example.easyrent.repository.PropertyRepository;
import com.example.easyrent.dto.response.*;
import com.example.easyrent.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService
{
    @Value("${google.const.header}")
    private String imageHeader;
    private final PropertyRepository propertyRepository;
    private final UploadObjectFromMemory uploadObjectFromMemory;
    private final StatusRepository statusRepository;
    private final CityRepository cityRepository;
    private final FeatureRepository featureRepository;
    private final UserService userService;
    public Page<PropertyResponseDto> getAllMarketProperties()
    {
        List<Property> properties = propertyRepository.findByPropertyStatusId(2);
        List<PropertyResponseDto> dto = properties.stream()
                .map(PropertyMapper::marketMapPropertyToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dto);
    }

    public Page<PropertyResponseDto> getOwnerProperties(String jwtToken)
    {
        User currentUser = userService.getUserFromToken(jwtToken);
        List<Property> properties = propertyRepository.findByOwnerId(currentUser.getId());
        List<PropertyResponseDto> dto = properties.stream()
                .map(PropertyMapper::marketMapPropertyToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dto);
    }

    public PropertyResponseDto getOwnerProperty(String jwtToken, Integer propertyId)
    {
        User currentUser = userService.getUserFromToken(jwtToken);
        Property property = propertyRepository.findById(propertyId).orElse(null);
        if(property != null && currentUser.getProperties().contains(property))
            return PropertyMapper.marketMapPropertyToDto(property);
        return null;
    }

    public boolean deletePropertyById(String jwtToken, Integer id)
    {
        try
        {
            User currentUser = userService.getUserFromToken(jwtToken);
            Property property = propertyRepository.findById(id).orElse(null);
            if(property != null && currentUser.getProperties().contains(property))
            {
                propertyRepository.deleteById(id);
                return true;
            }
            return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public void addProperty(String token, PropertyAddRequestDto request)
    {
        User owner = userService.getUserFromToken(token);
        PropertyStatus status = statusRepository.findPropertyStatusByName("NOT RENTED");
        City city = cityRepository.findCityByCityName(request.getCity());
        Property property = new Property();

        //Add simple values
        property.setArea(request.getArea());
        property.setAddress(request.getAddress());
        property.setDeposit(request.getDeposit());
        property.setDescription(request.getDescription());
        property.setLivingRooms(request.getLivingRooms());
        property.setPets(request.getPets());
        property.setStreetName(request.getStreetName());
        property.setRentAmount(request.getRentAmount());
        property.setUtilityCost(request.getUtilityCost());

        //Add repository values
        property.setCity(city);
        property.setOwner(owner);
        property.setPropertyStatus(status);

        //Add set values
        addPropertyFeatures(property, request.getFeatures());
        addPropertyPhotos(owner, property, request.getImages());
        propertyRepository.save(property);
    }

    private void addPropertyFeatures(Property property, Set<String> features)
    {
        Set<Feature> newFeatures = featureRepository.getFeaturesByNameIn(features);
        property.setFeatures(newFeatures);
    }

    private void addPropertyPhotos(User user, Property property, List<MultipartFile> imageFiles)
    {
        Set<PropertyPhoto> propertyPhotos = new HashSet<>();
        int counter = 0;
        for (MultipartFile imageFile : imageFiles)
        {
            PropertyPhoto photo = new PropertyPhoto();
            photo.setIsMain(counter == 0);
            String fileName = user.getUsername() + "_" + System.currentTimeMillis() + ".jpg";
            try
            {
                byte[] image = imageFile.getBytes();
                uploadObjectFromMemory.uploadImageToGCS(fileName, image);
                photo.setPhoto(imageHeader + fileName);
                photo.setProperty(property);
                propertyPhotos.add(photo);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new RuntimeException("Failed to upload image: " + e.getMessage());
            }
            counter ++;
        }
        property.setPropertyPhotos(propertyPhotos);
    }

}
