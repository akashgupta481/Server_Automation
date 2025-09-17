package com.automation.pages;

import com.automation.utils.ConfigReader;
import com.automation.utils.ExcelUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class OrdersPage extends BasePage{

    @FindBy(xpath = "//h2//span[text()='Order']")
    WebElement orderText;

    By orderRowsLocator = By.xpath("//tr");

    @FindBy(xpath = "//a[@class='page-link']//span[text()='»']")
    WebElement nextBtn;


    public boolean isOrdersPageDisplayed() {
        try{
            return orderText.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public void createAndSaveOrdersData() {

        int rowNum = Integer.parseInt(ConfigReader.getConfigValue("start.row.num"));

        ExcelUtils.createWorkbookAndSheet("OrdersData");
        ExcelUtils.setHeader();

        int totalPages = Integer.parseInt(ConfigReader.getConfigValue("last.page"));
        int startPage = Integer.parseInt(ConfigReader.getConfigValue("start.page"));

        // Wait for order rows on the first page before scraping
        wait.until(ExpectedConditions.presenceOfElementLocated(orderRowsLocator));

        // Navigate to the starting page
        for (int i = 1; i < startPage; i++) {
            nextBtn.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(orderRowsLocator));
        }

        for (int i = startPage; i <= totalPages; i++) {
            List<WebElement> orderRows = driver.findElements(orderRowsLocator);
            System.out.println("Scraping page: " + i);
            System.out.println("Found " + orderRows.size() + " rows.");
            List<List<String>> pageData = new ArrayList<>();
            for (WebElement rowElement : orderRows) {
                List<WebElement> cells = rowElement.findElements(By.tagName("td"));
                System.out.println("Row cells count: " + cells.size());
                if (cells.size() > 1) { // Ensure it's a data row
                    List<String> rowData = new ArrayList<>();
                    // Start from index 1 to skip the checkbox
                    for (int j = 1; j < cells.size(); j++) {
                        String cellText = cells.get(j).getText();
                        System.out.println("Cell text: " + cellText);
                        rowData.add(cellText);
                    }
                    pageData.add(rowData);
                }
            }
            rowNum = ExcelUtils.writeDataIntoSheet(rowNum, pageData);
            if (i < totalPages) {
                nextBtn.click();
                wait.until(ExpectedConditions.presenceOfElementLocated(orderRowsLocator));
            }
        }
        ExcelUtils.saveIntoFile("SalezMedia.xlsx");
    }
}
