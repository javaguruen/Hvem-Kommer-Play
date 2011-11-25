package no.hvemkommer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatoUtil {
  public static final String DATOFORMAT="dd.MM.yyyy";

  public static String formatterDato( Date dato){
    return new SimpleDateFormat( DATOFORMAT ).format( dato );
  }
}
