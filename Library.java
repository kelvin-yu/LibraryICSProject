import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


public class Library extends JFrame implements ActionListener{
	
	final String[] columnNames = {"Title", "Author (First)", "Author (Last)", "Resource", "Genre", "ISBN"};
	
	JPanel leftPanel, rightPanel;
	JTextField titleField, authorLastField, authorFirstField, ISBNField;
	JComboBox<String> resourceBox, genreBox;
	JTextArea notesField;
	JButton addButton, deleteButton, findButton, sortButton;
	DefaultTableModel model;
	
	ArrayList<Book> books = new ArrayList<Book>();
	
	Library(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Library Resource");
		setSize(1600, 500);
		setResizable(false);
		setAutoRequestFocus(true);
		
		GridLayout gLayout = new GridLayout(1,2);
		gLayout.setHgap(20);
		setLayout(gLayout);
		designLeftPanel();
		designRightPanel();
		
		add(leftPanel);
		add(rightPanel);

		setVisible(true);
	}
	
	
	public void designLeftPanel(){
		leftPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
		leftPanel.setLayout(boxLayout);
		
		leftPanel.add(new JLabel("Title"));
		titleField = new JTextField(30);
		titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, titleField.getPreferredSize().height));
		leftPanel.add(titleField);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		leftPanel.add(new JLabel("Author (First Name)"));
		authorFirstField = new JTextField(30);
		authorFirstField.setMaximumSize(new Dimension(Integer.MAX_VALUE, authorFirstField.getPreferredSize().height));
		leftPanel.add(authorFirstField);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		leftPanel.add(new JLabel("Author (Last Name)"));
		authorLastField = new JTextField(30);
		authorLastField.setMaximumSize(new Dimension(Integer.MAX_VALUE, authorLastField.getPreferredSize().height));
		leftPanel.add(authorLastField);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		leftPanel.add(new JLabel("Resource Type"));
		resourceBox = new JComboBox<>();
		resourceBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, resourceBox.getPreferredSize().height));
		leftPanel.add(resourceBox);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		leftPanel.add(new JLabel("Genre"));
		genreBox = new JComboBox<>();
		genreBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, genreBox.getPreferredSize().height));
		leftPanel.add(genreBox);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		leftPanel.add(new JLabel("ISBN"));
		ISBNField = new JTextField(30);
		ISBNField.setMaximumSize(new Dimension(Integer.MAX_VALUE, ISBNField.getPreferredSize().height));
		leftPanel.add(ISBNField);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		leftPanel.add(new JLabel("Notes"));
		notesField = new JTextArea();
		notesField.setLineWrap(true);
		notesField.setWrapStyleWord(true);
		leftPanel.add(new JScrollPane(notesField));
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
	}
	
	public void designRightPanel(){
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		findButton = new JButton("Find");
		findButton.addActionListener(this);
		sortButton = new JButton("Sort");
		sortButton.addActionListener(this);
		
		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(findButton);
		buttonPanel.add(sortButton);
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		JTable table = new JTable();
		
		model = new DefaultTableModel(columnNames,0){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table.setModel(model);
		
		rightPanel.add(new JScrollPane(table), BorderLayout.PAGE_START);
		rightPanel.add(buttonPanel, BorderLayout.PAGE_END);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("Add")){
			String titleText = titleField.getText();
			String authorFirstText = authorFirstField.getText();
			String authorLastText = authorLastField.getText();
			String resourceText = (String) resourceBox.getSelectedItem();
			String genreText = (String) genreBox.getSelectedItem();
			String ISBNText = (String) ISBNField.getText();
			String notesText = (String) notesField.getText();
			
			books.add(new Book(titleText, authorFirstText, authorLastText, resourceText, genreText, ISBNText, notesText));
			model.addRow(new String[]{titleText, authorFirstText, authorLastText, resourceText, genreText, ISBNText});
		}
		else if(command.equals("Delete")){
			
		}
		else if(command.equals("Find")){
			
		}
		else if(command.equals("Sort")){
			
		}
	}
	
	public static void main(String args[]){
		new Library();
	}
	
	public static class Book{
		int arrayIndex;
		String title;
		String authorFirst, authorLast;
		String resource, genre;
		int resourceIndex, genreIndex;
		String ISBN;
		String notes;
		
		Book(String title, String authorFirst, String authorLast, String resource, String genre, String ISBN, String notes){
			this.title = title;
			this.authorFirst = authorFirst;
			this.authorLast = authorLast;
			this.resource = resource;
			this.genre = genre;
			this.ISBN = ISBN;
			this.notes = notes;
		}
	}
	
}
