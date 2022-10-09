package br.com.faststore.lopestyle.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.faststore.lopestyle.controllers.dto.ProductDTO;
import br.com.faststore.lopestyle.services.ImageService;

@RestController
@RequestMapping("/images")
public class ImageController {
    
    @Autowired
    private ImageService imageService;

    @PostMapping("/product/{productId}/image/upload")   
    public ResponseEntity<Void> uploadProductImage(@PathVariable("productId") int productId, @RequestParam(name = "file") MultipartFile file) {
        URI uri = imageService.uploadProductPicture(file, productId);
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/product/{productId}/image")
    public ResponseEntity<Void> deleteProductImage(@PathVariable("productId") int productId, @RequestBody ProductDTO productDTO) {
        imageService.deleteProductImage(productId, productDTO.getImageId());
        return ResponseEntity.noContent().build();
    }
}
