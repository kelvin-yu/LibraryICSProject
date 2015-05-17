import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


public class Library extends JFrame implements ActionListener{
	/* Initialize Variables */
	static Library frame;
	final String[] columnNames = {"Title", "Author (First)", "Author (Last)", "Genre", "ISBN"};
	final String addResourceText = "Add New Resource";
	final String addGenreText = "Add New Genre";
	
	JTable table;
	JPanel leftPanel, rightPanel;
	JTextField titleField, authorLastField, authorFirstField, ISBNField;
	JComboBox<String> resourceBox, genreBox;
	JTextArea notesField;
	JButton addButton, deleteButton, findButton, sortButton, helpButton;
	static DefaultTableModel model;
	static int sortSelection;
	
	/* Arrays to store book records */
	static ArrayList<Book> books = new ArrayList<Book>();
	static ArrayList<String> bookTitles = new ArrayList<String>();
	/* Array to store JComboBox options */
	static ArrayList<String> resources = new ArrayList<String>();
	static ArrayList<String> genres = new ArrayList<String>();
	
	/* Initialize JFrame */
	Library(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Library Resource");
		setSize(1300, 500);
		setResizable(false);
		setAutoRequestFocus(true);
		
		//Separate the frame into 2 parts - left and right
		GridLayout gLayout = new GridLayout(1,2); 
		gLayout.setHgap(20);
		setLayout(gLayout);
		
		//Design the frame
		designLeftPanel();
		add(leftPanel);
		designRightPanel();
		add(rightPanel);

		setVisible(true);
	}
	
