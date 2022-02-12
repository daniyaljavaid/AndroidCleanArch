package com.dj.login.util

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class LoginValidatorTest {

    private lateinit var validator: LoginValidator

    @Before
    fun setUp() {
        validator = LoginValidator()
    }

    @Test
    fun `empty email returns false`() {
        assertEquals(false, validator.isEmailValid(""))
    }

    @Test
    fun `email without domain returns false`() {
        assertEquals(false, validator.isEmailValid("abc@gmail"))
    }

    @Test
    fun `email without username returns false`() {
        assertEquals(false, validator.isEmailValid("@gmail.com"))
    }

    @Test
    fun `email without @ returns false`() {
        assertEquals(false, validator.isEmailValid("abcgmail.com"))
    }

    @Test
    fun `valid email returns true`() {
        assertEquals(true, validator.isEmailValid("abc@gmail.com"))
    }

    @Test
    fun `valid email with country domain returns true`() {
        assertEquals(true, validator.isEmailValid("abc@gmail.com.pk"))
    }

    @Test
    fun `valid email with 2 digit domain returns true`() {
        assertEquals(true, validator.isEmailValid("abc@gmail.co.pk"))
    }

}