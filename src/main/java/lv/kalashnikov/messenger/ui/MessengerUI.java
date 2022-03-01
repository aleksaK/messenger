package lv.kalashnikov.messenger.ui;

import lv.kalashnikov.messenger.utils.BoxLayoutUtils;
import lv.kalashnikov.messenger.core.Messenger;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;

public class MessengerUI {

    private JTextArea text;
    private final Messenger messenger;

    public MessengerUI(Messenger messenger) {
        this.messenger = messenger;
        createGUI();
    }

    void createGUI() {

        //Внешний вид Unix
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException |
                InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Messenger");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = BoxLayoutUtils.createVerticalPanel();

        JLabel userName = new JLabel(messenger.getUserName());
        Font normalFont = new Font("serif", Font.BOLD, 15);
        userName.setForeground(Color.RED);
        userName.setFont(normalFont);

        JTextArea chat = new JTextArea(23, 35);
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
        chat.setEditable(false);
        DefaultCaret caret = (DefaultCaret) chat.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        text = new JTextArea(3, 35);
        text.addKeyListener(new Enter());
        text.setMaximumSize(new Dimension(320, 50));
        text.setMinimumSize(new Dimension(320, 50));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);

        JScrollPane scrollerChat = new JScrollPane(chat);
        scrollerChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollerChat.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollerChat.setMaximumSize(new Dimension(600, 407));
        scrollerChat.setMinimumSize(new Dimension(320, 100));

        JScrollPane scrollerText = new JScrollPane(text);
        scrollerText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollerText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollerText.setMaximumSize(new Dimension(600, 60));
        scrollerText.setMinimumSize(new Dimension(320, 60));

        JButton buttonSend = new JButton("SEND");
        buttonSend.addActionListener(new SendButton());
        Font bidFont = new Font("serif", Font.BOLD, 15);
        buttonSend.setFont(bidFont);
        buttonSend.setFocusable(false);

        main.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        main.add(userName);
        main.add(BoxLayoutUtils.createVerticalStrut(12));
        main.add(scrollerChat);
        main.add(BoxLayoutUtils.createVerticalStrut(12));
        main.add(scrollerText);

        frame.getContentPane().add(BorderLayout.CENTER, main);
        frame.getContentPane().add(BorderLayout.SOUTH, buttonSend);

        BoxLayoutUtils.setGroupAlignmentX(0.5f, main,
                userName, chat, text, buttonSend);

        frame.pack();
        frame.setVisible(true);
        text.requestFocus();

        while (true) {
            chat.append(messenger.receiveMessage() + "\n");
        }

    }

    private class SendButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!text.getText().equals("")) {
                messenger.sendMessage(text.getText());
                text.setText("");
            }
            text.requestFocus();
        }
    }

    private class Enter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER
                    && !text.getText().equals("")) {
                messenger.sendMessage(text.getText());
                text.setText("");
                e.consume();
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER
                    && text.getText().equals(""))
                e.consume();
        }

    }

}