package com.example.backend.Controller;

import com.example.backend.DocumentDetails;
import com.example.backend.ResponseData;
import com.example.backend.Bean.Documents;
import com.example.backend.Service.DocumentsService;
import com.google.common.net.HttpHeaders;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;
import javax.swing.text.Document;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/document")
public class DocumentsController {
    private final DocumentsService documentsService;

    public DocumentsController(DocumentsService documentsService){
        this.documentsService=documentsService;
    }


    @PostMapping("/upload/{Id}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file,@PathVariable int Id) throws Exception {
        Documents document = null;
        String downloadURl = "";
        document = documentsService.saveDocument(file,Id);
        downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/document/download/")
                .path(String.valueOf(document.getId()))
                .toUriString();
        return new ResponseData(document.getName(),
                downloadURl,
                file.getContentType(),
                file.getSize());
    }
    @GetMapping("/download/{Id}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<Resource> downloadFile(@PathVariable int Id) throws Exception{
        Documents document = null;
        document = documentsService.getDocument(Id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getDocument_type()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + document.getName()
                        + "\"")
                .body(new ByteArrayResource(document.getData()));
    }


    @DeleteMapping("/delete/{Id}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public void deleteDocuments(@PathVariable int Id){
        documentsService.delDocument(Id);
    }

    @GetMapping("/getAll/{Id}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public List<DocumentDetails> getAll(@PathVariable int Id){
        return documentsService.getAll(Id);//get all documents given user ID
    }

}
