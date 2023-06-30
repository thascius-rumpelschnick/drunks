package org.kappa.server.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service
public class JasonWebTokenService {

  private static final Logger LOGGER = LoggerFactory.getLogger(JasonWebTokenService.class);

  private static final String SECRET = "baeldung";
  private static final String ISSUER = "Baeldung";
  private static final String SUBJECT = "Baeldung Details";
  private static final String DATA_CLAIM = "userId";
  private static final String DATA = "1234";
  private static final long TOKEN_VALIDITY_IN_MILLIS = 5000L;

  private final Algorithm algorithm;
  private final JWTVerifier verifier;


  public JasonWebTokenService() {
    this.algorithm = Algorithm.HMAC256(SECRET);
    this.verifier = JWT.require(this.algorithm).withIssuer(ISSUER).build();
  }


  public String createWebToken() {
    return JWT.create()
        .withIssuer(ISSUER)
        .withSubject(SUBJECT)
        .withClaim(DATA_CLAIM, DATA)
        .withClaim(DATA_CLAIM, DATA)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_IN_MILLIS))
        .withJWTId(UUID.randomUUID()
            .toString())
        .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
        .sign(this.algorithm);
  }


  public DecodedJWT verifyJWT(final String jwtToken) {
    try {
      return this.verifier.verify(jwtToken);

    } catch (final JWTVerificationException e) {
      LOGGER.error(e.getMessage());
    }

    return null;
  }


  public boolean isJWTExpired(final DecodedJWT decodedJWT) {
    final Date expiresAt = decodedJWT.getExpiresAt();
    return expiresAt.getTime() < System.currentTimeMillis();
  }


  public String getClaim(final DecodedJWT decodedJWT, final String claimName) {
    final Claim claim = decodedJWT.getClaim(claimName);
    return claim != null ? claim.asString() : null;
  }

}
