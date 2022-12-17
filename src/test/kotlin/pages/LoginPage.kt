package pages

import com.autoQA.utilities.DriverFunctions

open class LoginPage : DriverFunctions() {

    private val standardUserAccountLogin = "standard_user"
    private val userPassword = "secret_sauce"
    private val loginTextfieldSelector = "user-name"
    private val passwordTextFieldSelector = "password"


    fun enterLogin() {
        enterDataByID(loginTextfieldSelector,standardUserAccountLogin)
    }

    fun enterPassword() {
        buildDriver()
        enterDataByID(passwordTextFieldSelector,userPassword)
    }

    fun clickOnLoginButton() {
        clickOnElementByID("login-button")
    }

}