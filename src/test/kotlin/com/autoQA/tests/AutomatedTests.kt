package com.autoQA.tests

import com.autoQA.utilities.DriverFunctions
import com.autoQA.utilities.DriverFunctions.Companion.driver
import org.testng.Assert
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test
import pages.BasePage
import pages.LoginPage

class AutomatedTests {

    private val loginPage = LoginPage()
    private val basePage = BasePage()
    private val driverFunction = DriverFunctions()

    @BeforeTest
    fun createDriver() {
        driverFunction.buildDriver()
    }

    @Test(priority = 0, groups = ["Smoke Tests"])
    fun openWebPage() {
        val url = "https://www.saucedemo.com/"
        driverFunction.openURL(url)

        Assert.assertEquals(driver.currentUrl, url, "Web page URL did not match!")
    }

    @Test(priority = 1, groups = ["Smoke Tests"])
    fun loggingStandardUser() {

        loginPage.enterLogin()
        loginPage.enterPassword()
        loginPage.clickOnLoginButton()
    }

    @Test(priority = 2, groups = ["Smoke Tests"])
    fun addItemsToCart() {

        basePage.addProduct("add-to-cart-sauce-labs-bolt-t-shirt")
        basePage.addProduct("add-to-cart-sauce-labs-fleece-jacket")

        val itemName = driverFunction.getElementTextByCSS("#item_1_title_link .inventory_item_name")
        val itemPrice = driverFunction.getElementTextByXPATH("//a[@id='item_1_title_link']/..//following-sibling::*[@class='pricebar']/div")
        val itemCounter = driverFunction.getElementTextByCSS(".shopping_cart_badge")

        Assert.assertEquals(itemCounter,"2","Item counts do not match")

        driverFunction.clickOnElementByID("shopping_cart_container")

        val itemNameInCartElement = driverFunction.getElementsTextByClassName("inventory_item_name",0)
        val itemPriceInCartElement = driverFunction.getElementsTextByClassName("inventory_item_price",0)

        Assert.assertEquals(itemName,itemNameInCartElement,"Names do not match!")
        Assert.assertEquals(itemPrice,itemPriceInCartElement,"Prices do not match!")

        driverFunction.clickOnElementByID("continue-shopping")
    }

    @Test(priority = 3, groups = ["Smoke Tests"])
    fun removeItemFromCart() {

        driverFunction.clickOnElementByID("remove-sauce-labs-bolt-t-shirt")
        driverFunction.clickOnElementByID("add-to-cart-sauce-labs-bolt-t-shirt")
        driverFunction.clickOnElementByID("shopping_cart_container")
        driverFunction.clickOnElementByID("remove-sauce-labs-bolt-t-shirt")

        val itemCounter = driverFunction.getElementTextByCSS(".shopping_cart_badge")

        Assert.assertEquals(itemCounter,"1","Item counts do not match")

        driverFunction.clickOnElementByID("continue-shopping")
    }

    @Test(priority = 4, groups = ["Smoke Tests"])
    fun sortingNameFromZtoA () {

        val nameOfTheFirstElementBeforeSort = driverFunction.getElementsTextByClassName("inventory_item_name",0)
        driverFunction.selectFromDropDown("product_sort_container","Name (Z to A)")
        val nameOfTheFirstElementAfterSort = driverFunction.getElementsTextByClassName("inventory_item_name",0)

        Assert.assertNotEquals(nameOfTheFirstElementAfterSort,nameOfTheFirstElementBeforeSort,"First product before and after are the same! Sorting Broken!")
        }

    @Test(priority = 5, groups = ["Smoke Tests"])
    fun sortingPriceFromLowestToHighest () {

        driverFunction.selectFromDropDown("product_sort_container","Price (low to high)")
        val nameOfTheFirstElementBeforeSort = driverFunction.getElementsTextByClassName("inventory_item_name",0)
        driverFunction.selectFromDropDown("product_sort_container","Price (high to low)")
        val nameOfTheFirstElementAfterSort = driverFunction.getElementsTextByClassName("inventory_item_name",0)

        Assert.assertNotEquals(nameOfTheFirstElementAfterSort,nameOfTheFirstElementBeforeSort,"First product cost before and after are the same! Sorting Broken!")
    }

    @Test(priority = 6, groups = ["Smoke Tests"])
    fun checkoutFormFill() {

        driverFunction.clickOnElementByID("shopping_cart_container")
        driverFunction.clickOnElementByID("checkout")
        driverFunction.enterDataByID("first-name","First Name")
        driverFunction.enterDataByID("last-name","Last Name")
        driverFunction.enterDataByID("postal-code","12-123")
        driverFunction.clickOnElementByID("continue")
        driverFunction.clickOnElementByID("finish")

        val finishOrderTextElement = driverFunction.getElementTextByClassName("complete-header")
        Assert.assertEquals(finishOrderTextElement,"THANK YOU FOR YOUR ORDER", "Finish Order text it is not matching!")
    }

    @AfterTest(groups = ["Smoke Tests"])
    fun tearDownDriver() {
        driver.quit()
    }

}

