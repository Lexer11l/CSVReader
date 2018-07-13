import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  private static ArrayList<ArrayList<Double>> list = new ArrayList<>();

  public static void main(String[] args) throws IOException {
//    Scanner scanner = new Scanner(System.in);
//    String inputFilename = scanner.nextLine();
//    String outputFilename = scanner.nextLine();
//    scanner = new Scanner(new File(filename));
    System.out.println(getCellCoordinates("AAA256"));
    String inputFilename = "example.csv";
    Scanner scanner = new Scanner(new File(inputFilename));
    int counter = 0;
    while (scanner.hasNext()){
      list.add(new ArrayList<>());
      String[] line = scanner.nextLine().replace("\uFEFF","").split(";");
      for (String s:line) {
        if (isNumber(s)){
          list.get(counter).add(Double.parseDouble(s));
        }
        else {
          list.get(counter).add(getDoubleFromFormula(s));
        }
      }
      counter++;
    }
    FileWriter writer = new FileWriter("answer.csv");
    for (ArrayList<Double> line:list){
         writer.write(
                 String.join(";",
                         line.stream()
                                 .map(Object::toString)
                                 .collect(Collectors.toList())));
         writer.write('\n');
    }
    writer.flush();
    System.out.println(list);
  }


  /*
  * https://habr.com/post/50196/
  * */
  private static Double getDoubleFromFormula(String s) {
    ArrayDeque<Double> operands = new ArrayDeque<>();
    ArrayDeque<String> operators = new ArrayDeque<>();
    StringBuilder builder = new StringBuilder();
    for (Character c:s.toCharArray()){
      if (isOperator(c)){
        operands.add(getCellValue(builder.toString()));
        builder = new StringBuilder();
        String currentTop = operators.getFirst();

        switch (c){
          case '+':
            break;
          case '-':
            break;
          case '*':
            break;
          case '/':
            break;
          case '(':
            break;
          case ')':
            break;
        }

      }
      else {
        builder.append(c);
      }
    }

    return operands.getFirst();
  }

  private static boolean isOperator(Character c) {
    return c.equals('+') || c.equals('-') || c.equals('*') || c.equals('/');
  }


  private static Double getCellValue(String s){
    Integer row = Integer.parseInt(s.split(":")[0]);
    Integer column = Integer.parseInt(s.split(":")[1]);

    return list.get(row).get(column);
  }

  private static String getCellCoordinates(String s){
    String column = columnToInt(s.replaceAll("[0-9]+",""));
    String row = s.replaceAll("[A-Z]+", "");
    return row + ":" + column;
  }


  private static String columnToInt(String s) {
    int result = 0;
    for (int i = s.length()-1; i>=0 ;i--){
      result += (s.charAt(i)-'A'+1)*Math.pow(26, s.length()-1-i);
    }
    return String.valueOf(result);
  }

  private static boolean isNumber(String s){
    try{
      Double.parseDouble(s);
    }
    catch (NumberFormatException e){
      return false;
    }
    return true;
  }
}
