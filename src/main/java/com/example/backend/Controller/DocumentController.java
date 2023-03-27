//package com.example.backend.Controller;
//
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import javax.swing.text.Document;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/document")
//public class DocumentController {
//    private DocumentService documentService;
//
//    public DocumentController(DocumentService documentService){
//        this.documentService=documentService;
//    }
//
//    @PostMapping("/upload")
//    public ResponseData uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
//        Document document = null;
//        String downloadURl = "";
//        document = documentService.saveAttachment(file);
//        downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/download/")
//                .path(document.getId())
//                .toUriString();
//
//        return new ResponseData(document.getFileName(),
//                downloadURl,
//                file.getContentType(),
//                file.getSize());
//    }
//
//
//}
