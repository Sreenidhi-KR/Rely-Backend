package com.example.backend.Service;

import com.example.backend.Bean.DocumentDetails;
import com.example.backend.Bean.Documents;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentsService {
    Documents saveDocument(MultipartFile file,Integer Id) throws Exception;
    Documents getDocument (Integer Id) throws Exception;

    void delDocument (Integer id);

    List<DocumentDetails> getAll (Integer id);

    List<DocumentDetails> getAllPrescriptions (Integer id);
}
