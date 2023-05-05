package com.example.backend.Service;

import com.example.backend.Bean.DocumentDetails;
import com.example.backend.Bean.Documents;
import com.example.backend.Bean.Patient;
import com.example.backend.Repository.DocumentsRepository;
import com.example.backend.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class DocumentsServiceImpl  implements DocumentsService{
    @Autowired
    private final DocumentsRepository documentsRepository;
    @Autowired
    private PatientRepository patientRepository;
    public DocumentsServiceImpl(DocumentsRepository documentsRepository){
        this.documentsRepository=documentsRepository;
    }
    @Override
    public Documents saveDocument(MultipartFile file, Integer Id) throws Exception {
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                        + fileName);
            }
            String fileType=file.getContentType();
            Date curDate=new Date();

            Patient curPatient=patientRepository.findPatientById(Id);
            Documents document
                    = new Documents(curDate,fileType,file.getBytes(),curPatient,fileName);
            return documentsRepository.save(document);

        } catch (Exception e) {
            throw new Exception("Could not save the File ");
        }
    }

    @Override
    public void delDocument (Integer id) {
        documentsRepository.removeDocumentById(id);//will only set data filed in as NULL
    }

    @Override
    public List<DocumentDetails> getAll (Integer id) {
        List<Documents> patientDocuments= documentsRepository.getAll(id);
        List<DocumentDetails>patientDocumentsDetails=new ArrayList<DocumentDetails>();
        for (Documents patientDocument : patientDocuments) {
            Integer curId = patientDocument.getId();
            String curName = patientDocument.getName();
            String document_type=patientDocument.getDocument_type();
            boolean isAvailible=true;
            if (patientDocument.getData()==null) {
                isAvailible=false;
            }
            DocumentDetails del = new DocumentDetails(curId, curName,isAvailible,document_type);
            patientDocumentsDetails.add(del);
            System.out.println(curId+curName+del);
        }
        return patientDocumentsDetails;
    }

    public List<DocumentDetails> getAllPrescriptions (Integer id){
        List<Documents> patientDocuments= documentsRepository.getAllPrescriptions(id);
        List<DocumentDetails>patientDocumentsDetails=new ArrayList<DocumentDetails>();
        for (Documents patientDocument : patientDocuments) {
            Integer curId = patientDocument.getId();
            String curName = patientDocument.getName();
            String document_type=patientDocument.getDocument_type();
            boolean isAvailible=true;
            if (patientDocument.getData()==null) {
                isAvailible=false;
            }
            DocumentDetails del = new DocumentDetails(curId, curName,isAvailible,document_type);
            patientDocumentsDetails.add(del);
            System.out.println(curId+curName+del);
        }
        return patientDocumentsDetails;
    }

    @Override
    public Documents getDocument (Integer Id) throws Exception {
        return documentsRepository
                .findById(Id)
                .orElseThrow(()->new Exception("File not found with Id:"+Id));
    }
}
