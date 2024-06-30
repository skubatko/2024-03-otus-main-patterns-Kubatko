package ru.skubatko.dev.jwt.service

import ru.skubatko.dev.api.models.user.User
import ru.skubatko.dev.api.models.user.Username
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.consts.EMPTY
import java.util.Date

@Service
class JwtTokenService(
    @Value("\${jwt.secret-key}") private val secretKey: String,
    @Value("\${jwt.expiration}") private val expiration: Long
) {

    fun generateToken(user: User): String {
        return generateToken(user, mapOf())
    }

    fun generateToken(user: User, extraClaims: Map<String, String>): String {
        val algorithm: Algorithm = Algorithm.HMAC256(secretKey)
        val jwtBuilder: JWTCreator.Builder = JWT.create()
            .withSubject(user.name.asString())
            .withIssuer("example")
            .withClaim(
                "role",
                user.authority.asString()
            )
            .withIssuedAt(Date(System.currentTimeMillis()))
            .withExpiresAt(Date(System.currentTimeMillis() + expiration))
        extraClaims.forEach(jwtBuilder::withClaim)
        return jwtBuilder.sign(algorithm)
    }

    fun decodeJWT(token: String?): DecodedJWT {
        val algorithm: Algorithm = Algorithm.HMAC256(secretKey)
        val verifier: JWTVerifier = JWT.require(algorithm).build()
        return verifier.verify(token)
    }

    fun <T> getClaim(token: String, claimsResolver: (Map<String, Claim>) -> T) =
        claimsResolver(getAllClaims(token))

    fun getUsername(token: String) =
        getClaim(token) { claims -> claims["sub"]?.asString() ?: EMPTY }

    fun getExpiration(token: String) =
        getClaim(token) { claims -> claims["exp"]?.asDate() ?: Date() }

    fun getAllClaims(token: String): MutableMap<String, Claim> = decodeJWT(token).claims

    fun isTokenValid(token: String?, username: Username) =
        try {
            decodeJWT(token).subject == username.asString() && !isTokenExpired(token)
        } catch (ex: TokenExpiredException) {
            false
        }

    fun isTokenExpired(token: String?) =
        try {
            decodeJWT(token).expiresAt.before(Date(System.currentTimeMillis()))
        } catch (ex: TokenExpiredException) {
            true
        }
}
