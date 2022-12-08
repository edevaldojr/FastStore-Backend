package br.com.faststore.lopestyle.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Optional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.util.List;
import java.awt.*;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.faststore.lopestyle.models.Image;
import br.com.faststore.lopestyle.models.Product;
import br.com.faststore.lopestyle.repositories.ImageRepository;
import br.com.faststore.lopestyle.repositories.ProductRepository;
import br.com.faststore.lopestyle.security.UserSS;
import br.com.faststore.lopestyle.services.Exceptions.AuthorizationException;
import br.com.faststore.lopestyle.services.Exceptions.FileException;
import br.com.faststore.lopestyle.services.Exceptions.ObjectNotFoundException;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private S3Service s3Service;

    public URI uploadProductPicture(MultipartFile multipartFile, int productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));

        Image imageProduct = new Image();

        UserSS user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }
        List<Image> images = product.getImages();
        images.add(imageProduct);
        
        BufferedImage jpgImage = this.getJpgImageFromFile(multipartFile);

        String fileName = product.getSku() +"-" + product.getImages().size() + ".jpg";
        URI imageUri = s3Service.uploadFile(this.getInputStream(jpgImage,"jpg"), fileName, "image");

        imageProduct.setUrlImage(imageUri.toString());
       
        imageRepository.save(imageProduct);
        productRepository.save(product);
        return imageUri;
    }

    public void deleteProductImage(int productId, int imageId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + productId + ", Tipo: " + Product.class.getName()));

        Optional<Image> imageProduct = imageRepository.findById(imageId);

        if(imageProduct.isEmpty()){
            throw new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + imageId + ", Tipo: " + Image.class.getName());
        }

        UserSS user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }
        
        for(int index=0; index<product.getImages().size(); index++) {
            if(product.getImages().get(index).getId() == imageId){
                String fileName = product.getSku() + "-" + index + ".jpg";
                s3Service.deleteFile(fileName); 
                product.removeImageFromProduct(product.getImages().get(index));
                productRepository.save(product);
                imageRepository.deleteById(imageId);
                break;
            }
        }
    }

    public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
        String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
        if (!"png".equals(ext.toLowerCase()) && !"jpg".equals(ext.toLowerCase())) {
            throw new FileException("Somente imagens PNG e JPG são permitidas");
        }

        try {
            BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
            if ("png".equals(ext)) {
                img = pngToJpg(img);
            }
            return img;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }

    public BufferedImage pngToJpg(BufferedImage img) {
        BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
        return jpgImage;
    }

    public InputStream getInputStream(BufferedImage img, String extension) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, extension, os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }
}
