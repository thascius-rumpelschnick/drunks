package org.kappa.server.domain;

public class OutputMessage {

  private final String from;
  private final String text;
  private final String time;


  public OutputMessage(final String from, final String text, final String time) {

    this.from = from;
    this.text = text;
    this.time = time;
  }


  public String getText() {
    return this.text;
  }


  public String getTime() {
    return this.time;
  }


  public String getFrom() {
    return this.from;
  }
}