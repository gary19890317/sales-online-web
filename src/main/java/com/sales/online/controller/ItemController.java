package com.sales.online.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.zip.Deflater;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sales.online.model.Item;
import com.sales.online.service.ItemService;

@Controller
public class ItemController {
	private ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@PostMapping("/upload")
	public String uplaodImage(@ModelAttribute(name = "imgData") Item itemData,
			@RequestParam("imageFile") MultipartFile file, Model model) throws IOException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("Original " + file.getBytes().length);
		try {
			java.util.Date date = formatter.parse(itemData.getExpirationDate_aux());
			Timestamp timestamp = new Timestamp(date.getTime());
			itemData.setExpirationDate(timestamp);
			Item item = new Item(itemData.getName(), compressBytes(file.getBytes()), itemData.getStartingPrice(),
					itemData.getExpirationDate(), itemData.getStatus(), itemData.getRanking());
			itemService.save(item);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "addItem";
	}

	@GetMapping("/upload")
	public String showViewUploadImage(@ModelAttribute(name = "imgData") Item itemData, Model model) {
		return "addItem";
	}

	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();
	}

}
