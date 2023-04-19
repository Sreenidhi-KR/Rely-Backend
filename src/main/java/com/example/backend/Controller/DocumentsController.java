package com.example.backend.Controller;

import com.example.backend.Bean.DocumentDetails;
import com.example.backend.ResponseData;
import com.example.backend.Bean.Documents;
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
    public String downloadFile(@PathVariable int Id) throws Exception{
        Documents document = null;
        document = documentsService.getDocument(Id);
        return Base64.getEncoder().encodeToString(document.getData());
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

    @GetMapping("/getAllPrescriptions/{Id}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public List<DocumentDetails> getAllPrescriptions(@PathVariable int Id){
        return documentsService.getAllPrescriptions(Id);//get all documents given user ID
    }

    @PostMapping("/uploadPrescription/{PId}/{Cid}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file,@PathVariable int PId, @PathVariable int Cid) throws Exception {
        Documents document = null;
        String downloadURl = "";
        System.out.println("alssddbadww"+PId);
        System.out.println(Cid);
        document = documentsService.saveDocument(file,PId);
        downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/document/download/")
                .path(String.valueOf(document.getId()))
                .toUriString();
        consultationService.addPrescription(Cid,document.getId());
        return new ResponseData(document.getName(),
                downloadURl,
                file.getContentType(),
                file.getSize());
    }
}
