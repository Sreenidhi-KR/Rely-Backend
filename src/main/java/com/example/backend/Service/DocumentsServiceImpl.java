package com.example.backend.Service;

import com.example.backend.Bean.Documents;
import com.example.backend.Repository.DocumentsRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class DocumentsServiceImpl  implements DocumentsService{
    private final DocumentsRepository documentsRepository;
    public DocumentsServiceImpl(DocumentsRepository documentsRepository){
        this.documentsRepository=documentsRepository;
    }
    @Override
    public Documents saveDocument(MultipartFile file, int Id) throws Exception {
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                        + fileName);
            }

            Documents document
                    = new Documents();
            return documentsRepository.save(document);

        } catch (Exception e) {
            throw new Exception("Could not save the File ");
        }
    }

    @Override
    public Documents getDocument(int Id) throws Exception {
        return null;
    }
}
