package GUI;

import Connector.Connector;
import GUI.Utils.RowCreator;
import GUI.Utils.TableModel;

import java.util.Date;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Form extends JFrame {

    private Panel panel1 = new Panel();
    private Panel panel2 = new Panel();
    private Panel panel3 = new Panel();
    private Panel editDB = new Panel();
    private JTable table1;
    private JTable table2;
    private JTable table3;
    private Panel logs = new Panel();
    private JTextArea textArea;
    private Connector connector = new Connector();


    public Form() throws HeadlessException {
        super("sql with thread");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

        textArea = new JTextArea();
        textArea.setEditable(false);
        logs.add(textArea);

        tabbedPane.add("Таблица Node", panel1);
        tabbedPane.add("Таблица Notes", panel2);
        tabbedPane.add("Таблица User", panel3);
        tabbedPane.add("Редактировать базу данных", editDB);
        tabbedPane.add("Логи", logs);

        try {
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initDBChange();

        content.add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(content);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        new Thread(() -> {
            while (true) {
                try {
                    update();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                textArea.append("Последнее обновление данных >> " + new Date().toString() + "\n");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void update() throws SQLException {
        table1.setModel(new TableModel(new RowCreator(connector.getRs("node")).getRows(),
                connector.getHeader("node")));
        table2.setModel(new TableModel(new RowCreator(connector.getRs("notes")).getRows(),
                connector.getHeader("notes")));
        table3.setModel(new TableModel(new RowCreator(connector.getRs("user")).getRows(),
                connector.getHeader("user")));
    }

    private void createTables() throws SQLException {
        Dimension tableSize = new Dimension(500, 300);
        table1 = new JTable(new TableModel(new RowCreator(connector.getRs("node")).getRows(),
                connector.getHeader("node")));
        JScrollPane scrollPane = new JScrollPane(table1);
        table1.setPreferredScrollableViewportSize(tableSize);
        panel1.add(scrollPane);

        table2 = new JTable(new TableModel(new RowCreator(connector.getRs("notes")).getRows(),
                connector.getHeader("notes")));
        scrollPane = new JScrollPane(table2);
        table2.setPreferredScrollableViewportSize(tableSize);
        panel2.add(scrollPane);

        table3 = new JTable(new TableModel(new RowCreator(connector.getRs("user")).getRows(),
                connector.getHeader("user")));
        scrollPane = new JScrollPane(table3);
        table3.setPreferredScrollableViewportSize(tableSize);
        panel3.add(scrollPane);
    }

    private void initDBChange() {
        //вставка в базу
        JPanel grid = new JPanel();
        GridLayout layout = new GridLayout(4, 0, 5, 12);
        grid.setLayout(layout);

        JPanel table1Flow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //лабел сверху
        JLabel insetLabel = new JLabel("Операция вставки:");
        table1Flow.add(insetLabel);
        grid.add(table1Flow);

        //таблица 1
        table1Flow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel tableUser = new JLabel("Таблица user: ");
        table1Flow.add(tableUser);
        JTextField userName = new JTextField(15);
        userName.setToolTipText("Введите имя пользователя");
        table1Flow.add(userName);
        JTextField userAge = new JTextField(5);
        userAge.setToolTipText("Введите возраст ползователя");
        table1Flow.add(userAge);
        JTextField userBirthday = new JTextField(10);
        userBirthday.setToolTipText("Введите дату рождения ГГГГ-ММ-ДД");
        table1Flow.add(userBirthday);
        JButton userInsert = new JButton("Добавить");
        table1Flow.add(userInsert);


        userInsert.addActionListener(l -> {
            if (connector.insertIntoUser(userName.getText(), userAge.getText(), userBirthday.getText()) == 1)
                textArea.append("Данные таблицы user успешно обновлены\n");
            else
                textArea.append("Неудачная вставка в таблицу user. Данные:\n"
                        + userName.getText() + "\n" +
                        userAge.getText() + "\n" +
                        userBirthday.getText() + "\n");
        });

        grid.add(table1Flow);


        //таблица 2
        table1Flow = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel tableNotes = new JLabel("Таблица notes: ");
        table1Flow.add(tableNotes);
        JTextField note = new JTextField(15);
        note.setToolTipText("Введите заметку");
        table1Flow.add(note);
        JTextField priority = new JTextField(5);
        priority.setToolTipText("Введите приоритет");
        table1Flow.add(priority);
        JTextField time = new JTextField(10);
        time.setToolTipText("Введите время HH:MM:SS");
        table1Flow.add(time);
        JButton noteInsert = new JButton("Добавить");
        table1Flow.add(noteInsert);

        noteInsert.addActionListener(l -> {
            if (connector.insertIntoNote(note.getText(), priority.getText(), time.getText()) == 1)
                textArea.append("Данные таблицы note успешно обновлены\n");
            else
                textArea.append("Неудачная вставка в таблицу note. Данные: \n"
                        + note.getText() + "\n"
                        + priority.getText() + "\n"
                        + time.getText() + "\n");
        });

        grid.add(table1Flow);

        //Таблица 3

        table1Flow = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel tableNodes = new JLabel("Таблица node: ");
        table1Flow.add(tableNodes);
        JTextField userId = new JTextField(15);
        note.setToolTipText("Введите id пользователя");
        table1Flow.add(userId);
        JTextField noteId = new JTextField(5);
        priority.setToolTipText("Введите id заметки");
        table1Flow.add(noteId);
        JTextField percent = new JTextField(10);
        time.setToolTipText("Введите процент выполнения");
        table1Flow.add(percent);
        JButton nodeInsert = new JButton("Добавить");
        table1Flow.add(nodeInsert);

        nodeInsert.addActionListener(l -> {
            if (connector.insertIntoNode(userId.getText(), noteId.getText(), percent.getText()) == 1)
                textArea.append("Данные таблицы node успешно обновлены\n");
            else
            textArea.append("Неудачная вставка в таблицу node. Данные: \n"
                    + userId.getText() + "\n"
                    + noteId.getText() + "\n"
                    + percent.getText() + "\n");
        });

        grid.add(table1Flow);
        editDB.add(grid);


    }
}
