// import java.awt.event.KeyListener;
// import java.awt.event.KeyEvent;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;

import javax.swing.JCheckBoxMenuItem;
// import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
// import javax.swing.border.Border;

/**
 * JavaNotepad
 */
public class JavaNotepad extends JFrame
        implements CaretListener, ActionListener {

    private static final long serialVersionUID = 7396578183827256967L;
    JTextArea area;
    JLabel status;
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, viewMenu;
    JMenuItem saveMenuItem, openMenuItem, newMenuItem, newWinMenuItem, exitMenuItem, undoMenuItem, redoMenuItem;
    JMenuItem zoomMenuItem, statusMenuItem;
    JMenu fontMenu;
    JCheckBoxMenuItem fontMenuItem;
    JScrollPane scrollPane;
    JPanel topPanel;

    JavaNotepad() {

        setVisible(true);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setTitle("Java-Notepad");
        setLayout(new BorderLayout());
        topPanel = new JPanel(new GridLayout(2, 1));

        menuBarred();
        // add(topPanel, BorderLayout.NORTH);
        // textAreaMethod();
        status = new JLabel("Line 11, Col 45    ", JLabel.RIGHT);
        add(status, BorderLayout.SOUTH);
    }

    private void textAreaMethod() {
        area = new JTextArea();
        // area.setFont(new Font("Consolas", Font.LAYOUT_NO_LIMIT_CONTEXT, 14));
        // area.setFont(new Font("Consolas", Font.BOLD, 14));
        area.setFont(new Font("Consolas", Font.HANGING_BASELINE, 14));
        // area.setColumns(50);
        // area.setLineWrap(true);
        // area.setWrapStyleWord(true);
        area.setTabSize(4);
        area.addCaretListener(this);

        add(area, BorderLayout.CENTER);

        scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
    }

    private void menuBarred() {
        JMenuBar menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        newMenuItem = new JMenuItem("New File");
        // newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke("control N"));
        newMenuItem.addActionListener(this);

        newWinMenuItem = new JMenuItem("New Window");
        // newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
        newWinMenuItem.setAccelerator(KeyStroke.getKeyStroke("control shift N"));
        newWinMenuItem.addActionListener(this);

        openMenuItem = new JMenuItem("Open File");
        saveMenuItem = new JMenuItem("Save File");
        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(newMenuItem);
        fileMenu.add(newWinMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(openMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        editMenu = new JMenu("Edit");
        undoMenuItem = new JMenuItem("Undo    Ctrl+Z");
        redoMenuItem = new JMenuItem("Redo    Ctrl+Y");
        editMenu.add(undoMenuItem);
        editMenu.add(redoMenuItem);
        editMenu.addSeparator();

        viewMenu = new JMenu("View");
        zoomMenuItem = new JMenuItem("Zoom");
        statusMenuItem = new JMenuItem("Status Bar");
        fontMenu = new JMenu("Change Font");
        fontMenuItem = new JCheckBoxMenuItem("Consolas", true);
        fontMenu.add(fontMenuItem);
        viewMenu.add(zoomMenuItem);
        viewMenu.add(statusMenuItem);
        viewMenu.add(fontMenu);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        // topPanel.add(menuBar);
        add(menuBar, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        // tabbedPane.addTab("Tab0", new JLabel("Tab0"));
        // tabbedPane.addTab("Tab1", null, new JTextArea(), "Tab2");
 // In this case the custom component is responsible for rendering the
 // title of the tab.
        // tabbedPane.addTab(null, new JLabel("Tab1"));
        // JComponent panel4 = makeTextPanel(
        // "Panel #4 (has a preferred size of 410 x 50).");
        JPanel panel4 = new JPanel(new BorderLayout());
        area = new JTextArea();
        area.setFont(new Font("Consolas", Font.HANGING_BASELINE, 14));
        area.setTabSize(4);
        area.addCaretListener(this);
        panel4.add(area, BorderLayout.CENTER);
        // panel4.setPreferredSize(new Dimension(410, 50));
        tabbedPane.addTab("Tab 0", null, panel4, "Tab 0");
        // tabbedPane.addTab("Tab 1", null, panel4, "Tab 1");
        // tabbedPane.addTab("Tab 2", null, panel4, "Tab 2");
        // tabbedPane.addTab("Tab 3", null, panel4, "Tab 3");
        // tabbedPane.setTabComponentAt(0, new JLabel("Tab2"));
        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new ImplementRunnable());
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        int line = 1, col = 1;
        try {
            // First we find the position of the caret. This is the number of where the caret is in relation to the start of the JTextArea
                    // in the upper left corner. We use this position to find offset values (eg what line we are on for the given position as well as
                    // what position that line starts on.
                    int caretpos = area.getCaretPosition();
                    line = area.getLineOfOffset(caretpos);

                    // We subtract the offset of where our line starts from the overall caret position.
                    // So lets say that we are on line 5 and that line starts at caret position 100, if our caret position is currently 106
                    // we know that we must be on column 6 of line 5.
                    col = caretpos - area.getLineStartOffset(line);

                    // We have to add one here because line numbers start at 0 for getLineOfOffset and we want it to start at 1 for display.
                    line += 1;
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Error in JavaNotepadCaretListener : " + e);
        }
        if (ce.getSource() == area) {
            status.setText("Ln " + line + "    Col " + col + "    ");
        }

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == newMenuItem) {
            System.out.println("new menu is working");
        }
        if(e.getSource() == newWinMenuItem) {
            System.out.println("New Window opened");
            java.awt.EventQueue.invokeLater(new ImplementRunnable());
            this.dispose();
        }

    }
}