package com.sopp.Payment.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class FirebaseTokenAuthenticationFilter(
    private val firebaseAuth: FirebaseAuth,
    private val tokenHelper: TokenHelper
) {

    private val log: Logger = LoggerFactory.getLogger(FirebaseTokenAuthenticationFilter::class.java)

    private fun verifyToken(request: HttpServletRequest) {
        val token: String? = tokenHelper.getBearerToken(request)
        try {
            val decodedToken: FirebaseToken = firebaseAuth.verifyIdToken(token, true)
            if (decodedToken.uid != null){
                println("Success")
            }
        } catch (e: FirebaseAuthException) {
            log.error("Error while validate token: ${e.message}", e)
        } catch (e: Exception) {
            log.error("Error: ${e.message}", e)
        }
    }
}