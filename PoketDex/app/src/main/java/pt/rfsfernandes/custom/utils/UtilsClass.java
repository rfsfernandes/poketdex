package pt.rfsfernandes.custom.utils;

import android.content.Context;

import pt.rfsfernandes.R;

public class UtilsClass {

  /**
   * A method to turn pokemon names with hifen (-) into camel cased name
   *
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
   *
   * @param typeName Pokemon Type
   * @return Color of corresponding pokemon type
   */
  public static int returnColorId(Context context, String typeName) {
    int colorId = 0;

    switch (typeName.toLowerCase()) {
      case "bug":
        colorId = context.getResources().getColor(R.color.bug, null);
        break;
      case "dark":
        colorId = context.getResources().getColor(R.color.dark, null);
        break;
      case "dragon":
        colorId = context.getResources().getColor(R.color.dragon, null);
        break;
      case "electric":
        colorId = context.getResources().getColor(R.color.electric, null);
        break;
      case "fairy":
        colorId = context.getResources().getColor(R.color.fairy, null);
        break;
      case "fighting":
        colorId = context.getResources().getColor(R.color.fighting, null);
        break;
      case "fire":
        colorId = context.getResources().getColor(R.color.fire, null);
        break;
      case "flying":
        colorId = context.getResources().getColor(R.color.flying, null);
        break;
      case "ghost":
        colorId = context.getResources().getColor(R.color.ghost, null);
        break;
      case "grass":
        colorId = context.getResources().getColor(R.color.grass, null);
        break;
      case "ground":
        colorId = context.getResources().getColor(R.color.ground, null);
        break;
      case "ice":
        colorId = context.getResources().getColor(R.color.ice, null);
        break;
      case "normal":
        colorId = context.getResources().getColor(R.color.normal, null);
        break;
      case "poison":
        colorId = context.getResources().getColor(R.color.poison, null);
        break;
      case "psychic":
        colorId = context.getResources().getColor(R.color.psychic, null);
        break;
      case "rock":
        colorId = context.getResources().getColor(R.color.rock, null);
        break;
      case "steel":
        colorId = context.getResources().getColor(R.color.steel, null);
        break;
      case "water":
        colorId = context.getResources().getColor(R.color.water, null);
        break;

    }

    return colorId;
  }

}
