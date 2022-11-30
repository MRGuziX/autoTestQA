package com.autoQA.tests

import com.autoQA.utilities.DriverFunctions
import org.testng.Assert
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test

class AutomatedTests : DriverFunctions() {

    val standardUserAccountLogin= "standard_user"
    val userPassword = "secret_sauce"
    val loginTextfieldSelector = "user-name"
    val passwordTextFieldSelector = "password"

    @BeforeTest
    fun createDriver() {
        buildDriver()
    }

    @Test(priority = 0, suiteName = "smoke")
    fun openWebPage() {
        val url = "https://www.saucedemo.com/"
        openURL(url)

        Assert.assertEquals(driver.currentUrl, url, "Web page URL did not match!")
    }

    @Test(priority = 1, suiteName = "smoke")
    fun loggingStandardUser() {

        enterDataByID(loginTextfieldSelector,standardUserAccountLogin)
        enterDataByID(passwordTextFieldSelector,userPassword)
        clickOnElementByID("login-button")
    }

    @Test(priority = 2, suiteName = "smoke")
    fun addItemsToCart() {

        clickOnElementByID("add-to-cart-sauce-labs-bolt-t-shirt")
        clickOnElementByID("add-to-cart-sauce-labs-fleece-jacket")

        val itemName = getElementTextByCSS("#item_1_title_link .inventory_item_name")
        val itemPrice = getElementTextByXPATH("//a[@id='item_1_title_link']/..//following-sibling::*[@class='pricebar']/div")
        val itemCounter = getElementTextByCSS(".shopping_cart_badge")

        Assert.assertEquals(itemCounter,"2","Item counts do not match")

        clickOnElementByID("shopping_cart_container")

        val itemNameInCartElement = getElementsTextByClassName("inventory_item_name",0)
        val itemPriceInCartElement = getElementsTextByClassName("inventory_item_price",0)

        Assert.assertEquals(itemName,itemNameInCartElement,"Names do not match!")
        Assert.assertEquals(itemPrice,itemPriceInCartElement,"Prices do not match!")

        clickOnElementByID("continue-shopping")
    }

    @Test(priority = 3, suiteName = "smoke")
    fun removeItemFromCart() {

        clickOnElementByID("remove-sauce-labs-bolt-t-shirt")
        clickOnElementByID("add-to-cart-sauce-labs-bolt-t-shirt")
        clickOnElementByID("shopping_cart_container")
        clickOnElementByID("remove-sauce-labs-bolt-t-shirt")

        val itemCounter = getElementTextByCSS(".shopping_cart_badge")

        Assert.assertEquals(itemCounter,"1","Item counts do not match")

        clickOnElementByID("continue-shopping")
    }

    @Test(priority = 4, suiteName = "smoke")
    fun sortingNameFromZtoA () {

        val nameOfTheFirstElementBeforeSort = getElementsTextByClassName("inventory_item_name",0)
        selectFromDropDown("product_sort_container","Name (Z to A)")
        val nameOfTheFirstElementAfterSort = getElementsTextByClassName("inventory_item_name",0)

        Assert.assertNotEquals(nameOfTheFirstElementAfterSort,nameOfTheFirstElementBeforeSort,"First product before and after are the same! Sorting Broken!")
        }

    @Test(priority = 5, suiteName = "smoke")
    fun sortingPriceFromLowestToHighest () {

        selectFromDropDown("product_sort_container","Price (low to high)")
        val nameOfTheFirstElementBeforeSort = getElementsTextByClassName("inventory_item_name",0)
        selectFromDropDown("product_sort_container","Price (high to low)")
        val nameOfTheFirstElementAfterSort = getElementsTextByClassName("inventory_item_name",0)

        Assert.assertNotEquals(nameOfTheFirstElementAfterSort,nameOfTheFirstElementBeforeSort,"First product cost before and after are the same! Sorting Broken!")
    }

    @Test(priority = 6, suiteName = "smoke")
    fun checkoutFormFill() {

        clickOnElementByID("shopping_cart_container")
        clickOnElementByID("checkout")
        enterDataByID("first-name","First Name")
        enterDataByID("last-name","Last Name")
        enterDataByID("postal-code","12-123")
        clickOnElementByID("continue")
        clickOnElementByID("finish")

        val finishOrderTextElement = getElementTextByClassName("complete-header")
        Assert.assertEquals(finishOrderTextElement,"THANK YOU FOR YOUR ORDER", "Finish Order text it is not matching!")
    }

    @Test(priority = 7, suiteName = "smoke")
    fun checkOutFormWarnings() {

    }

    @AfterTest
    fun tearDownDriver() {
        driver.quit()
    }

}

