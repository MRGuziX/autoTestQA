package com.autoQA.tests

import com.autoQA.utilities.DriverFunctions
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.Select
import org.testng.Assert
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test

class AutomatedTests : DriverFunctions() {

    val standardUserAccountLogin= "standard_user"
    val lockedUserAccountLogin= "locked_out_user"
    val problemUserAccountLogin = "problem_user"
    val userPassword = "secret_sauce"
    val loginTextfieldSelector = "user-name"
    val passwordTextFieldSelector = "password"
    val loginButtonSelector = "login-button"

    @BeforeTest
    fun createDriver() {
        buildDriver()
    }

    @Test(priority = 0)
    fun openWebPage() {
        val url = "https://www.saucedemo.com/"
        openURL(url)

        Assert.assertEquals(driver!!.currentUrl, url, "Web page URL did not match!")
    }

//    @Test(priority = 1)
//    fun loggingLockedUser() {
//
//        val loginTextFieldElement = driver!!.findElement(By.id(loginTextfieldSelector))
//        loginTextFieldElement.sendKeys(lockedUserAccountLogin)
//
//        val passwordTextFieldElement = driver!!.findElement(By.id(passwordTextFieldSelector))
//        passwordTextFieldElement.sendKeys(userPassword)
//
//        val loginButtonElement = driver!!.findElement(By.id(loginButtonSelector))
//        loginButtonElement.click()
//
//        val errorLockedUserLoginMessage = driver!!.findElement(By.className("error-message-container")).text
//        Assert.assertEquals(
//            errorLockedUserLoginMessage,
//            "Epic sadface: Sorry, this user has been locked out.",
//            "Error message do  not match"
//        )
//    }

    @Test(priority = 1)
    fun loggingStandardUser() {

        val loginTextFieldElement = driver!!.findElement(By.id(loginTextfieldSelector))
        loginTextFieldElement.sendKeys(standardUserAccountLogin)
        val passwordTextFieldElement = driver!!.findElement(By.id(passwordTextFieldSelector))
        passwordTextFieldElement.sendKeys(userPassword)
        clickOnElementByID("login-button")
    }

    @Test(priority = 2)
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

    @Test(priority = 3)
    fun removeItemFromCart() {

        clickOnElementByID("remove-sauce-labs-bolt-t-shirt")
        clickOnElementByID("add-to-cart-sauce-labs-bolt-t-shirt")
        clickOnElementByID("shopping_cart_container")
        clickOnElementByID("remove-sauce-labs-bolt-t-shirt")

        val itemCounter = getElementTextByCSS(".shopping_cart_badge")

        Assert.assertEquals(itemCounter,"1","Item counts do not match")

        clickOnElementByID("continue-shopping")
    }

    @Test(priority = 4)
    fun sortingNameFromZtoA () {

        val nameOfTheFirstElementBeforeSort = getElementsTextByClassName("inventory_item_name",0)
        selectFromDropDown("product_sort_container","Name (Z to A)")
        val nameOfTheFirstElementAfterSort = getElementsTextByClassName("inventory_item_name",0)

        Assert.assertNotEquals(nameOfTheFirstElementAfterSort,nameOfTheFirstElementBeforeSort,"First product before and after are the same! Sorting Broken!")
        }

    @Test(priority = 5)
    fun sortingPriceFromLowestToHighest () {

        selectFromDropDown("product_sort_container","Price (low to high)")
        val nameOfTheFirstElementBeforeSort = getElementsTextByClassName("inventory_item_name",0)
        selectFromDropDown("product_sort_container","Price (high to low)")
        val nameOfTheFirstElementAfterSort = getElementsTextByClassName("inventory_item_name",0)

        Assert.assertNotEquals(nameOfTheFirstElementAfterSort,nameOfTheFirstElementBeforeSort,"First product cost before and after are the same! Sorting Broken!")
    }

    @Test(priority = 6)
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

    @Test(priority = 7)
    fun checkOutFormWarnings() {

    }

    @AfterTest
    fun tearDownDriver() {
        driver!!.quit()
    }

}

