package com.github.eltonsandre.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

@Slf4j
public abstract class ScreenshotUtils {

      public static void printScreen(final WebDriver driver, final String fileName){
            final File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            try {
                  FileUtils.copyFile(screenshot,  new File(fileName));
            } catch (final Exception e){
                  log.error("Erro ao capturar o screnshot: {}", e.getMessage());
            }
      }

}
