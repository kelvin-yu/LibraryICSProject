import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


public class Library extends JFrame implements ActionListener{
	static Library frame;
	final String[] columnNames = {"Title", "Author (First)", "Author (Last)", "Genre", "ISBN"};
	final String addResourceText = "Add New Resource";
	final String addGenreText = "Add New Genre";
	
	JPanel leftPanel, rightPanel;
	JTextField titleField, authorLastField, authorFirstField, ISBNField;
	JComboBox<String> resourceBox, genreBox;
	JTextArea notesField;
	JButton addButton, deleteButton, findButton, sortButton;
	DefaultTableModel model;
	
	Book[] books = new Book[100];
	String[] bookTitles = new String[100];
	int currentIndex = 0;
	ArrayList<String> resources = new ArrayList<String>();
	ArrayList<String> genres = new ArrayList<String>();
	
	Library(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Library Resource");
		setSize(1300, 500);
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
		
		leftPanel.add(new JLabel("Title"){
			@Override
			public void setFont(Font font) {
				super.setFont(new Font("Sans-Serif", Font.BOLD, 15));
			}
		});
		titleField = new JTextField(30);
		titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, titleField.getPreferredSize().height));
		leftPanel.add(titleField);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		leftPanel.add(new JLabel("Author (First Name)"){
			@Override
			public void setFont(Font font) {
				super.setFont(new Font("Sans-Serif", Font.BOLD, 15));
			}
		});
		authorFirstField = new JTextField(30);
		authorFirstField.setMaximumSize(new Dimension(Integer.MAX_VALUE, authorFirstField.getPreferredSize().height));
		leftPanel.add(authorFirstField);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		leftPanel.add(new JLabel("Author (Last Name)"){
			@Override
			public void setFont(Font font) {
				super.setFont(new Font("Sans-Serif", Font.BOLD, 15));
			}
		});
		authorLastField = new JTextField(30);
		authorLastField.setMaximumSize(new Dimension(Integer.MAX_VALUE, authorLastField.getPreferredSize().height));
		leftPanel.add(authorLastField);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		leftPanel.add(new JLabel("Resource Type"){
			@Override
			public void setFont(Font font) {
				super.setFont(new Font("Sans-Serif", Font.BOLD, 14));
			}
		});
		resourceBox = new JComboBox<>();
		resourceBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, resourceBox.getPreferredSize().height));
		resourceBox.addItem(addResourceText);
		resources.add(addResourceText);
		resourceBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> jb = (JComboBox<String>) e.getSource();
				if(jb.getSelectedItem().equals(addResourceText)){
					jb.setSelectedIndex(0);
					String newResource = JOptionPane.showInputDialog(frame, "Enter a new resource", "New Resource", JOptionPane.PLAIN_MESSAGE);
					if(newResource != null && newResource.equals("")){
						JOptionPane.showMessageDialog(frame, "Can't be blank!", "Resource", JOptionPane.ERROR_MESSAGE);
					}
					else if(newResource != null){
						jb.addItem(newResource);
						resources.add(newResource);
						jb.setSelectedIndex(resources.size() - 1);
					}
				}
			}
		});
		leftPanel.add(resourceBox);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		leftPanel.add(new JLabel("Genre"){
			@Override
			public void setFont(Font font) {
				super.setFont(new Font("Sans-Serif", Font.BOLD, 15));
			}
		});
		genreBox = new JComboBox<>();
		genreBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, genreBox.getPreferredSize().height));
		genreBox.addItem(addGenreText);
		genres.add(addGenreText);
		genreBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> jb = (JComboBox<String>) e.getSource();
				if(jb.getSelectedItem().equals(addGenreText)){
					jb.setSelectedIndex(0);
					String newGenre = JOptionPane.showInputDialog(frame, "Enter a new genre", "New Genre", JOptionPane.PLAIN_MESSAGE);
					if(newGenre != null && newGenre.equals("")){
						JOptionPane.showMessageDialog(frame, "Can't be blank!", "Genre", JOptionPane.ERROR_MESSAGE);
					}
					else if(newGenre != null){
						jb.addItem(newGenre);
						genres.add(newGenre);
						jb.setSelectedIndex(genres.size() - 1);
					}
				}
			}
		});
		leftPanel.add(genreBox);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		leftPanel.add(new JLabel("ISBN"){
			@Override
			public void setFont(Font font) {
				super.setFont(new Font("Sans-Serif", Font.BOLD, 15));
			}
		});
		ISBNField = new JTextField(30);
		ISBNField.setMaximumSize(new Dimension(Integer.MAX_VALUE, ISBNField.getPreferredSize().height));
		leftPanel.add(ISBNField);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		leftPanel.add(new JLabel("Notes"){
			@Override
			public void setFont(Font font) {
				super.setFont(new Font("Sans-Serif", Font.BOLD, 15));
			}
		});
		notesField = new JTextArea();
		notesField.setLineWrap(true);
		notesField.setWrapStyleWord(true);
		leftPanel.add(new JScrollPane(notesField));
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		addButton = new JButton("Add New Record");
		addButton.setFont(new Font("Arial", Font.PLAIN, 15));
		JPanel addPanel = new JPanel(new BorderLayout());
		addPanel.add(addButton, BorderLayout.SOUTH);
		leftPanel.add(addPanel);
		
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
	}
	
	public void designRightPanel(){
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		findButton = new JButton("Find");
		findButton.addActionListener(this);
		sortButton = new JButton("Sort");
		sortButton.addActionListener(this);
		
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
		model.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				
			}
		});
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
			
			if(titleText.equals("")){
				JOptionPane.showMessageDialog(null, "Enter a title.");
			}
			else if(authorFirstText.equals("")){
				JOptionPane.showMessageDialog(null, "Enter the author's first name.");
			}
			else if(authorLastText.equals("")){
				JOptionPane.showMessageDialog(null, "Enter the author's last name.");
			}
			else if(resourceText == null || resourceText.equals(addResourceText)){
				JOptionPane.showMessageDialog(null, "Choose a resource type.");
			}
			else if(genreText == null || genreText.equals(addGenreText)){
				JOptionPane.showMessageDialog(null, "Choose a genre");
			}
			else if(ISBNText.equals("")){
				JOptionPane.showMessageDialog(null, "Enter a ISBN.");
			}
			else{
				if(!duplicateTitle(titleText)){
					books[currentIndex] = new Book(titleText, authorFirstText, authorLastText, resourceText, genreText, ISBNText, notesText);
					bookTitles[currentIndex] = titleText;
					model.addRow(new String[]{titleText, authorFirstText, authorLastText, genreText, ISBNText});
					currentIndex++;
				}
				else{
					JOptionPane.showMessageDialog(frame, "Title already exists!", "Duplicate Title", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(command.equals("Delete")){
			
		}
		else if(command.equals("Search")){
			JDialog dialog = new JDialog(frame, "Search", true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		else if(command.equals("Sort")){
			JDialog dialog = new JDialog(frame, "Sort", true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
	}
	
	boolean duplicateTitle(String title){
		for(int i = 0; i < 100; i++){
			if(title.equals(bookTitles[i]))
				return true;
		}
		return false;
	}
	
	public static void main(String args[]){
		frame = new Library();
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
