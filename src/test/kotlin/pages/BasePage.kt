package pages

import com.autoQA.utilities.DriverFunctions

class BasePage : DriverFunctions() {

    fun addProduct(productName: String){
        clickOnElementByID(productName)
    }



}