package org.kappa.client.http;

public enum Status {

  ACCEPTED(200), BAD_REQUEST(400), DENIED(401), INTERNAL_SERVER_ERROR(500);

  public final int statusCode;


  Status(final int statusCode) {
    this.statusCode = statusCode;
  }


  public boolean foo(final int statusCode) {
    return this.statusCode == statusCode;
  }

}
