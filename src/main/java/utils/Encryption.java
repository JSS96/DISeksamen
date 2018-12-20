package utils;

public final class Encryption {

  public static String encryptDecryptXOR(String rawString) {

    // If encryption is enabled in Config.
    if (Config.getEncryption()) {

      // The key is predefined and hidden in code
      // TODO: Create a more complex code and store it somewhere better FIXED
      /*
      Fixed by saveing the key in the config file and storing it in an array
       */

      char[] key = Config.getEncrtyptionKey().toCharArray();

      // Stringbuilder enables you to play around with strings and make useful stuff
      StringBuilder thisIsEncrypted = new StringBuilder();

      // TODO: This is where the magic of XOR is happening. Are you able to explain what is going on FIXED
      /**
       * Dette er et for loop der køre lige så mange gange som stregen er lang,
       * altså tæller en op for hvert bokstav/tal der er blevet krypteret, altså tæller i en op.
       * Det der sker inde i for loopet er, den streng der er blevet bygget ovenfor bliver (thisIsEncrypted),
       * for tljføjet den krypteret del der bliver lavet indtil hele streget er krypteret.
       * Alle tegn har en værdi, så hvis CharAt(i) er et g, så har dette måske værdien 12, som bliver opløftet i
       * key. Key har også en tal værdi, som bliver fundet ved at tage krypteringskodens længde og divederet med den
       * remainder metode, som kunne være 3. Så vil den char der vil blive append til thisIsEncrypted være et tegn,
       * hvor talværdien matcher
       */
      for (int i = 0; i < rawString.length(); i++) {
        thisIsEncrypted.append((char) (rawString.charAt(i) ^ key[i % key.length]));
      }

      // We return the encrypted string
      return thisIsEncrypted.toString();

    } else {
      // We return without having done anything
      return rawString;
    }
  }
}
