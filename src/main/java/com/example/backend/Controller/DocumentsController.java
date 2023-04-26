package com.example.backend.Controller;

import com.example.backend.Bean.DocumentDetails;
import com.example.backend.Bean.Documents;
import com.example.backend.ResponseData;
import com.example.backend.Service.ConsultationService;
import com.example.backend.Service.DocumentsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/document")
public class DocumentsController {
    private final DocumentsService documentsService;

    public DocumentsController(DocumentsService documentsService){
        this.documentsService=documentsService;
    }

    @Resource
    private ConsultationService consultationService;

    @PostMapping("/upload/{Id}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file,@PathVariable Integer Id) throws Exception {
        Documents document = null;
        String downloadURl = "";
        try {
            document = documentsService.saveDocument(file, Id);
            downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/document/download/")
                    .path(String.valueOf(document.getId()))
                    .toUriString();
            return new ResponseData(document.getName(),
                    downloadURl,
                    file.getContentType(),
                    file.getSize());
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    @GetMapping("/download/{Id}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public String downloadFile(@PathVariable Integer Id) throws Exception{
        Documents document = null;
       try {
           document = documentsService.getDocument(Id);
           return Base64.getEncoder().encodeToString(document.getData());
       }
       catch(Exception e)
       {
           System.out.println(e);
           return null;
       }
    }


    @DeleteMapping("/delete/{Id}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public void deleteDocuments(@PathVariable Integer Id) throws Exception{
        try{
            documentsService.delDocument(Id);
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }

    @GetMapping("/getAll/{Id}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public List<DocumentDetails> getAll(@PathVariable Integer Id) throws Exception{
        try{
            return documentsService.getAll(Id);//get all documents given user ID
        }catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/getAllPrescriptions/{Id}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public List<DocumentDetails> getAllPrescriptions(@PathVariable Integer Id) throws Exception{
        try{
            return documentsService.getAllPrescriptions(Id);//get all documents given user ID
        }catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }

    @PostMapping("/uploadPrescription/{PId}/{Cid}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file,@PathVariable Integer PId, @PathVariable Integer Cid) throws Exception {
        Documents document = null;
        String downloadURl = "";
        System.out.println("alssddbadww"+PId);
        System.out.println(Cid);
       try {
           document = documentsService.saveDocument(file, PId);
           downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                   .path("/api/v1/document/download/")
                   .path(String.valueOf(document.getId()))
                   .toUriString();
           consultationService.addPrescription(Cid, document.getId());
           return new ResponseData(document.getName(),
                   downloadURl,
                   file.getContentType(),
                   file.getSize());
       }
       catch(Exception e)
       {
           System.out.println(e);
           return null;
       }
    }
}
