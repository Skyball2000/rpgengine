
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class GuiHub extends JFrame {
    private JMenuBar menuBar;
    private final JButton b_add;
    private final JButton b_delete;
    private final JButton b_edit;
    private final JButton b_nextPage;
    private final JButton b_previousPage;
    private final JButton b_refresh;
    private final JLabel l_pane1;
    private final JLabel l_pane2;
    private final JLabel l_pane3;
    private final JLabel l_pane4;
    private final JLabel l_pane5;
    private final JLabel l_pane6;
    private final JLabel l_credits;
    private final JPanel panel1;
    private final JPanel panel2;
    private final JPanel panel3;
    private final JPanel panel4;
    private final JPanel panel5;
    private final JPanel panel6;
    private final JTextArea ta_pane1;
    private final JTextArea ta_pane2;
    private final JTextArea ta_pane3;
    private final JTextArea ta_pane4;
    private final JTextArea ta_pane5;
    private final JTextArea ta_pane6;

    private final Manager manager;
    private final int creditsWidth = 220;
    private int currentScreen = 0;
    private final int maxScreen = 15;
    private final String[] boxNames = new String[maxScreen + 1];

    public GuiHub(Manager manager) {
        int size_x = Math.max(StaticStuff.getScreenWidth() - 500, 912);
        int size_y = Math.max(StaticStuff.getScreenHeight() - 200, 480);
        this.manager = manager;
        this.setTitle(StaticStuff.PROJECT_NAME + " - Main menu - open or create new adventure to start" + (StaticStuff.ee.equals("true") ? " :)" : ""));
        this.setSize(size_x, size_y);
        generateMenu();
        this.setJMenuBar(menuBar);

        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(size_x, size_y));
        setMinimumSize(new Dimension(912, 480));
        contentPane.setBackground(StaticStuff.getColor("background"));
        setIconImage(new ImageIcon("res/img/iconyellow.png").getImage());

        b_add = new JButton();
        b_add.setBounds(601, 848, 90, 35);
        b_add.setBackground(StaticStuff.getColor("buttons"));
        b_add.setForeground(StaticStuff.getColor("text_color"));
        b_add.setEnabled(true);
        b_add.setFont(StaticStuff.getBaseFont());
        b_add.setText("New");
        b_add.setVisible(true);
        b_add.addActionListener(evt -> addEntity());

        b_delete = new JButton();
        b_delete.setBounds(701, 848, 90, 35);
        b_delete.setBackground(StaticStuff.getColor("buttons"));
        b_delete.setForeground(StaticStuff.getColor("text_color"));
        b_delete.setEnabled(true);
        b_delete.setFont(StaticStuff.getBaseFont());
        b_delete.setText("Delete");
        b_delete.setVisible(true);
        b_delete.addActionListener(evt -> deleteEntity());

        b_edit = new JButton();
        b_edit.setBounds(801, 848, 90, 35);
        b_edit.setBackground(StaticStuff.getColor("buttons"));
        b_edit.setForeground(StaticStuff.getColor("text_color"));
        b_edit.setEnabled(true);
        b_edit.setFont(StaticStuff.getBaseFont());
        b_edit.setText("Edit");
        b_edit.setVisible(true);
        b_edit.addActionListener(evt -> editEntity());

        b_nextPage = new JButton();
        b_nextPage.setBounds(1360, 848, 90, 35);
        b_nextPage.setBackground(StaticStuff.getColor("buttons"));
        b_nextPage.setForeground(StaticStuff.getColor("text_color"));
        b_nextPage.setEnabled(true);
        b_nextPage.setFont(StaticStuff.getBaseFont());
        b_nextPage.setText("Next");
        b_nextPage.setVisible(true);
        b_nextPage.addActionListener(evt -> nextScreen());

        b_previousPage = new JButton();
        b_previousPage.setBounds(1260, 848, 90, 35);
        b_previousPage.setBackground(StaticStuff.getColor("buttons"));
        b_previousPage.setForeground(StaticStuff.getColor("text_color"));
        b_previousPage.setEnabled(false);
        b_previousPage.setFont(StaticStuff.getBaseFont());
        b_previousPage.setText("Previous");
        b_previousPage.setVisible(true);
        b_previousPage.addActionListener(evt -> previousScreen());

        b_refresh = new JButton();
        b_refresh.setBounds(1160, 848, 90, 35);
        b_refresh.setBackground(StaticStuff.getColor("buttons"));
        b_refresh.setForeground(StaticStuff.getColor("text_color"));
        b_refresh.setEnabled(true);
        b_refresh.setFont(StaticStuff.getBaseFont());
        b_refresh.setText("Refresh");
        b_refresh.setVisible(true);
        b_refresh.addActionListener(evt -> updateScreen());

        l_credits = new JLabel();
        l_credits.setBounds(20, 848, creditsWidth, 26);
        l_credits.setBackground(StaticStuff.getColor("buttons"));
        l_credits.setForeground(StaticStuff.getColor("text_color"));
        l_credits.setEnabled(true);
        l_credits.setFont(StaticStuff.getBaseFont());
        l_credits.setText("<html><b>" + StaticStuff.PROJECT_NAME + " by Yan Wittmann (v. " + Manager.version + ")");
        l_credits.setVisible(true);

        l_pane1 = createAJLabel();
        l_pane2 = createAJLabel();
        l_pane3 = createAJLabel();
        l_pane4 = createAJLabel();
        l_pane5 = createAJLabel();
        l_pane6 = createAJLabel();

        panel1 = createAJPanel();
        panel2 = createAJPanel();
        panel3 = createAJPanel();
        panel4 = createAJPanel();
        panel5 = createAJPanel();
        panel6 = createAJPanel();

        ta_pane1 = createTaPane();
        ta_pane2 = createTaPane();
        ta_pane3 = createTaPane();
        ta_pane4 = createTaPane();
        ta_pane5 = createTaPane();
        ta_pane6 = createTaPane();

        panel1.add(l_pane1);
        panel2.add(l_pane2);
        panel3.add(l_pane3);
        panel4.add(l_pane4);
        panel5.add(l_pane5);
        panel6.add(l_pane6);

        panel1.add(ta_pane1);
        panel2.add(ta_pane2);
        panel3.add(ta_pane3);
        panel4.add(ta_pane4);
        panel5.add(ta_pane5);
        panel6.add(ta_pane6);

        contentPane.add(panel1);
        contentPane.add(panel2);
        contentPane.add(panel3);
        contentPane.add(panel4);
        contentPane.add(panel5);
        contentPane.add(panel6);

        contentPane.add(l_credits);
        contentPane.add(b_add);
        contentPane.add(b_delete);
        contentPane.add(b_edit);
        contentPane.add(b_nextPage);
        contentPane.add(b_previousPage);
        contentPane.add(b_refresh);

        this.add(contentPane);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);

        boxNames[0] = "Location";
        boxNames[1] = "NPC";
        boxNames[2] = "Item Type";
        boxNames[3] = "Inventory";
        boxNames[4] = "Image";
        boxNames[5] = "Audio";
        boxNames[6] = "Battle Map";
        boxNames[7] = "Talent";
        boxNames[8] = "Loot Table";
        boxNames[9] = "Variable";
        boxNames[10] = "EventCollection";
        boxNames[11] = "CustomCommand";
        boxNames[12] = "Color";
        boxNames[13] = "File";
        boxNames[14] = "Popup";
        updateScreen();

        Action saveAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "SAVE");
        getRootPane().getActionMap().put("SAVE", saveAction);

        Action saveAsAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "SAVEAS");
        getRootPane().getActionMap().put("SAVEAS", saveAsAction);

        Action copyAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                editEntity();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "OPEN");
        getRootPane().getActionMap().put("OPEN", copyAction);

        Action openInPlayerAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                openAdventureInPlayer();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "PLAYINPLAYER");
        getRootPane().getActionMap().put("PLAYINPLAYER", openInPlayerAction);

        Action cloneAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                cloneEntity();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "cloneAction");
        getRootPane().getActionMap().put("cloneAction", cloneAction);

        Action removeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                deleteEntity();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "REMOVE");
        getRootPane().getActionMap().put("REMOVE", removeAction);

        Action addAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                addEntity();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "ADD");
        getRootPane().getActionMap().put("ADD", addAction);

        Action newFileAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                createNew();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "NEWFILE");
        getRootPane().getActionMap().put("NEWFILE", newFileAction);

        Action refreshAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                manager.updateGui();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "REFRESH");
        getRootPane().getActionMap().put("REFRESH", refreshAction);

        Action leftAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                previousScreen();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "LEFT");
        getRootPane().getActionMap().put("LEFT", leftAction);

        Action rightAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                nextScreen();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "RIGHT");
        getRootPane().getActionMap().put("RIGHT", rightAction);

        Action upAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (scrollIndex > 0) scrollIndex -= 2;
                updateScreen();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "UP");
        getRootPane().getActionMap().put("UP", upAction);

        Action downAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                scrollIndex += 2;
                updateScreen();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "DOWN");
        getRootPane().getActionMap().put("DOWN", downAction);

        Action openFileAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                open();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "OPENFILE");
        getRootPane().getActionMap().put("OPENFILE", openFileAction);

        Action findAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                find();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK, false), "FIND");
        getRootPane().getActionMap().put("FIND", findAction);

        Action toggleFullscreenAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                toggleFullscreen();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0, false), "FULLSCREEN");
        getRootPane().getActionMap().put("FULLSCREEN", toggleFullscreenAction);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (manager.unsavedChanges) {
                    int choice = Popup.selectButton(StaticStuff.PROJECT_NAME, "There may be unsaved changes.\nWhat do you want to do?", new String[]{"Save", "Exit", "Cancel"});
                    if (choice == 0) {
                        save();
                        FileManager.clearTmp();
                        System.exit(3);
                    } else if (choice == 1) {
                        FileManager.clearTmp();
                        System.exit(2);
                    }
                } else {
                    FileManager.clearTmp();
                    System.exit(1);
                }
            }
        });

        setee();
        addDropListener(this);

        new Thread(() -> {
            while (true) {
                Sleep.seconds(StaticStuff.randomNumber(30, 120));

                l_credits.setBounds(creditsHoverPosX, creditsHoverPosY, creditsWidth, 29);
                l_credits.setForeground(StaticStuff.getRandomSaturatedColorForCreditsHover());

                Sleep.milliseconds(StaticStuff.randomNumber(600, 2000));

                l_credits.setBounds(creditsUpPosX, creditsUpPosY, creditsWidth, 26);
                l_credits.setForeground(StaticStuff.getColor("text_color"));

                Sleep.milliseconds(StaticStuff.randomNumber(200, 1000));

                l_credits.setBounds(creditsHoverPosX, creditsHoverPosY, creditsWidth, 29);
                l_credits.setForeground(StaticStuff.getRandomSaturatedColorForCreditsHover());

                Sleep.milliseconds(StaticStuff.randomNumber(600, 2000));

                l_credits.setBounds(creditsUpPosX, creditsUpPosY, creditsWidth, 26);
                l_credits.setForeground(StaticStuff.getColor("text_color"));
            }
        }).start();

        addComponentListener(new ComponentAdapter() { // b_add b_delete b_edit b_nextPage b_previousPage b_refresh l_pane1 l_pane2 l_pane3 l_pane4 l_credits panel1 panel2 panel3 panel4 ta_pane1 ta_pane2 ta_pane3 ta_pane4
            @Override
            public void componentResized(ComponentEvent e) {
                resizeWindow(e);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }
        });

        resizeWindow(null);
    }

    private void resizeWindow(ComponentEvent e) {
        int width;
        int height;
        if (e == null) {
            width = getWidth();
            height = getHeight();
        } else {
            width = e.getComponent().getWidth();
            height = e.getComponent().getHeight();
        }
        resizeWindow(width, height);
    }

    private void resizeWindow(int width, int height) {
        //get width and height (original: 1494,891)

        //initialise variables
        int mainPanelDist = 11, mainPanelMinWidth = 280, mainPanelWidth, mainPanelHeight = height - 132, mainPanelAmount, middle = width / 2;

        //calculate main panel data
        int widthWithoutMainPanelDist = width - ((width / mainPanelMinWidth) * mainPanelDist);
        mainPanelAmount = Math.min((widthWithoutMainPanelDist / mainPanelMinWidth), 6);
        widthWithoutMainPanelDist = width - ((mainPanelAmount + 1) * mainPanelDist);
        mainPanelWidth = (widthWithoutMainPanelDist / mainPanelAmount) - 3;

        //set components size and visibility
        b_nextPage.setLocation(width - 130, height - 105);
        b_previousPage.setLocation(width - 230, height - 105);
        b_refresh.setLocation(width - 330, height - 105);

        b_add.setLocation(middle - 180, height - 105);
        b_delete.setLocation(middle - 80, height - 105);
        b_edit.setLocation(middle + 20, height - 105);

        creditsDefaultPosY = height - 113;
        creditsHoverPosY = creditsDefaultPosY + 5;
        creditsUpPosY = creditsDefaultPosY + 8;
        l_credits.setLocation(creditsUpPosX, creditsUpPosY);

        panel1.setBounds(10, 16, mainPanelWidth, mainPanelHeight);
        panel2.setBounds(10 + mainPanelDist + mainPanelWidth, 16, mainPanelWidth, mainPanelHeight);
        panel3.setBounds(10 + ((mainPanelDist + mainPanelWidth) * 2), 16, mainPanelWidth, mainPanelHeight);
        panel4.setBounds(10 + ((mainPanelDist + mainPanelWidth) * 3), 16, mainPanelWidth, mainPanelHeight);
        panel5.setBounds(10 + ((mainPanelDist + mainPanelWidth) * 4), 16, mainPanelWidth, mainPanelHeight);
        panel6.setBounds(10 + ((mainPanelDist + mainPanelWidth) * 5), 16, mainPanelWidth, mainPanelHeight);

        ta_pane1.setSize(mainPanelWidth - 12, mainPanelHeight - 41);
        ta_pane2.setSize(mainPanelWidth - 12, mainPanelHeight - 41);
        ta_pane3.setSize(mainPanelWidth - 12, mainPanelHeight - 41);
        ta_pane4.setSize(mainPanelWidth - 12, mainPanelHeight - 41);
        ta_pane5.setSize(mainPanelWidth - 12, mainPanelHeight - 41);
        ta_pane6.setSize(mainPanelWidth - 12, mainPanelHeight - 41);

        setPanelVisible(mainPanelAmount);

        updateScreen();
    }

    private boolean isFullscreen = false;

    private void toggleFullscreen() {
        dispose();
        if (isFullscreen) {
            setExtendedState(JFrame.NORMAL);
            setUndecorated(false);
            setBounds(0, 0, 1494, 891);
            setLocationRelativeTo(null);
            resizeWindow(1494, 891);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
        }
        isFullscreen = !isFullscreen;
        setVisible(true);
    }

    private final HashMap<JTextArea, Boolean> isTextAreaFocused = new HashMap<>();

    private JTextArea createTaPane() {
        JTextArea ta_pane = new JTextArea();
        ta_pane.setBounds(6, 34, 348, 780);
        ta_pane.setBackground(StaticStuff.getColor("text_background"));
        ta_pane.setForeground(StaticStuff.getColor("text_color"));
        ta_pane.setEnabled(true);
        ta_pane.setFont(StaticStuff.getBaseFont());
        ta_pane.setText("");
        ta_pane.setBorder(BorderFactory.createBevelBorder(1));
        ta_pane.setLineWrap(true);
        ta_pane.setVisible(true);
        ta_pane.addMouseWheelListener(this::scroll);
        isTextAreaFocused.put(ta_pane, false);
        ta_pane.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                isTextAreaFocused.replace(ta_pane, true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                isTextAreaFocused.replace(ta_pane, false);
            }
        });
        return ta_pane;
    }

    private JPanel createAJPanel() {
        JPanel panel = new JPanel(null);
        panel.setBorder(BorderFactory.createEtchedBorder(1));
        panel.setBounds(1123, 16, 360, 821);
        panel.setBackground(StaticStuff.getColor("buttons"));
        panel.setForeground(StaticStuff.getColor("text_color"));
        panel.setEnabled(true);
        panel.setFont(StaticStuff.getBaseFont());
        panel.setVisible(true);
        return panel;
    }

    private JLabel createAJLabel() {
        JLabel l_pane = new JLabel();
        l_pane.setBounds(18, 8, 140, 20);
        l_pane.setBackground(StaticStuff.getColor("buttons"));
        l_pane.setForeground(StaticStuff.getColor("text_color"));
        l_pane.setEnabled(true);
        l_pane.setFont(StaticStuff.getBaseFont());
        l_pane.setText("<html><b>Pls wait working on it");
        l_pane.setVisible(true);
        return l_pane;
    }

    private int amountPanelsVisible = 4;

    private void setPanelVisible(int amount) {
        amountPanelsVisible = amount;
        if (amount == 1) {
            panel1.setVisible(true);
            panel2.setVisible(false);
            panel3.setVisible(false);
            panel4.setVisible(false);
            panel5.setVisible(false);
            panel6.setVisible(false);
        } else if (amount == 2) {
            panel1.setVisible(true);
            panel2.setVisible(true);
            panel3.setVisible(false);
            panel4.setVisible(false);
            panel5.setVisible(false);
            panel6.setVisible(false);
        } else if (amount == 3) {
            panel1.setVisible(true);
            panel2.setVisible(true);
            panel3.setVisible(true);
            panel4.setVisible(false);
            panel5.setVisible(false);
            panel6.setVisible(false);
        } else if (amount == 4) {
            panel1.setVisible(true);
            panel2.setVisible(true);
            panel3.setVisible(true);
            panel4.setVisible(true);
            panel5.setVisible(false);
            panel6.setVisible(false);
        } else if (amount == 5) {
            panel1.setVisible(true);
            panel2.setVisible(true);
            panel3.setVisible(true);
            panel4.setVisible(true);
            panel5.setVisible(true);
            panel6.setVisible(false);
        } else if (amount == 6) {
            panel1.setVisible(true);
            panel2.setVisible(true);
            panel3.setVisible(true);
            panel4.setVisible(true);
            panel5.setVisible(true);
            panel6.setVisible(true);
        }
    }

    //thank you rustyx for this code (https://stackoverflow.com/questions/811248/how-can-i-use-drag-and-drop-in-swing-to-get-file-path):
    private void addDropListener(Component c) {
        c.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        dropFile(file.getAbsolutePath());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void dropFile(String path) {
        if (!(filename == null))
            if (path.matches(".+\\.adv")) {
                String filename = path.replaceAll("([^\\\\]+\\\\)*", "");
                FileManager.copyFile(path, "../adventures/" + filename);
                Popup.message(StaticStuff.PROJECT_NAME, "Copied file into the adventures-folder!");
                setFilename(filename);
                manager.openFile(filename.replace("" + StaticStuff.ADVENTURE_FILE_ENDING, ""));
            } else if (StaticStuff.isImageFile(path)) {
                String name = Popup.input("Name of the image:", path.replaceAll("(?:.*)\\\\(?:.+\\\\)*(.+)\\.(?:.+)$", "$1"));
                if (name == null) return;
                if (name.equals("")) return;
                manager.newImage(path, name);
            } else if (StaticStuff.isAudioFile(path)) {
                String name = Popup.input("Name of the audio:", path.replaceAll("(?:.*)\\\\(?:.+\\\\)*(.+)\\.(?:.+)$", "$1"));
                if (name == null) return;
                if (name.equals("")) return;
                manager.newAudio(path, name);
            }
    }

    int scrollIndex = 0;

    private void scroll(MouseWheelEvent evt) {
        if ((evt.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == 0) { //ctrl not down
            if (evt.getUnitsToScroll() < 0) {
                if (scrollIndex > 0) scrollIndex -= 2;
            } else
                scrollIndex += 2;
            updateScreen();
        } else { //ctrl down
            if (evt.getUnitsToScroll() < 0) {
                previousScreen();
            } else
                nextScreen();
        }
    }

    private String getPlural(String text) {
        if (text == null) return null;
        if (text.equals("Inventory")) text = "Inventories";
        else text = text + "s";
        return text;
    }

    public void updateTextAreas(String[] text) {
        ta_pane1.setText("");
        ta_pane2.setText("");
        ta_pane3.setText("");
        ta_pane4.setText("");
        ta_pane5.setText("");
        ta_pane6.setText("");
        StringBuilder displayString;
        String[] splitString;
        try {
            for (int i = 0; i < 6; i++) {
                if (scrollIndex > 0) {
                    splitString = text[currentScreen + i].split("\n");
                    displayString = new StringBuilder(splitString[0]);
                    for (int j = scrollIndex + 1; j < splitString.length; j++) {
                        displayString.append("\n").append(splitString[j]);
                    }
                } else displayString = new StringBuilder(text[currentScreen + i]);
                if (i == 0) ta_pane1.setText(displayString.toString());
                else if (i == 1) ta_pane2.setText(displayString.toString());
                else if (i == 2) ta_pane3.setText(displayString.toString());
                else if (i == 3) ta_pane4.setText(displayString.toString());
                else if (i == 4) ta_pane5.setText(displayString.toString());
                else ta_pane6.setText(displayString.toString());
            }
        } catch (Exception ignored) {
        }
    }

    private void addEntity() {
        if (Manager.filename.equals("")) return;
        String type = Popup.dropDown(StaticStuff.PROJECT_NAME, "Choose an entity to add:", boxNames);
        if (type == null) return;
        if (type.equals("")) return;
        manager.newEntity(type);
        updateScreen();
    }

    private String getSelectedText() {
        String s = getSelectedTextInJTextArea(ta_pane1);
        if (s != null) return s;
        s = getSelectedTextInJTextArea(ta_pane2);
        if (s != null) return s;
        s = getSelectedTextInJTextArea(ta_pane3);
        if (s != null) return s;
        s = getSelectedTextInJTextArea(ta_pane4);
        if (s != null) return s;
        s = getSelectedTextInJTextArea(ta_pane5);
        if (s != null) return s;
        s = getSelectedTextInJTextArea(ta_pane6);
        if (s != null) return s;
        return "";
    }

    private String getSelectedTextInJTextArea(JTextArea textArea) {
        if (textArea.isFocusOwner() && isTextAreaFocused.get(textArea)) {
            if (textArea.getSelectedText() != null) return textArea.getSelectedText();
            return StaticStuff.getWordAtIndex(textArea.getText(), textArea.getCaretPosition());
        }
        return null;
    }

    private String getSelectedUID(String message) {
        String selectedText = getSelectedText().replaceAll("(?:.+)?(?: |\\b)([a-z0-9]{16})(?: |\\b)(?:.+)?", "$1");
        if (StaticStuff.isValidUIDSilent(selectedText))
            return selectedText;
        selectedText = StaticStuff.autoDetectUID(message);
        if (selectedText != null) {
            selectedText = selectedText.replaceAll("(?:.+)?(?: |\\b)([a-z0-9]{16})(?: |\\b)(?:.+)?", "$1");
            if (StaticStuff.isValidUIDSilent(selectedText))
                return selectedText;
        }
        return "";
    }

    private String lastTimeVersionUsed = "";

    private void openAdventureInPlayer() {
        if (Manager.filename.equals("")) return;
        if (!FileManager.fileExists("../../play/versions.txt")) {
            Popup.error("Error", "Cannot find versions folder.");
            return;
        }
        String[] availableVersions = FileManager.getDirs("../../play");
        lastTimeVersionUsed = Popup.dropDown("Version selection", "What version do you want to use to open the adventure?\nThis is a 1.11.1+ feature!", availableVersions, lastTimeVersionUsed);
        if (!FileManager.fileExists("../../play/" + lastTimeVersionUsed + "/play.jar")) {
            Popup.error("Error", "Cannot find version jar.");
            return;
        }
        FileManager.openJar("../../play/" + lastTimeVersionUsed + "/play.jar", "../../play/" + lastTimeVersionUsed + "/", new String[]{"filename:" + Manager.filename});

    }

    private void deleteEntity() {
        if (Manager.filename.equals("")) return;
        manager.deleteEntity(getSelectedUID("Enter the UID to delete:"));
    }

    private void editEntity() {
        if (Manager.filename.equals("")) return;
        manager.openEntity(getSelectedUID("Enter the UID to open:"));
    }

    private void cloneEntity() {
        if (Manager.filename.equals("")) return;
        manager.cloneEntity(getSelectedUID("Enter the UID to clone:"));
    }

    public void nextScreen() {
        b_previousPage.setEnabled(true);
        b_nextPage.setEnabled(true);
        if (currentScreen < maxScreen - amountPanelsVisible)
            currentScreen++;
        if (currentScreen == maxScreen - amountPanelsVisible)
            b_nextPage.setEnabled(false);
        updateScreen();
    }

    public void previousScreen() {
        b_previousPage.setEnabled(true);
        b_nextPage.setEnabled(true);
        if (currentScreen > 0)
            currentScreen--;
        if (currentScreen == 0)
            b_previousPage.setEnabled(false);
        updateScreen();
    }

    String l_pane1Text, l_pane2Text, l_pane3Text, l_pane4Text, l_pane5Text, l_pane6Text;

    private void updateScreen() {
        if (currentScreen > maxScreen - amountPanelsVisible)
            currentScreen = maxScreen - amountPanelsVisible;
        try {
            try {
                manager.updateGui();
            } catch (Exception ignored) {
            }
            l_pane1Text = getPlural(boxNames[currentScreen]);
            l_pane2Text = getPlural(boxNames[currentScreen + 1]);
            if (amountPanelsVisible >= 3) l_pane3Text = getPlural(boxNames[currentScreen + 2]);
            else l_pane3Text = "";
            if (amountPanelsVisible >= 4) l_pane4Text = getPlural(boxNames[currentScreen + 3]);
            else l_pane4Text = "";
            if (amountPanelsVisible >= 5) l_pane5Text = getPlural(boxNames[currentScreen + 4]);
            else l_pane5Text = "";
            if (amountPanelsVisible >= 6) l_pane6Text = getPlural(boxNames[currentScreen + 5]);
            else l_pane6Text = "";
            l_pane1.setText("<html><b>" + l_pane1Text);
            l_pane2.setText("<html><b>" + l_pane2Text);
            l_pane3.setText("<html><b>" + l_pane3Text);
            l_pane4.setText("<html><b>" + l_pane4Text);
            l_pane5.setText("<html><b>" + l_pane5Text);
            l_pane6.setText("<html><b>" + l_pane6Text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String filename = "";

    public void setFilename(String text) {
        filename = text;
        setTitle(StaticStuff.PROJECT_NAME + " - Main menu - " + text.replace("" + StaticStuff.ADVENTURE_FILE_ENDING, "") + (StaticStuff.ee.equals("true") ? " :)" : ""));
    }

    public void generateMenu() {
        UIManager.put("MenuItem.background", StaticStuff.getColor("menu_item_background"));
        UIManager.put("MenuItem.foreground", StaticStuff.getColor("text_color"));
        UIManager.put("MenuBar.background", StaticStuff.getColor("menu_background"));
        UIManager.put("MenuBar.foreground", StaticStuff.getColor("text_color"));
        UIManager.put("Menu.background", StaticStuff.getColor("menu_background"));
        UIManager.put("Menu.foreground", StaticStuff.getColor("text_color"));

        menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu properties = new JMenu("Adventure");
        JMenu other = new JMenu("Other");

        JMenuItem createNew = new JMenuItem("New   ");
        JMenuItem open = new JMenuItem("Open   ");
        JMenuItem save = new JMenuItem("Save   ");
        JMenuItem saveAs = new JMenuItem("Save as   ");
        JMenuItem delete = new JMenuItem("Delete   ");

        JMenuItem selectStylesheet = new JMenuItem("Set stylesheet   ");
        JMenuItem toggleActionEditor = new JMenuItem("Toggle open actions directly in editor   ");
        JMenuItem createVis = new JMenuItem("Create a VisJs graph   ");
        JMenuItem openAdventureInPlayer = new JMenuItem("Open current adventure in player   ");
        JMenuItem shortcuts = new JMenuItem("Shortcuts   ");
        JMenuItem documentation = new JMenuItem("Documentation   ");
        JMenuItem website = new JMenuItem("Website   ");
        JMenuItem showTips = new JMenuItem("Toggle show tips on startup   ");

        JMenuItem project = new JMenuItem("Project   ");
        JMenuItem player = new JMenuItem("Player   ");
        JMenuItem find = new JMenuItem("Find   ");
        JMenuItem refactor = new JMenuItem("Refactor   ");
        JMenuItem renameUIDs = new JMenuItem("Auto-rename UIDs   ");

        createNew.addActionListener(evt -> createNew());

        open.addActionListener(evt -> open());

        save.addActionListener(evt -> save());

        saveAs.addActionListener(evt -> saveAs());

        delete.addActionListener(evt -> deleteAdventure());

        shortcuts.addActionListener(evt -> Popup.message(StaticStuff.PROJECT_NAME, "These are the shortcuts you can use on the main menu:\nctrl+shift+n: New adventure\nctrl+n: New entity\nctrl+e: Edit entity\nctrl+k: Clone entity\nctrl+d: Delete entity\nctrl+r: Reload\nctrl+q: Scroll left\nctrl+w: Scroll right\nctrl+shift+q: Scroll up\nctrl+shift+w: Scroll down\nctrl+s: Save\nctrl+shift+s: Save as\nctrl+o: Open File\nctrl+shift+o: Open Adventure in player\nctrl+f: Find text\nF11: Toggle fullscreen\nUse drag and drop to add adventures, images or audio files"));

        documentation.addActionListener(evt -> StaticStuff.openURL("http://yanwittmann.de/projects/rpgengine/documentation/"));

        website.addActionListener(evt -> StaticStuff.openURL("http://yanwittmann.de/projects/rpgengine/site/"));

        showTips.addActionListener(evt -> toggleShowTips());

        selectStylesheet.addActionListener(evt -> selectStylesheet());

        toggleActionEditor.addActionListener(evt -> toggleActionEditor());

        createVis.addActionListener(evt -> createVis());

        project.addActionListener(evt -> projectSettings());

        player.addActionListener(evt -> playerSettings());

        find.addActionListener(evt -> find());

        refactor.addActionListener(evt -> refactor());

        renameUIDs.addActionListener(evt -> autoRenameUIDs());

        openAdventureInPlayer.addActionListener(evt -> openAdventureInPlayer());

        file.add(createNew);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.add(delete);
        menuBar.add(file);

        properties.add(player);
        properties.add(project);
        properties.add(find);
        properties.add(refactor);
        properties.add(renameUIDs);
        menuBar.add(properties);

        other.add(selectStylesheet);
        other.add(toggleActionEditor);
        other.add(createVis);
        other.add(openAdventureInPlayer);
        other.add(shortcuts);
        other.add(documentation);
        other.add(website);
        other.add(showTips);
        menuBar.add(other);
    }

    private boolean isReady() {
        return filename.length() > 0;
    }

    private final static String[] VIS_CREATE_TYPES = new String[]{
            "Connections between objects based on UIDs and names",
            "Connections between objects based on UIDs and names by type",
            "Objects with their types"
    };
    private final static String[] VIS_CREATE_TYPES_IDENTIFIERS = new String[]{
            "uidNameConnections",
            "uidNameConnectionsByType",
            "objectsByTypes"
    };
    private void createVis() {
        if(!isReady()) return;
        String type = Popup.dropDown(StaticStuff.PROJECT_NAME, "What type of graph to you want to generate?", VIS_CREATE_TYPES);
        if(type == null || type.length() == 0) return;
        IntStream.range(0, VIS_CREATE_TYPES.length).filter(i -> VIS_CREATE_TYPES[i].equals(type)).findFirst().ifPresent(i -> manager.generateVis(VIS_CREATE_TYPES_IDENTIFIERS[i]));
    }

    private void selectStylesheet() {
        StaticStuff.selectColorScheme();
        manager.setMainCfgSetting("stylesheet", StaticStuff.getColorScheme());
        int result = 1;
        if (!this.filename.equals(""))
            result = Popup.selectButton(StaticStuff.PROJECT_NAME, "The engine will restart. Do you want to save your adventure?", new String[]{"Yes!", "No"});
        if (result == 0) save();
        FileManager.openFile("create.jar");
        System.exit(0);
    }

    private void toggleActionEditor() {
        Manager.toggleActionEditor();
    }

    private void toggleShowTips() {
        Manager.toggleShowTips();
    }

    private boolean refactorUIDwarningShow = true;
    public static final String REFACTOR_UID_WARNING_MESSAGE = "It seems like you try to replace a UID with a string that does not match the pattern of a UID.\n" +
            "If you replace a UID with a string that is not a UID, you cannot adress the object any more (meaning that you can't edit or delete the object).\n" +
            "Do you know what you are doing?";

    private void refactor() {
        if(!isReady()) return;
        String find = Popup.input("Find:", "");
        if (find == null) return;
        if (find.equals("")) return;
        String replace = Popup.input("Replace:", "");
        if (replace == null) return;
        if (replace.equals("")) return;
        if (StaticStuff.isValidUIDSilent(find) && !StaticStuff.isValidUIDSilent(replace) && refactorUIDwarningShow) {
            int answer = Popup.selectButton(StaticStuff.PROJECT_NAME, REFACTOR_UID_WARNING_MESSAGE, new String[]{"Yes", "No", "Yes, don't ask me again"});
            if (answer == 1 || answer == -1) return;
            else if (answer == 2) refactorUIDwarningShow = false;
        }
        int occ = manager.refactor(find, replace);
        updateScreen();
        Popup.message(StaticStuff.PROJECT_NAME, "Replaced " + occ + " occurrences");
    }

    private static final String AUTO_RENAME_UIDS_WARNING_MESSAGE = "The UIDs in this adventure will be renamed using this format:\n" +
            "Example: house00000000loc\n" +
            "Do NOT perform this action if your adventure has multiple objects with the same type and name!\n" +
            "Type 'rename' to confirm this action:";

    private void autoRenameUIDs() {
        if(!isReady()) return;
        String confirm = Popup.input(AUTO_RENAME_UIDS_WARNING_MESSAGE, "");
        if (confirm == null || !confirm.equals("rename")) return;
        manager.autoRenameUIDs();
        manager.updateGui();
    }

    private void find() {
        if(!isReady()) return;
        String find = Popup.input("Enter the string to find in the adventure:", "");
        if (find == null) return;
        if (find.equals("")) return;
        ArrayList<String> found = manager.find(find);
        new GuiShowText("Search results for '" + find + "':", found.toArray(new String[0]), manager);
    }

    private void createNew() {
        new GuiCreateNewAdventure(manager);
    }

    private void deleteAdventure() {
        String[] files = FileManager.getFilesWithEnding(Manager.pathExtension + "adventures/", StaticStuff.ADVENTURE_FILE_ENDING_NO_DOT);
        if (files.length > 0) {
            String filename = Popup.dropDown(StaticStuff.PROJECT_NAME, "What file do you want to delete?", files);
            if (!(filename == null))
                if (!(filename.length() == 0)) FileManager.deleteFile(Manager.pathExtension + "adventures/" + filename);
        } else {
            Popup.message(StaticStuff.PROJECT_NAME, "There are no adventures.\nClick on 'new' to create a new adventure.");
        }
    }

    private void open() {
        String[] files = FileManager.getFilesWithEnding(Manager.pathExtension + "adventures/", StaticStuff.ADVENTURE_FILE_ENDING_NO_DOT);
        if (files.length > 0) {
            String filename = Popup.dropDown(StaticStuff.PROJECT_NAME, "What file do you want to open?", files);
            if (!(filename == null)) {
                setFilename(filename);
                manager.openFile(filename.replace("" + StaticStuff.ADVENTURE_FILE_ENDING, ""));
            }
        } else {
            Popup.message(StaticStuff.PROJECT_NAME, "There are no adventures.\nClick on 'new' to create a new adventure.");
        }
    }

    private void save() {
        manager.save();
    }

    private void saveAs() {
        int result = 1;
        if (!this.filename.equals(""))
            result = Popup.selectButton(StaticStuff.PROJECT_NAME, "You have already opened a file.\nDo you want to save this first?", new String[]{"Yes!", "No"});
        if (result == 0) save();
        String name = Popup.input("Enter a name for the adventure:", StaticStuff.generateRandomMessageFromFile("res/txt/filename" + StaticStuff.DATA_FILE_ENDING));
        if (name == null) return;
        if (name.equals("")) return;
        setFilename(name);
        manager.setFilename(name);
        manager.save();
    }

    private void playerSettings() {
        if(!isReady()) return;
        Manager.player.openSettings();
    }

    private void projectSettings() {
        if(!isReady()) return;
        Manager.projectSettings.openSettings();
    }

    private final int creditsHoverPosX = 20;
    private final int creditsUpPosX = 20;
    private final int creditsDefaultPosX = 20;
    private int creditsHoverPosY = 845;
    private int creditsUpPosY = 848;
    private int creditsDefaultPosY = 840;

    private void setee() {
        l_credits.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                l_credits.setBounds(creditsHoverPosX, creditsHoverPosY, creditsWidth, 29);
            }

            public void mousePressed(MouseEvent e) {
                l_credits.setBounds(creditsDefaultPosX, creditsDefaultPosY, creditsWidth, 34);
                l_pane1.setForeground(l_credits.getForeground());
                l_pane2.setForeground(l_credits.getForeground());
                l_pane3.setForeground(l_credits.getForeground());
                l_pane4.setForeground(l_credits.getForeground());
                l_pane5.setForeground(l_credits.getForeground());
                l_pane6.setForeground(l_credits.getForeground());
            }

            public void mouseExited(MouseEvent e) {
                l_credits.setBounds(creditsUpPosX, creditsUpPosY, creditsWidth, 26);
                l_credits.setForeground(StaticStuff.getColor("text_color"));
            }

            public void mouseEntered(MouseEvent e) {
                l_credits.setBounds(creditsHoverPosX, creditsHoverPosY, creditsWidth, 29);
                l_credits.setForeground(StaticStuff.getRandomSaturatedColorForCreditsHover());
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        l_pane1.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
                l_pane1.setText("<html><b>" + l_pane1Text);
            }

            public void mouseEntered(MouseEvent e) {
                l_pane1.setText("<html><b>" + StaticStuff.shuffle(l_pane1Text));
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        l_pane2.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
                l_pane2.setText("<html><b>" + l_pane2Text);
                l_pane2.setBounds(18, 8, 90, 20);
            }

            public void mouseEntered(MouseEvent e) {
                l_pane2.setText("<html><b>" + StaticStuff.makeWithSpace(l_pane2Text));
                l_pane2.setBounds(18, 8, 250, 20);
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        l_pane3.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
                l_pane3.setText("<html><b>" + l_pane3Text);
            }

            public void mouseEntered(MouseEvent e) {
                l_pane3.setText(l_pane3Text);
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        l_pane4.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
                l_pane4.setText("<html><b>" + l_pane4Text);
            }

            public void mouseEntered(MouseEvent e) {
                l_pane4.setText("<html><b>" + l_pane4Text.toUpperCase());
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        l_pane5.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
                l_pane5.setText("<html><b>" + l_pane5Text);
            }

            public void mouseEntered(MouseEvent e) {
                l_pane5.setText("<html><b>" + l_pane5Text.length());
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
        l_pane6.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
                l_pane6.setText("<html><b>" + l_pane6Text);
            }

            public void mouseEntered(MouseEvent e) {
                l_pane6.setText("<html><u><b>" + l_pane6Text.toUpperCase());
            }

            public void mouseClicked(MouseEvent e) {
            }
        });
    }

}