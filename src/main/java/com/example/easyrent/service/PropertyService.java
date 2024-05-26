package com.example.easyrent.service;

import com.example.easyrent.dto.request.AddTenantDto;
import com.example.easyrent.dto.request.PropertyAddRequestDto;
import com.example.easyrent.mapper.PropertyMapper;
import com.example.easyrent.model.*;
import com.example.easyrent.repository.*;
import com.example.easyrent.dto.response.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService
{
    @Value("${google.const.header}")
    private String imageHeader;
    private final PropertyRepository propertyRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UploadObjectFromMemory uploadObjectFromMemory;
    private final StatusRepository statusRepository;
    private final CityRepository cityRepository;
    private final FeatureRepository featureRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserContractRepository userContractRepository;
    private final PropertyStatusRepository propertyStatusRepository;

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

    public PropertyResponseDto getProperty( Integer propertyId)
    {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));
        return PropertyMapper.marketMapPropertyToDto(property);
    }

    public PropertyResponseDto getOwnerProperty(String jwtToken, Integer propertyId)
    {
        User currentUser = userService.getUserFromToken(jwtToken);
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));
        if (!currentUser.getProperties().contains(property))
        {
            throw new RuntimeException("You do not have permission to access this property.");
        }

        return PropertyMapper.ownerMapper(property);
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
        property.setContract(createContract(property));

        //Add repository values
        property.setCity(city);
        property.setOwner(owner);
        property.setPropertyStatus(status);

        //Add set values
        addPropertyFeatures(property, request.getFeatures());
        if(!request.getImages().isEmpty())
            addPropertyPhotos(owner, property, request.getImages());
        propertyRepository.save(property);
    }

    public boolean updatePropertyStatus(String jwtToken, Integer propertyId, Integer statusId) {
        User currentUser = userService.getUserFromToken(jwtToken);
        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);

        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            if (currentUser.getProperties().contains(property)) {
                Optional<PropertyStatus> statusOptional = statusRepository.findById(statusId);
                if (statusOptional.isPresent()) {
                    property.setPropertyStatus(statusOptional.get());
                    propertyRepository.save(property);
                    return true;
                }
            }
        }
        return false;
    }

    public Set<Feature> getPropertyFeatures(String jwtToken, Integer propertyId) {
        User currentUser = userService.getUserFromToken(jwtToken);
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        if (!currentUser.getProperties().contains(property)) {
            throw new RuntimeException("You do not have permission to access this property.");
        }

        return property.getFeatures();
    }

    public PropertyStatusesDto getStatuses()
    {
        PropertyStatusesDto response = new PropertyStatusesDto();
        List<PropertyStatus> statuses = propertyStatusRepository.findAll();
        List<PropertyStatusDto> res = new LinkedList<>();
        for(PropertyStatus status: statuses)
            res.add(new PropertyStatusDto(status.getId(), status.getName()));
        response.setStatuses(res);
        return response;
    }


    @Transactional
    public void addTenant(String token, AddTenantDto request)
    {
        User owner = userService.getUserFromToken(token);
        User tenant = new User();
        Property property = propertyRepository.findPropertyById(request.getPropertyId());
        Role role = roleRepository.findByName("TENANT");

        tenant.addRole(role);
        tenant.addProperty(property);
        tenant.addContract(property.getContract());
        tenant.setName(request.getName());
        tenant.setLastname(request.getLastname());
        tenant.setUsername(request.getEmail());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        tenant.setPassword(encodedPassword);

        UserContract userContract = new UserContract();
        userContract.setUser(tenant);
        userContract.setContract(property.getContract());
        userRepository.save(tenant);
        userContractRepository.save(userContract);
    }

    @Transactional
    public PropertyResponseDto updateProperty(String token, Integer propertyId, PropertyAddRequestDto request) throws Exception
    {
        User owner = userService.getUserFromToken(token);
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        if (!property.getOwner().equals(owner))
            throw new RuntimeException("You do not have permission to update this property.");

        copyNonNullProperties(request, property);

        if (request.getCity() != null)
        {
            City city = cityRepository.findCityByCityName(request.getCity());
            property.setCity(city);
        }
        if (request.getFeatures() != null && !request.getFeatures().isEmpty())
            addPropertyFeatures(property, request.getFeatures());

        if (request.getImages() != null && !request.getImages().isEmpty())
            addPropertyPhotos(owner, property, request.getImages());

        propertyRepository.save(property);
        return PropertyMapper.marketMapPropertyToDto(property);
    }

    private void copyNonNullProperties(PropertyAddRequestDto src, Property target)
    {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private String[] getNullPropertyNames(Object source)
    {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    private void addPropertyFeatures(Property property, Set<String> features)
    {
        Set<Feature> newFeatures = featureRepository.getFeaturesByNameIn(features);
        property.setFeatures(newFeatures);
    }

    private void addPropertyPhotos(User user, Property property, List<MultipartFile> imageFiles)
    {
        Set<PropertyPhoto> propertyPhotos = new HashSet<>();
        int counter = property.getPropertyPhotos().size();
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

    private Contract createContract(Property property)
    {
        Contract contract = new Contract();
        contract.setProperty(property);
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.of(2040, 12,31));
        return contract;
    }

}
