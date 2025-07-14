package com.epam.cora.esa.rest.index;

import com.epam.cora.entity.datastorage.DataStorageFile;
import com.epam.cora.esa.rest.AbstractRestController;
import com.epam.cora.esa.service.index.StorageDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@ConditionalOnProperty(
        value = "git.hook.endpoint.disable",
        matchIfMissing = true,
        havingValue = "false"
)
@Slf4j
@RestController
public class StorageDocumentController extends AbstractRestController {

    private final StorageDocumentService storageDocumentService;

    @PostMapping(value = "/index/document")
    public ResponseEntity processDocs(@RequestBody final List<DataStorageFile> files, @RequestParam final Long storageId) {
        storageDocumentService.indexFile(storageId, files);
        return new ResponseEntity(HttpStatus.OK);
    }
}
