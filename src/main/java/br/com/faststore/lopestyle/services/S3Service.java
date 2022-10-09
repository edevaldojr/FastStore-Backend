package br.com.faststore.lopestyle.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import br.com.faststore.lopestyle.services.Exceptions.FileException;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class S3Service {

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream is = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(is, fileName, contentType);
        } catch (IOException exception) {
            throw new FileException("Erro de I0: " + exception.getMessage());
        }
    }

    public URI uploadFile(InputStream is, String fileName, String contentType) {
        ObjectMetadata meta = new ObjectMetadata();
        log.info("Iniciando upload");
        s3client.putObject(bucketName, fileName, is, meta);
        log.info("Upload finalizado");
        try {
            return s3client.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException exception) {
            throw new FileException("Erro ao converter URL para URI");
        }
    }

    public void deleteFile(String fileName) {
        try {
            log.info("Iniciando deleção");
            s3client.deleteObject(bucketName, fileName);
            log.info("Deleção finalizada");
        } catch (Exception exception) {
            throw new FileException("Erro ao deletar objeto do bucket");
        }
    }

}