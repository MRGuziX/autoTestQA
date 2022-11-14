package com.autoQA.utilities

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.Select


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

    fun getElementTextByCSS(elementCSS: String): String? {
        return driver!!.findElement(By.cssSelector(elementCSS)).text
    }

     fun getElementTextByXPATH (elementXPATH: String): String? {
        return driver!!.findElement(By.xpath(elementXPATH)).text
     }

     fun getElementsTextByClassName (className: String,position: Int): String?{
         return driver!!.findElements(By.className(className))[position].text
     }

    // TODO: Delete that funciotn duplicate of code 
    fun getOneOfTheElementFromList(className: String,position: Int): String?{
        return driver!!.findElements(By.className(className))[position].text
    }

    fun selectFromDropDown(dropDownClassName: String, visibleText: String ) {
        val sortDropdownElement = Select(driver!!.findElement(By.className(dropDownClassName)))
        sortDropdownElement.selectByVisibleText(visibleText)
    }
}