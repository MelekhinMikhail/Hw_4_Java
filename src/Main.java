import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Мелехин Михаил Тимофеевич");
        System.out.println("Группа РИБО-01-21");

        int countOfFiles;
        while (true) {
            System.out.println();
            System.out.println("Введите какое кол-во файлов хотите склеить:");
            try {
                countOfFiles = Integer.parseInt(scan());
                if (countOfFiles <= 1) {
                    System.out.println("Необходимо ввести целое число больше единицы.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Необходимо ввести целое число больше нуля.");
            }
        }

        File[] fileArray = new File[countOfFiles];
        String path;
        File file;
        for (int i=0; i < fileArray.length; i++) {
            while (true) {
                System.out.println();
                System.out.println("Введите путь " + (i+1) + "-го файла:");
                path = scan();
                if (path.isEmpty() || path.isBlank()) {
                    System.out.println("Путь необходимо заполнить!");
                    continue;
                }
                file = new File(path);
                if (!file.exists()) {
                    System.out.println("Файл не найден! Попробуйте снова.");
                    continue;
                }
                if (file.isDirectory()) {
                    System.out.println("Ошибка! Необходимо указать файл, а не директорию.");
                    continue;
                }
                fileArray[i] = file;
                break;
            }
        }

        FileInputStream fis;
        byte[][] contentArray = new byte[countOfFiles][];
        byte[] fileContent;
        int countOfBytes = 0;
        for (int j=0; j < contentArray.length; j++) {
            System.out.println();
            try {
                fis = new FileInputStream(fileArray[j]);
                fileContent = new byte[fis.available()];
                countOfBytes += fis.available();
                fis.read(fileContent);
                fis.close();
                contentArray[j] = fileContent;
                System.out.println("Содержимое " + (j+1) + "-го файла:");
                for (byte b : fileContent) {
                    System.out.print((char) b);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        File resultFile = new File(fileArray[0].getParent() + "\\result.txt");
        byte[] resultArray = new byte[countOfBytes];
        int a = 0;
        for (int i=0; i < contentArray.length; i++) {
            for (int j=0; j < contentArray[i].length; j++) {
                resultArray[a] = contentArray[i][j];
                a++;
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(resultFile);
            fos.write(resultArray);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
        System.out.println("Успешно! Файлы склеены.");
        System.out.println("Результат находится в файле по пути: " + resultFile.getPath());
    }

    public static String scan() {
        return new Scanner(System.in).nextLine();
    }
}