package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.creation.*;
import com.itapp.inventorycontrol.dto.front.*;
import com.itapp.inventorycontrol.dto.page.DashboardDTO;
import com.itapp.inventorycontrol.dto.page.ItemCreationPageDTO;
import com.itapp.inventorycontrol.dto.page.WarehouseStorageDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class InventoryController {
    private final RestTemplate restTemplate;
    private final String api;

    public InventoryController(RestTemplate restTemplate, @Value("${internal_api}") String api) {
        this.restTemplate = restTemplate;
        this.api = api;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest httpSession){
        String token = (String) httpSession.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);


        ResponseEntity<DashboardDTO> responseEntity = restTemplate.exchange(api + "/dashboard", HttpMethod.GET, requestEntity, DashboardDTO.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/auth/login";
        }

        model.addAttribute("dashboardDTO", responseEntity.getBody());
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

        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange(api + "/warehouse", HttpMethod.POST, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return "redirect:/dashboard";
        }
        System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
        return "redirect:/dashboard";
    }

    @DeleteMapping("/delete_warehouse/{id}")
    public String deleteWarehouse(@PathVariable("id") Long warehouseId, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<DeleteWarehouseDTO> requestEntity = new HttpEntity<>(new DeleteWarehouseDTO(warehouseId), headers);

        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange(api + "/warehouse", HttpMethod.DELETE, requestEntity, TokenDTO.class);

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

        ResponseEntity<List<ItemDTO>> responseEntity = restTemplate.exchange(api + "/item", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ItemDTO>>() {});

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/auth/login";
        }

        model.addAttribute("itemList", responseEntity.getBody());
        return "item_list";
    }

    @DeleteMapping("/item/{id}")
    public String deleteItem(@PathVariable("id") Long id, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<DeleteStorageItemDTO> requestEntity = new HttpEntity<>(new DeleteStorageItemDTO(id), headers);

        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange(api + "/item", HttpMethod.DELETE, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/item_list";
        }
        return "redirect:/item_list";
    }

    @PostMapping("/item")
    public String createItem(CreateItemDTO createItemDTO, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<CreateItemDTO> requestEntity = new HttpEntity<>(createItemDTO, headers);
        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange(api + "/item", HttpMethod.POST, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
        }
        return "redirect:/item_list";
    }

    @GetMapping("/item/create")
    public String itemCreationPage(Model model, HttpServletRequest httpSession){
        String token = (String) httpSession.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);


        ResponseEntity<ItemCreationPageDTO> responseEntity = restTemplate.exchange(api + "/item/compliances-and-conditions", HttpMethod.GET, requestEntity, ItemCreationPageDTO.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/item_list";
        }
        model.addAttribute("itemCreationPageDTO", responseEntity.getBody());
        model.addAttribute("createItemDTO", new CreateItemDTO());
        return "create_item";
    }

    @GetMapping("/product_list")
    public String getProductList(Model model, HttpSession httpSession){
        String token = (String) httpSession.getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<StorageItemDTO>> responseEntity = restTemplate.exchange(api + "/storage-items", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<StorageItemDTO>>() {});

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/auth/login";
        }

        model.addAttribute("storageItemList", responseEntity.getBody());
        return "product_list";
    }

    @GetMapping("/create_product")
    public String createStorageItem(Model model, HttpSession httpSession){
        String token = (String) httpSession.getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<ItemDTO>> responseEntity = restTemplate.exchange(api + "/item", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ItemDTO>>() {});

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/item_list";
        }

        model.addAttribute("items", responseEntity.getBody());

        ResponseEntity<List<StorageSpaceDTO>> responseEntity2 = restTemplate.exchange(api + "/storage", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<StorageSpaceDTO>>() {});

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            System.out.println("Request failed with status code: " + responseEntity2.getStatusCode());
            return "redirect:/item_list";
        }

        model.addAttribute("storageSpaces", responseEntity2.getBody());
        model.addAttribute("createStorageItemDTO", new CreateProductDTO());

        return "create_item";
    }

    @GetMapping("/warehouse/{id}")
    public String getWarehousePage(Model model, HttpServletRequest httpServletRequest, @PathVariable("id") Long id){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<Long> requestEntity = new HttpEntity<>(id, headers);

        ResponseEntity<List<WarehouseStorageDTO>> responseEntity = restTemplate.exchange(api + "/storage/warehouse/" + id, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<WarehouseStorageDTO>>() {});

        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/dashboard";
        }

        model.addAttribute("warehouseStoragePage", responseEntity.getBody());
        model.addAttribute("warehouseId", id);

        return "warehouse_storage";
    }


    @DeleteMapping("/box/{id}")
    public String deleteBox(@PathVariable("id") Long id, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<Long> requestEntity = new HttpEntity<>(id, headers);

        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange(api + "/box", HttpMethod.DELETE, requestEntity, TokenDTO.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/dashboard";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/box")
    public String getBoxCreationPage(Model model, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<Objects> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<StorageDTO>> responseEntity = restTemplate.exchange(api + "/storage", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<StorageDTO>>() {});
        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/dashboard";
        }

        model.addAttribute("storages", responseEntity.getBody());

        ResponseEntity<List<ItemDTO>> responseEntity2 = restTemplate.exchange(api + "/item", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ItemDTO>>() {});
        if (responseEntity2.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/dashboard";
        }

        model.addAttribute("items", responseEntity2.getBody());
        model.addAttribute("boxCreationDTO", new BoxCreationDTO());

        return "box_creation";
    }

    @PostMapping("/create_box")
    public String createBox(@Validated @ModelAttribute("boxCreationDTO") BoxCreationDTO boxCreationDTO, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<BoxCreationDTO> requestEntity = new HttpEntity<>(boxCreationDTO, headers);

        ResponseEntity<QrCodeDTO> responseEntity = restTemplate.exchange(api + "/box", HttpMethod.POST, requestEntity, QrCodeDTO.class);


        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return "redirect:/generate-qr/" + responseEntity.getBody().getUuid();
        }
        System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
        return "redirect:/dashboard";
    }

    @GetMapping("/storage_creation")
    public String getStorageCreationPage(Model model, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<Objects> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<WarehouseDTO>> responseEntity = restTemplate.exchange(api + "/warehouse", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<WarehouseDTO>>() {});
        if (responseEntity.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/dashboard";
        }

        model.addAttribute("warehouses", responseEntity.getBody());

        ResponseEntity<List<StorageConditionDTO>> responseEntity2 = restTemplate.exchange(api + "/storage-condition", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<StorageConditionDTO>>() {});
        if (responseEntity2.getStatusCode() != HttpStatus.OK){
            System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
            return "redirect:/dashboard";
        }

        model.addAttribute("storageConditions", responseEntity2.getBody());
        model.addAttribute("storageCreationDTO", new StorageCreationDTO());

        return "storage_creation";
    }

    @PostMapping("/storage_creation")
    public String createStorage(@Validated @ModelAttribute("storageCreationDTO") StorageCreationDTO storageCreationDTO, HttpServletRequest httpServletRequest){
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "redirect:/auth/login";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<StorageCreationDTO> requestEntity = new HttpEntity<>(storageCreationDTO, headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(api + "/storage", HttpMethod.POST, requestEntity, Object.class);


        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return "redirect:/dashboard";
        }
        System.out.println("Request failed with status code: " + responseEntity.getStatusCode());
        return "redirect:/dashboard";
    }
}