	/* Design Left JFrame */
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
		addButton.addActionListener(this);
		addButton.setFont(new Font("Sans-Serif", Font.PLAIN, 15));
		JPanel addPanel = new JPanel(new GridLayout(1, 1));
		addPanel.add(addButton);
		leftPanel.add(addPanel);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
	}
	
	/* Design Right JFrame */
	public void designRightPanel(){
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		findButton = new JButton("Search");
		findButton.addActionListener(this);
		sortButton = new JButton("Sort");
		sortButton.addActionListener(this);
		
		buttonPanel.add(deleteButton);
		buttonPanel.add(findButton);
		buttonPanel.add(sortButton);
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		table = new JTable();
		model = new DefaultTableModel(columnNames,0){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(model);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				if(e.getClickCount() == 2){
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					new BookRecord(row);
				}
			}
		});
		
		rightPanel.add(new JScrollPane(table), BorderLayout.PAGE_START);
		rightPanel.add(buttonPanel, BorderLayout.PAGE_END);
	}
	
	/* Refresh Book Record Table Information */
	public void refreshTable(){
		for(int i = 0; i < model.getRowCount(); i++){
			model.setValueAt(books.get(i).title, i, 0);
			model.setValueAt(books.get(i).authorFirst, i, 1);
			model.setValueAt(books.get(i).authorLast, i, 2);
			model.setValueAt(books.get(i).genre, i, 3);
			model.setValueAt(books.get(i).ISBN, i, 4);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("Add New Record")){
			String titleText = titleField.getText();
			String authorFirstText = authorFirstField.getText();
			String authorLastText = authorLastField.getText();
			String resourceText = (String) resourceBox.getSelectedItem();
			String genreText = (String) genreBox.getSelectedItem();
			String ISBNText = (String) ISBNField.getText();
			String notesText = (String) notesField.getText();
			
			if(titleText.equals("")){
				JOptionPane.showMessageDialog(frame, "Enter a title!", "Title Missing", JOptionPane.ERROR_MESSAGE);
			}
			else if(authorFirstText.equals("")){
				JOptionPane.showMessageDialog(frame, "Enter the author's first name.");
			}
			else if(authorLastText.equals("")){
				JOptionPane.showMessageDialog(frame, "Enter the author's last name.");
			}
			else if(resourceText == null || resourceText.equals(addResourceText)){
				JOptionPane.showMessageDialog(frame, "Choose a resource type.");
			}
			else if(genreText == null || genreText.equals(addGenreText)){
				JOptionPane.showMessageDialog(frame, "Choose a genre");
			}
			else if(ISBNText.equals("")){
				JOptionPane.showMessageDialog(frame, "Enter a ISBN.");
			}
			else{
				if(!bookTitles.contains(titleText)){
					books.add(new Book(titleText, authorFirstText, authorLastText, resourceText, genreText, ISBNText, notesText));
					bookTitles.add(titleText);
					model.addRow(new String[]{titleText, authorFirstText, authorLastText, genreText, ISBNText});
					titleField.setText("");
					authorLastField.setText("");
					authorFirstField.setText("");
					ISBNField.setText("");
					notesField.setText("");
				}
				else{
					JOptionPane.showMessageDialog(frame, "Title already exists!", "Duplicate Title", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(command.equals("Delete")){
			if(table.getSelectedRow() != -1){
				bookTitles.remove(books.get(table.getSelectedRow()).title);
				books.remove(table.getSelectedRow());
				model.removeRow(table.getSelectedRow());
			}
			else{
				JOptionPane.showMessageDialog(frame, "Select a record!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else if(command.equals("Search")){
			String[] selections = { "Title", "Author (First)", "Author (Second)", "Resource Type", "Genre", "ISBN" };
			String selection = (String) JOptionPane.showInputDialog(null, "What do you want to search for? ", "Search", JOptionPane.QUESTION_MESSAGE, null, selections, "Title");
			if(selection != null){
				String input = JOptionPane.showInputDialog("Search for:");
				for(int i = 0; i < books.size(); i++){
					if(selection.equals("Title") && books.get(i).title.equals(input)){
						new BookRecord(i);
					}
					else if(selection.equals("Author (First)") && books.get(i).authorFirst.equals(input)){
						new BookRecord(i);
					}
					else if(selection.equals("Author (Second)") && books.get(i).authorLast.equals(input)){
						new BookRecord(i);
					}
					else if(selection.equals("Resource Type") && books.get(i).resource.equals(input)){
						new BookRecord(i);
					}
					else if(selection.equals("Genre") && books.get(i).genre.equals(input)){
						new BookRecord(i);
					}
					else if(selection.equals("ISBN") && books.get(i).ISBN.equals(input)){
						new BookRecord(i);
					}
				}
			}
		}
		else if(command.equals("Sort")){
			String[] selections = { "Title", "Author (First)", "Author (Second)", "Resource Type", "Genre", "ISBN" };
			String selection = (String) JOptionPane.showInputDialog(null, "What do you want to sort by? ", "Sort", JOptionPane.QUESTION_MESSAGE, null, selections, "Title");
			if(selection != null){
				if(selection.equals("Title"))
					sortSelection = 1;
				else if(selection.equals("Author (First)"))
					sortSelection = 2;
				else if(selection.equals("Author (Second)"))
					sortSelection = 3;
				else if(selection.equals("Resource Type"))
					sortSelection = 4;
				else if(selection.equals("Genre"))
					sortSelection = 5;
				else if(selection.equals("ISBN"))
					sortSelection = 6;
				Collections.sort(books);
				refreshTable();
			}
		}
	}
	
	/* Book class to store book information */
	public static class Book implements Comparable<Book>{
		int arrayIndex;
		String title;
		String authorFirst, authorLast;
		String resource, genre;
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

		/* Sort parameters */
		@Override
		public int compareTo(Book b) {
			if(sortSelection == 1)
				return title.compareTo(b.title);
			if(sortSelection == 2)
				return authorFirst.compareTo(b.authorFirst);
			if(sortSelection == 3)
				return authorLast.compareTo(b.authorLast);
			if(sortSelection == 4)
				return resource.compareTo(b.resource);
			if(sortSelection == 5)
				return genre.compareTo(b.genre);
			if(sortSelection == 6)
				return ISBN.compareTo(b.ISBN);
			return title.compareTo(b.title);
		}
	}
	
	/* JDialog to display book record information */
	public static class BookRecord extends JDialog implements ActionListener{
		/* Initialize Variables */
		int recordIndex;
		JTextArea notesArea;
		JLabel titleLabel, authorFirstLabel, authorLastLabel, resourceLabel, genreLabel, ISBNLabel;
		/* Initialize JDialog */
		BookRecord(int index){
			recordIndex = index; // get the index of the data in the array
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setResizable(false);
			setLayout(new BorderLayout());
			setSize(500, 300);
			
			/* Design book record layout */
			JPanel bigPanel = new JPanel(new GridLayout(2, 2));
			JPanel topPanel = new JPanel(new GridLayout(6, 2));
			JPanel botPanel = new JPanel();
			botPanel.setLayout(new BorderLayout());
			
			topPanel.add(new JLabel("Title:"));
			titleLabel = new JLabel();
			titleLabel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e){
					if(e.getClickCount() == 2){
						String newTitle = JOptionPane.showInputDialog(null, "Edit Title Field", books.get(recordIndex).title);
						if(!bookTitles.contains(newTitle)){
							books.get(recordIndex).title = newTitle;
							bookTitles.set(recordIndex, newTitle);
							titleLabel.setText(newTitle);
							setTitle(newTitle);
						}
						else{
							JOptionPane.showMessageDialog(null, "Title already exists!", "Duplicate Title", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
			topPanel.add(titleLabel);
			
			topPanel.add(new JLabel("Author (First Name):"));
			authorFirstLabel = new JLabel();
			authorFirstLabel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e){
					if(e.getClickCount() == 2){
						String newAuthorFirst = JOptionPane.showInputDialog(null, "Edit Author(First Name) Field", books.get(recordIndex).authorFirst);
						books.get(recordIndex).authorFirst = newAuthorFirst;
						authorFirstLabel.setText(newAuthorFirst);
					}
				}
			});
			topPanel.add(authorFirstLabel);
			
			topPanel.add(new JLabel("Author (Last Name):"));
			authorLastLabel = new JLabel();
			authorLastLabel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e){
					if(e.getClickCount() == 2){
						String newAuthorLast = JOptionPane.showInputDialog(null, "Edit Author(Last Name) Field", books.get(recordIndex).authorLast);
						books.get(recordIndex).authorLast = newAuthorLast;
						authorLastLabel.setText(newAuthorLast);
					}
				}
			});
			topPanel.add(authorLastLabel);
			
			topPanel.add(new JLabel("Resource Type:"));
			resourceLabel = new JLabel();
			resourceLabel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e){
					if(e.getClickCount() == 2){
						String newResource = JOptionPane.showInputDialog(null, "Edit Resource Type Field", books.get(recordIndex).resource);
						books.get(recordIndex).resource = newResource;
						resourceLabel.setText(newResource);
					}
				}
			});
			topPanel.add(resourceLabel);
			
			topPanel.add(new JLabel("Genre:"));
			genreLabel = new JLabel();
			genreLabel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e){
					if(e.getClickCount() == 2){
						String newGenre = JOptionPane.showInputDialog(null, "Edit Genre Field", books.get(recordIndex).genre);
						books.get(recordIndex).genre = newGenre;
						genreLabel.setText(newGenre);
					}
				}
			});
			topPanel.add(genreLabel);
			
			topPanel.add(new JLabel("ISBN:"));
			ISBNLabel = new JLabel();
			ISBNLabel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e){
					if(e.getClickCount() == 2){
						String newISBN = JOptionPane.showInputDialog(null, "Edit Genre Field", books.get(recordIndex).ISBN);
						books.get(recordIndex).ISBN = newISBN;
						ISBNLabel.setText(newISBN);
					}
				}
			});
			topPanel.add(ISBNLabel);
			
			notesArea = new JTextArea();
			notesArea.setLineWrap(true);
			notesArea.setWrapStyleWord(true);
			botPanel.add(new JScrollPane(notesArea));
			
			bigPanel.add(topPanel);
			bigPanel.add(botPanel);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
			JButton prevButton = new JButton("Prev");
			JButton nextButton = new JButton("Next");
			prevButton.addActionListener(this);
			nextButton.addActionListener(this);
			buttonPanel.add(prevButton);
			buttonPanel.add(nextButton);
			
			add(bigPanel, BorderLayout.CENTER);
			add(buttonPanel, BorderLayout.SOUTH);
			
			displayInformation();
			
			setVisible(true);
		}
		
		public void displayInformation(){
			setTitle(books.get(recordIndex).title);
			titleLabel.setText(books.get(recordIndex).title);
			titleLabel.setToolTipText(books.get(recordIndex).title);
			authorFirstLabel.setText(books.get(recordIndex).authorFirst);
			authorFirstLabel.setToolTipText(books.get(recordIndex).authorFirst);
			authorLastLabel.setText(books.get(recordIndex).authorLast);
			authorLastLabel.setToolTipText(books.get(recordIndex).authorLast);
			resourceLabel.setText(books.get(recordIndex).resource);
			resourceLabel.setToolTipText(books.get(recordIndex).resource);
			genreLabel.setText(books.get(recordIndex).genre);
			genreLabel.setToolTipText(books.get(recordIndex).genre);
			ISBNLabel.setText(books.get(recordIndex).ISBN);
			ISBNLabel.setToolTipText(books.get(recordIndex).ISBN);
			notesArea.setText(books.get(recordIndex).notes);
		}
		
		@Override
		public void dispose() {
			books.get(recordIndex).notes = notesArea.getText();
			frame.refreshTable();
			super.dispose();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if(command.equals("Prev")){
				if(recordIndex != 0){
					books.get(recordIndex).notes = notesArea.getText();
					recordIndex--;
					displayInformation();
				}
				else{
					JOptionPane.showMessageDialog(null, "First Page!");
				}
			}
			else if(command.equals("Next")){
				if(recordIndex != model.getRowCount() - 1){
					books.get(recordIndex).notes = notesArea.getText();
					recordIndex++;
					displayInformation();
				}
				else{
					JOptionPane.showMessageDialog(null, "Last Page!");
				}
			}
		}
	}

	public static void main(String args[]){
		frame = new Library();
	}
}
