package com.works.services;

import com.works.entities.Product;
import com.works.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ProductService {

    final ProductRepository productRepository;

    //Ürün listesi
    public List<Product> allProduct(){
      return productRepository.findAll();

    }
    //Yeni ürün ekleme
    public Product save(Product product){
        return productRepository.save(product);
    }

    //Ürün silme metodu
    public boolean productDelete(String stpid) {
        try {
            long pid=Long.parseLong(stpid);
            boolean status=productRepository.existsById(pid);
            if(status){
                productRepository.deleteById(pid);
                return true;
            }else {
                return false;
            }
        }catch (Exception exception){
            System.err.println("Delete Error"+exception);
            return false;
        }
    }




}
