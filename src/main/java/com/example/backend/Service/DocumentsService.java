package com.example.backend.Service;

import com.example.backend.Bean.Documents;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentsService {
    Documents saveDocument(MultipartFile file,int Id) throws Exception;
    Documents getDocument(int Id) throws Exception;

}
