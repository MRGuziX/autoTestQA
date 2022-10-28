package com.autoQA

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.Select
import org.testng.Assert
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test


class AutomatedTests {

    var driver: WebDriver? = null
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

    @Test(priority = 0)
    fun openWebPage() {
        val url = "https://www.saucedemo.com/"
        driver!!.get(url)

        Assert.assertEquals(driver!!.currentUrl, url, "Web page URL did not match!")
    }

    @Test(priority = 1)
    fun loggingStandardUser() {

        val loginTextFieldElement = driver!!.findElement(By.id(loginTextfieldSelector))
        loginTextFieldElement.sendKeys(standardUserAccountLogin)

        val passwordTextFieldElement = driver!!.findElement(By.id(passwordTextFieldSelector))
        passwordTextFieldElement.sendKeys(userPassword)

        val loginButtonElement = driver!!.findElement(By.id(loginButtonSelector))
        loginButtonElement.click()
    }

    @Test(priority = 2)
    fun addItemsToCart() {

        val addToCartFirstElement = driver!!.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt"))
        addToCartFirstElement.click()

        val addToCartSecondElement = driver!!.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"))
        addToCartSecondElement.click()

        val itemNameElement = driver!!.findElement(By.cssSelector("#item_1_title_link .inventory_item_name"))
        val itemName = itemNameElement.text
        val itemPriceElement = driver!!.findElement(By.xpath("//a[@id='item_1_title_link']/..//following-sibling::*[@class='pricebar']/div"))
        val itemPrice = itemPriceElement.text
        val itemCounterElement = driver!!.findElement(By.cssSelector(".shopping_cart_badge"))
        val itemCounter = itemCounterElement.text

        Assert.assertEquals(itemCounter,"2","Item counts do not match")

        val cartIconElement = driver!!.findElement(By.id("shopping_cart_container"))
        cartIconElement.click()

        val itemNameInCartElement = driver!!.findElements(By.className("inventory_item_name"))[0].text
        val itemPriceInCartElement = driver!!.findElements(By.className("inventory_item_price"))[0].text

        Assert.assertEquals(itemName,itemNameInCartElement,"Names do not match!")
        Assert.assertEquals(itemPrice,itemPriceInCartElement,"Prices do not match!")

        val continueShoppingButtonElement = driver!!.findElement(By.id("continue-shopping"))
        continueShoppingButtonElement.click()
    }

    @Test(priority = 3)
    fun removeItemFromCart() {

        val removeItemElement = driver!!.findElement(By.id("remove-sauce-labs-bolt-t-shirt"))
        removeItemElement.click()

        val addToCartElement = driver!!.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt"))
        addToCartElement.isDisplayed
        addToCartElement.click()

        val cartIconElement = driver!!.findElement(By.id("shopping_cart_container"))
        cartIconElement.click()
        //Finding button once again, because DOM has changed since previous search and element cant be find again
        val removeItemInCartElement = driver!!.findElement(By.id("remove-sauce-labs-bolt-t-shirt"))
        removeItemInCartElement.click()

        val itemCounterElement = driver!!.findElement(By.cssSelector(".shopping_cart_badge"))
        val itemCounter = itemCounterElement.text
        Assert.assertEquals(itemCounter,"1","Item counts do not match")

        val continueShoppingButtonElement = driver!!.findElement(By.id("continue-shopping"))
        continueShoppingButtonElement.click()
    }

    @Test(priority = 4)
    fun sortingNameFromZtoA () {

        val nameOfTheFirstElementBeforeSort = driver!!.findElements(By.className("inventory_item_name"))[0].text
        val sortDropdownElement = Select(driver!!.findElement(By.className("product_sort_container")))
        sortDropdownElement.selectByVisibleText("Name (Z to A)")

        val nameOfTheFirstElementAfterSort = driver!!.findElements(By.className("inventory_item_name"))[0].text
        Assert.assertNotEquals(nameOfTheFirstElementAfterSort,nameOfTheFirstElementBeforeSort,"First product before and after are the same! Sorting Broken!")
        }

    @Test(priority = 5)
    fun sortingPriceFromLowestToHighest () {

        val sortDropdownElement = Select(driver!!.findElement(By.cssSelector(".product_sort_container")))
        sortDropdownElement.selectByVisibleText("Price (low to high)")

        val nameOfTheFirstElementBeforeSort = driver!!.findElements(By.className("inventory_item_price"))[0].text
        //Finding button once again, because DOM has changed since previous search and element cant be find again
        val sortDropdownElement2 = Select(driver!!.findElement(By.cssSelector(".product_sort_container")))
        sortDropdownElement2.selectByVisibleText("Price (high to low)")

        val nameOfTheFirstElementAfterSort = driver!!.findElements(By.className("inventory_item_price"))[0].text
        Assert.assertNotEquals(nameOfTheFirstElementAfterSort,nameOfTheFirstElementBeforeSort,"First product cost before and after are the same! Sorting Broken!")
    }

    @Test(priority = 6)
    fun checkoutFormFill() {

        val cartIconElement = driver!!.findElement(By.id("shopping_cart_container"))
        cartIconElement.click()

        val checkoutButtonElement = driver!!.findElement(By.id("checkout"))
        checkoutButtonElement.click()

        val firstNameTextFieldElement = driver!!.findElement(By.id("first-name"))
        val lastNameTextFieldElement = driver!!.findElement(By.id("last-name"))
        val postalCodeTextFieldElement = driver!!.findElement(By.id("postal-code"))
        val continueButtonElement = driver!!.findElement((By.id("continue")))

        firstNameTextFieldElement.sendKeys("First Name")
        lastNameTextFieldElement.sendKeys("Last Name")
        postalCodeTextFieldElement.sendKeys("12-123")
        continueButtonElement.click()

        val finishButtonElement = driver!!.findElement(By.id("finish"))
        finishButtonElement.click()

        val finishOrderTextElement = driver!!.findElement(By.className("complete-header")).text
        Assert.assertEquals(finishOrderTextElement,"THANK YOU FOR YOUR ORDER", "Finish Order text it is not matching!")

    }


    @AfterTest
    fun tearDownDriver() {
        driver!!.quit()
    }

}

