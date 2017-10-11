package util;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Zhao Zhe on 2017/10/11.
 */
public class TextAppender extends LogAppender{

    private JTextArea textArea;
    private JScrollPane scroll;
    public TextAppender(JTextArea textArea, JScrollPane scroll) throws IOException {
        super("textArea");
        this.textArea = textArea;
        this.scroll = scroll;
    }
    @Override
    public void run() {
        // 不间断地扫描输入流
        Scanner scanner = new Scanner(reader);
        // 将扫描到的字符流显示在指定的JLabel上
        while (scanner.hasNextLine()) {
            try {
                Thread.sleep(100);
                String line = scanner.nextLine();
                textArea.append(line);
                textArea.append("\n");
                scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
            } catch (Exception ex) {
            }
        }
    }
}
