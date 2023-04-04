package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.security.KeyPair;

public class Main {
    public static void main(String[] args) {

        // creating private and public keys
        var keys = createKeys();

        // create JWT signed with private key
        var jwt = createJwt(keys.getPrivate());

        System.out.println("JWT: " + jwt);

        // parse JWT with public key and get CPF claim
        var cpf = getJwtClains(jwt, keys.getPublic()).getBody().get("cpf");

        System.out.println("CPF: " + cpf);

    }

    private static Jws<Claims> getJwtClains(String jwt, Key publicKey) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(jwt);
    }

    private static String createJwt(Key privateKey) {
        return Jwts.builder()
                .claim("cpf", "25575915808")
                .signWith(privateKey)
                .compact();
    }

    private static KeyPair createKeys() {
        return Keys.keyPairFor(SignatureAlgorithm.RS256);
    }
}