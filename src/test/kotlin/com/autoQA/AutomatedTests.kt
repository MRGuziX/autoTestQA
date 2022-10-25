package com.autoQA

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.testng.Assert
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test


class AutomatedTests {

    private var driver: WebDriver? = null
    val standardUserAccountLogin = "standard_user"
    val userPassword: String = "secret_sauce"
    val loginTextfieldSelector = "user-name"
    val passwordTextFieldSelector = "password"
    val loginButtonSelector = "login-button"

    @BeforeTest
    fun createDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/kotlin/com/autoQA/drivers/chromedriver.exe")
        driver = ChromeDriver()
    }

    @AfterTest
    fun tearDownDriver() {
        driver!!.quit()
    }

    @BeforeTest
    fun openWebPage() {
        val url = "https://www.saucedemo.com/"
        driver!!.get(url)

        Assert.assertEquals(driver!!.currentUrl, url, "Web page URL did not match!")
    }

    @Test
    fun loggingStandardUser() {

        val loginTextFieldElement = driver!!.findElement(By.id(loginTextfieldSelector))
        loginTextFieldElement.sendKeys(standardUserAccountLogin)

        val passwordTextFieldElement = driver!!.findElement(By.id(passwordTextFieldSelector))
        passwordTextFieldElement.sendKeys(userPassword)

        val loginButtonElement = driver!!.findElement(By.id(loginButtonSelector))
        loginButtonElement.click()
    }

    @Test
    fun zAddItemToCart() {

        val addToCartElement = driver!!.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt"))
        addToCartElement.click()

        val itemNameElement = driver!!.findElement(By.cssSelector("#item_1_title_link .inventory_item_name"))
        val itemName = itemNameElement.text

        val itemPriceElement = driver!!.findElement(By.xpath("//a[@id='item_1_title_link']/..//following-sibling::*[@class='pricebar']/div"))
        val itemPrice = itemPriceElement.text

        val itemCounterElement = driver!!.findElement(By.cssSelector(".shopping_cart_badge"))
        val itemCounter = itemCounterElement.text

    }
}
