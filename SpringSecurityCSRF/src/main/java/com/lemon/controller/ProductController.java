package com.lemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lemon.model.Product;
import com.lemon.repository.ProductRepository;


@Controller
public class ProductController {	
	@Autowired
ProductRepository pr;
	@GetMapping("/product")
	private String getProduct() {
		return"AddProduct";
	}
	@PostMapping("/product")
	private String postProduct(@ModelAttribute Product product) {
		pr.save(product);
		return"AddProduct";
	}
	@GetMapping("/productList")
	private String getProductList(Model model) {
		model.addAttribute("pList" , pr.findAll());
		return "ProductList";
	}
	@GetMapping("/editProduct")
	private String editProduct(@RequestParam int id, Model model) {
		model.addAttribute("productModel", pr.findById(id));
		return "EditProduct";
	}
	@PostMapping("/deleteProduct")
	private String deleteProduct(Model model,@RequestParam int id) {
	    System.out.println("Delete request for product ID: " + id);

		System.out.println(id);
		pr.deleteById(id);
		return "redirect:/productList"; 
	}
	@PostMapping("/updateProduct")
	private String updateProduct(@ModelAttribute Product product) {
		pr.save(product);
		return"redirect:/productList";
	}
}
