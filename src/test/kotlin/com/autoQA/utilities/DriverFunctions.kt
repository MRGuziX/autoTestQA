package com.autoQA.utilities

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import java.awt.Button


open class DriverFunctions {

    var driver: WebDriver? = null

    fun buildDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/kotlin/com/autoQA/drivers/chromedriver.exe")
        driver = ChromeDriver()
    }

     fun refreshPage() {
        driver!!.navigate().refresh()
    }

     fun openURL(url: String) {
         driver!!.get(url)
     }

     fun clickOnElementByID(elementID: String) {
         driver!!.findElement(By.id(elementID)).click()
     }

     fun getElementTextByCSS (elementCSS: String) {
         driver!!.findElement(By.cssSelector(elementCSS)).text

     }

     fun getElementTextByXPATH (elementXPATH: String) {
         driver!!.findElement(By.xpath(elementXPATH)).text
     }

     fun getElementsTextByClassName (className: String,position: Int){
         driver!!.findElements(By.className(className))[position].text
     }
}