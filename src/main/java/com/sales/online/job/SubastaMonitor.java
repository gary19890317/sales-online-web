package com.sales.online.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sales.online.service.ItemService;

@Component
public class SubastaMonitor {
  private static final Logger logger = LoggerFactory.getLogger(SubastaMonitor.class);

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  private ItemService itemService;

  public SubastaMonitor(ItemService itemService) {
    this.itemService = itemService;
  }

  @Scheduled(fixedRate = 5000)
  public void reportCurrentTime() {
    itemService.updateExpiredItemToInactive();
    logger.info("The time is now {}", dateFormat.format(new Date()));
  }
}
