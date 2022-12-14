package com.example.MarketAPI.service.impl;

import com.example.MarketAPI.entity.Product;
import com.example.MarketAPI.model.Result;
import com.example.MarketAPI.payload.ProductPayload;
import com.example.MarketAPI.repository.AttachmentRepository;
import com.example.MarketAPI.repository.ProductRepository;
import com.example.MarketAPI.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements com.example.MarketAPI.service.ProductService {

    private final ProductRepository productRepository;
    private final AttachmentService attachmentService;
    private final Result result;
    private final AttachmentRepository attachmentRepository;
    @Override
    public Result add(ProductPayload productPayload) {
        try {
            Product product=Product.builder()
                    .price(productPayload.getPrice())
                    .description(productPayload.getDescription())
                    .name(productPayload.getName())
                    .attachment(attachmentRepository.findAttachmentByHashId(productPayload.getHashId()))
                    .build();
            productRepository.save(product);
            return result.success(product);
        }catch (Exception e) {
            log.error(e.getMessage());
            return result.failed(e.getMessage());
        }
    }

    @Override
    public Result delete(Long id) {
        try {
            Product product = productRepository.findById(id).orElseThrow();
            String hashId=product.getAttachment().getHashId();
            productRepository.delete(product);
            attachmentService.deleteAttachmentById(hashId);
            return result.success("file deleted");
        }catch (Exception e){
            log.error(e.getMessage());
            return result.failed(e.getMessage());
        }
    }

    @Override
    public Result edit(ProductPayload productPayload,Long id) {
        try {

            Product product=Product.builder()
                    .id(id)
                    .name(productPayload.getName())
                    .description(productPayload.getDescription())
                    .price(productPayload.getPrice())
                    .attachment(attachmentRepository.findAttachmentByHashId(productPayload.getHashId()))
                    .build();

            productRepository.save(product);

            return result.success(product);
        }catch (Exception e){
            log.error(e.getMessage());
            return result.failed(e.getMessage());
        }
    }

    @Override
    public Result findById(Long id) {

        return new Result("succsess",true,productRepository.findById(id).orElseThrow(),null);
    }

    @Override
    public Result findAll() {
        return new Result("succsess",true,productRepository.findAll(),null);
    }
}
