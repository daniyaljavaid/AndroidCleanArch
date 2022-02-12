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
    fun `valid email with 2 character domain returns true`() {
        assertEquals(true, validator.isEmailValid("abc@gmail.co.pk"))
    }

    //Password Tests
    @Test
    fun `empty password returns false`() {
        assertEquals(false, validator.isPasswordValid(""))
    }

    @Test
    fun `password without alphabet returns false`() {
        assertEquals(false, validator.isPasswordValid("123213@._"))
    }

    @Test
    fun `password without digit returns false`() {
        assertEquals(false, validator.isPasswordValid("Aasdasd@._"))
    }

    @Test
    fun `password with whitespace returns false`() {
        assertEquals(false, validator.isPasswordValid("Aasdasd 123._"))
    }

    @Test
    fun `password with 6 characters returns false`() {
        assertEquals(false, validator.isPasswordValid("Test@1"))
    }

    @Test
    fun `password without cap alphabet returns false`() {
        assertEquals(false, validator.isPasswordValid("a123._asd"))
    }

    @Test
    fun `password without special character returns false`() {
        assertEquals(false, validator.isPasswordValid("Test1234"))
    }

    @Test
    fun `password with 1cap alphabet, 1digit, no space, 8 char with special char returns true`() {
        assertEquals(true, validator.isPasswordValid("Test@123"))
    }

    @Test
    fun `password with 2cap alphabet, 3digit, no space, 10 char with 3 special char returns true`() {
        assertEquals(true, validator.isPasswordValid("*TesT@123*"))
    }

    @Test
    fun `password with no cap alphabet, 3digit, no space, 10 char with 3 special char returns true`() {
        assertEquals(false, validator.isPasswordValid("*test@123*"))
    }
}