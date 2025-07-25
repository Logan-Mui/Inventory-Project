package com.inventory.lbm.controller;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.lbm.dto.RequestDTO;
import com.inventory.lbm.entity.RequestEntity;
import com.inventory.lbm.mapper.RequestMapper;
import com.inventory.lbm.service.RequestStoreService;
import com.inventory.lbm.service.impl.storage.RequestStoreServiceImpl;

import org.springframework.web.bind.annotation.RequestParam;

import com.inventory.lbm.model.OperationType;


@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private final RequestStoreService requestService;

    private final RequestMapper requestMapper;

    public RequestController(RequestStoreServiceImpl requestService, RequestMapper requestMapper) {
        this.requestService = requestService;
        this.requestMapper = requestMapper;
    }

    @GetMapping("/")
    public ResponseEntity<Page<RequestDTO>> getRequests(@PageableDefault(page = 0, size = 10) Pageable pageable) {

        logger.info("REQREPO - Recieved get all request.");

        Page<RequestEntity> entities = requestService.getRequests(pageable);
        Page<RequestDTO> dtoList = requestMapper.toDTOPage(entities);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RequestDTO> getRequestByID(@RequestParam String id) throws NoSuchElementException{

        logger.info("REQREPO - Recieved get by ID request.");

        UUID requestID = UUID.fromString(id);
        Optional<RequestEntity> optionalEntity = requestService.getRequestByID(requestID);
        return optionalEntity.map(requestMapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<Page<RequestDTO>> getRequestsByType(@PageableDefault(page = 0, size = 10) Pageable pageable, @RequestParam String type) {
        
        logger.info("REQREPO - Recieved get by Type request.");

        OperationType operationType = OperationType.valueOf(type);
        Page<RequestEntity> entities = requestService.getRequestsByType(pageable, operationType);
        Page<RequestDTO> dtoPage = requestMapper.toDTOPage(entities);
        if(dtoPage != null && !dtoPage.isEmpty()) {
            return ResponseEntity.ok(dtoPage);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
