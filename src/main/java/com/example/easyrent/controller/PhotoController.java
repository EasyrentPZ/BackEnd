package com.example.easyrent.controller;

import com.example.easyrent.dto.response.MessageDto;
import com.example.easyrent.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/photos")
public class PhotoController
{
    private final PhotoService photoService;
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deletePhotoById(@CookieValue("jwtCookie") String jwtToken, @PathVariable("id") Integer photoId)
    {
        try
        {
            boolean deleted = photoService.removePhoto(jwtToken, photoId);
            if (deleted)
                return ResponseEntity.ok().body(new MessageDto("Photo deleted successfully."));
            else
                return ResponseEntity.notFound().build();
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Unknown error!");
        }

    }
}
