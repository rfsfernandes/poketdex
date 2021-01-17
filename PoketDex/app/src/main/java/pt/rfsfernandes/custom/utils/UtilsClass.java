package pt.rfsfernandes.custom.utils;

import android.content.Context;

import pt.rfsfernandes.R;

public class UtilsClass {

  /**
   * A method to turn pokemon names with hifen (-) into camel cased name
   * @param word to camel case
   * @return String in camel case
   */
  public static String toCamelCase(String word) {
    String[] s = word.split("-");
    StringBuilder newWord = new StringBuilder();

    for (String s1 : s) {
      char[] chars = s1.toCharArray();
      for (int i = 0; i < chars.length; i++) {
        if (i == 0)
          newWord.append(String.valueOf(chars[i]).toUpperCase());
        else
          newWord.append(String.valueOf(chars[i]).toLowerCase());
      }
      newWord.append(" ");
    }

    newWord.trimToSize();

    return newWord.toString();
  }

  /**
   * Returns the color matching the name
   * @param typeName
   * @return
   */
  public static int returnColorId(Context context, String typeName) {
    int colorId = 0;

    switch (typeName.toLowerCase()) {
      case "bug":
        colorId = context.getResources().getColor(R.color.bug);
        break;
      case "dark":
        colorId = context.getResources().getColor(R.color.dark);
        break;
      case "dragon":
        colorId = context.getResources().getColor(R.color.dragon);
        break;
      case "electric":
        colorId = context.getResources().getColor(R.color.electric);
        break;
      case "fairy":
        colorId = context.getResources().getColor(R.color.fairy);
        break;
      case "fighting":
        colorId = context.getResources().getColor(R.color.fighting);
        break;
      case "fire":
        colorId = context.getResources().getColor(R.color.fire);
        break;
      case "flying":
        colorId = context.getResources().getColor(R.color.flying);
        break;
      case "ghost":
        colorId = context.getResources().getColor(R.color.ghost);
        break;
      case "grass":
        colorId = context.getResources().getColor(R.color.grass);
        break;
      case "ground":
        colorId = context.getResources().getColor(R.color.ground);
        break;
      case "ice":
        colorId = context.getResources().getColor(R.color.ice);
        break;
      case "normal":
        colorId = context.getResources().getColor(R.color.normal);
        break;
      case "poison":
        colorId = context.getResources().getColor(R.color.poison);
        break;
      case "psychic":
        colorId = context.getResources().getColor(R.color.psychic);
        break;
      case "rock":
        colorId = context.getResources().getColor(R.color.rock);
        break;
      case "steel":
        colorId = context.getResources().getColor(R.color.steel);
        break;
      case "water":
        colorId = context.getResources().getColor(R.color.water);
        break;

    }

    return colorId;
  }

}
