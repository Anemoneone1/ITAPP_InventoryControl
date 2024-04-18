package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.front.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class InventoryController {
    private final RestTemplate restTemplate;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest httpSession){
        String token = (String) httpSession.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);


        ResponseEntity<DashboardDTO> responseEntity = restTemplate.exchange("http://localhost:8081/v1/dashboard", HttpMethod.GET, requestEntity, DashboardDTO.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/auth/login";
        }

        model.addAttribute("dashboardDTO", responseEntity.getBody());
        model.addAttribute("createEmployeeDTO", new CreateEmployeeDTO());
        model.addAttribute("createWarehouseDTO", new CreateWarehouseDTO());
        return "dashboard";
    }



    @PostMapping("/create_warehouse")
    public String createWarehouse(@Validated @ModelAttribute("createWarehouse") CreateWarehouseDTO createWarehouseDTO, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<CreateWarehouseDTO> requestEntity = new HttpEntity<>(createWarehouseDTO, headers);

        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange("http://localhost:8081/v1/warehouse", HttpMethod.POST, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return "redirect:/dashboard";
        }
        System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
        return "redirect:/dashboard";
    }

    @PostMapping("/delete_warehouse/{id}")
    public String deleteWarehouse(@PathVariable("id") Long warehouseId, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<DeleteWarehouseDTO> requestEntity = new HttpEntity<>(new DeleteWarehouseDTO(warehouseId), headers);

        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange("http://localhost:8081/v1/warehouse", HttpMethod.DELETE, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/dashboard";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/item_list")
    public String getItemList(Model model, HttpSession httpSession){
        String token = (String) httpSession.getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<StorageItemDTO>> responseEntity = restTemplate.exchange("http://localhost:8081/v1/storage-items", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<StorageItemDTO>>() {});

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/auth/login";
        }

        model.addAttribute("storageItemListDTO", responseEntity.getBody());
        return "item_list";
    }

    @PostMapping("/delete_item/{id}")
    public String deleteItem(@PathVariable("id") Long id, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<DeleteStorageItemDTO> requestEntity = new HttpEntity<>(new DeleteStorageItemDTO(id), headers);

        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange("http://localhost:8081/v1/warehouse", HttpMethod.DELETE, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/item_list";
        }
        return "redirect:/item_list";
    }

    @GetMapping("/create_item")
    public String createStorageItem(Model model, HttpSession httpSession){
        String token = (String) httpSession.getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<ItemDTO>> responseEntity = restTemplate.exchange("http://localhost:8081/v1/item", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ItemDTO>>() {});

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/item_list";
        }

        model.addAttribute("items", responseEntity.getBody());

        ResponseEntity<List<StorageSpaceDTO>> responseEntity2 = restTemplate.exchange("http://localhost:8081/v1/storage", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<StorageSpaceDTO>>() {});

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            System.out.println("Request failed with status code: " + responseEntity2.getStatusCode());
            return "redirect:/item_list";
        }

        model.addAttribute("storageSpaces", responseEntity2.getBody());


        model.addAttribute("createStorageItemDTO", new CreateStorageItemDTO());
        return "create_item";
    }

    @PostMapping("/create_item")
    public String createStorageItem(@Validated @ModelAttribute("createStorageItemDTO") CreateStorageItemDTO createStorageItemDTO, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<CreateStorageItemDTO> requestEntity = new HttpEntity<>(createStorageItemDTO, headers);

        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange("http://localhost:8081/v1/storage-items", HttpMethod.POST, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return "redirect:/item_list";
        }
        System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
        return "redirect:/item_list";
    }

}
