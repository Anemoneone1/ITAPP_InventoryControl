package com.itapp.inventorycontrol.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itapp.inventorycontrol.dto.front.BoxDTO;
import com.itapp.inventorycontrol.dto.front.DeleteBoxDTO;
import com.itapp.inventorycontrol.dto.front.DeleteRequestDTO;
import com.itapp.inventorycontrol.dto.front.DeleteStorageItemDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class QrController {

    private final RestTemplate restTemplate;
    private final String api;

    public QrController(RestTemplate restTemplate, @Value("${internal_api}") String api) {
        this.restTemplate = restTemplate;
        this.api = api;
    }

    @GetMapping("/qrscanner")
    public String qrScanner(HttpServletRequest httpServletRequest) {
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        if (token == null) {
            return "login";
        }
        return "qrscanner";
    }

    @PostMapping("/process-qr")
    @ResponseBody
    public Map<String, Long> processQR(@RequestBody Map<String, String> payload, HttpServletRequest httpServletRequest) {
        String qrData = payload.get("data");

        String token = (String) httpServletRequest.getSession().getAttribute("token");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(qrData, headers);

        Map<String, Long> response = new HashMap<>();
        ResponseEntity<BoxDTO> responseEntity = restTemplate.exchange(api + "/box/" + qrData, HttpMethod.GET, requestEntity, BoxDTO.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            response.put("action", responseEntity.getBody().getId());
        }
        else {
            response.put("action", (long) -1);
        }

        return response;
    }

    @PostMapping("/process-qr-delete")
    public String deleteByQR(@RequestBody DeleteRequestDTO deleteRequest, HttpServletRequest httpServletRequest) {
        Long id = deleteRequest.getData();
        String token = (String) httpServletRequest.getSession().getAttribute("token");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<DeleteBoxDTO> requestEntity = new HttpEntity<>(new DeleteBoxDTO(id), headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(api + "/box", HttpMethod.DELETE, requestEntity, Void.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return "redirect:/qrscanner";
        } else {
            throw new RuntimeException("Failed to delete box with id: " + id);
        }
    }
    @GetMapping("/generate-qr/{data}")
    public void generateQRCode(@PathVariable String data, HttpServletResponse response) throws IOException, WriterException {
        response.setHeader("Content-Disposition", "attachment; filename=\"qrcode.png\"");
        response.setContentType("image/png");

        generateQRCodeImage(data, response.getOutputStream());
    }

    private void generateQRCodeImage(String text, OutputStream outputStream) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        byte[] pngData = pngOutputStream.toByteArray();
        outputStream.write(pngData);
    }

}
