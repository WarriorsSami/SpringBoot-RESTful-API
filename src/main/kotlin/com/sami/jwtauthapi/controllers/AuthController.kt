package com.sami.jwtauthapi.controllers

import com.sami.jwtauthapi.entities.User
import com.sami.jwtauthapi.models.LoginDto
import com.sami.jwtauthapi.models.Message
import com.sami.jwtauthapi.models.RegisterDto
import com.sami.jwtauthapi.services.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api")
class AuthController(
    private val userService: UserService
) {
    private val secretKey = "Zq4t7w!z%C*F-J@NcRfUjXn2r5u8x/A?D(G+KbPdSgVkYp3s6v9y\$B&E)H@McQfT\\n"

    @PostMapping("register")
    fun register(@RequestBody body: RegisterDto): ResponseEntity<User> {
        val user = User()
        user.name = body.name
        user.email = body.email
        user.password = body.password

        return ResponseEntity.ok(userService.saveUser(user))
    }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDto, response: HttpServletResponse): ResponseEntity<Any> {
        val user = userService.getUserByEmail(body.email)
            ?: return ResponseEntity.badRequest().body(Message("User not found"))

        if (!user.comparePassword(body.password)) {
            return ResponseEntity.badRequest().body(Message("Wrong password"))
        }

        val issuer = user.id.toString()
        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true
        response.addCookie(cookie)

        return ResponseEntity.ok(Message("success"))
    }

    @GetMapping("user")
    fun getUser(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        try {
            if (jwt == null) {
                return ResponseEntity.status(401).body(Message("Unauthenticated"))
            }

            val userId = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).body.issuer.toInt()
            val user = userService.getUserById(userId)
                ?: return ResponseEntity.badRequest().body(Message("User not found"))

            return ResponseEntity.ok(user)
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Message("Unauthenticated"))
        }
    }

    @PostMapping("logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val cookie = Cookie("jwt", "")
        cookie.isHttpOnly = true
        cookie.maxAge = 0
        response.addCookie(cookie)

        return ResponseEntity.ok(Message("success"))
    }

    @GetMapping("users")
    fun getUsers(): ResponseEntity<Any> {
        return ResponseEntity.ok(userService.getAllUsers())
    }
}