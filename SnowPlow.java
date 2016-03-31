import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SnowPlow extends JFrame implements ActionListener{
	
	JPanel leftPanel, rightPanel;
	JSpinner rowField, colField;
	JLabel[][] snowLabel;
	boolean[][] visited;
	JButton generateButton, plowButton;	
	int MAXROWS = 8, MAXCOLS = 8;
	Queue<Integer> rowQueue = new LinkedList<Integer>();
	Queue<Integer> colQueue = new LinkedList<Integer>();
	
	public SnowPlow(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Snow Plow");
		setSize(1000, 1000);
		setResizable(false);
		setAutoRequestFocus(true);
		
		GridLayout gLayout = new GridLayout(1, 2);
		gLayout.setHgap(20);
		setLayout(gLayout);
		
		designLeftPanel();
		add(leftPanel);
		rightPanel = new JPanel();
		designRightPanel();
		add(rightPanel);
		
		setVisible(true);
	}
	
	public void designLeftPanel(){
		leftPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
		leftPanel.setLayout(boxLayout);
		SpinnerNumberModel rowModel = new SpinnerNumberModel(
	            new Integer(8), // value
	            new Integer(2), // min
	            new Integer(50), // max
	            new Integer(1) // step
	        );
		SpinnerNumberModel colModel = new SpinnerNumberModel(
	            new Integer(8), // value
	            new Integer(2), // min
	            new Integer(50), // max
	            new Integer(1) // step
	        );
		
		leftPanel.add(new JLabel("Rows"));
		rowField =  new JSpinner(rowModel);
		rowField.setMaximumSize(new Dimension(Integer.MAX_VALUE, rowField.getPreferredSize().height));
		leftPanel.add(rowField);
		
		leftPanel.add(new JLabel("Columns"));
		colField =  new JSpinner(colModel);
		colField.setMaximumSize(new Dimension(Integer.MAX_VALUE, colField.getPreferredSize().height));
		leftPanel.add(colField);
		
		generateButton = new JButton("Generate");
		generateButton.addActionListener(this);
		leftPanel.add(generateButton);
		
		plowButton = new JButton("Plow");
		plowButton.addActionListener(this);
		leftPanel.add(plowButton);
	}
	
	public void designRightPanel(){
		MAXROWS = (int)rowField.getValue();
		MAXCOLS = (int)colField.getValue();
		visited = new boolean[MAXROWS][MAXCOLS];
		rightPanel.removeAll();
		rightPanel.setLayout(new GridLayout(MAXROWS, MAXCOLS));
		snowLabel = new JLabel[MAXROWS][MAXCOLS];
		for(int i = 0; i < MAXROWS; i++){
			for(int j = 0; j < MAXCOLS; j++){
				int randNum = (int)(Math.random() * 2 + 1);
				snowLabel[i][j] = new JLabel(""+randNum){
					@Override
					public void setFont(Font font) {
						super.setFont(new Font("Sans-Serif", Font.BOLD, 16));
					}
				};
				if(randNum == 1){
					snowLabel[i][j].setForeground(Color.RED);
				}
				else if(randNum == 2){
					snowLabel[i][j].setForeground(Color.BLACK);
				}
				rightPanel.add(snowLabel[i][j]);
			}
		}
		rightPanel.revalidate();
		rightPanel.repaint();
	}
	
	public void plow(int row, int col){
		if(row >= MAXROWS || col >= MAXCOLS || row < 0 || col < 0 || !snowLabel[row][col].getText().equals("1") || visited[row][col] == true)
			return;
		visited[row][col] = true;
		rowQueue.add(row);
		colQueue.add(col);
		plow(row+1, col); //Plow down one square
		plow(row-1, col); //Plow up one square
		plow(row, col+1); //Plow right one square
		plow(row, col-1); //Plow left one square
		plow(row+1, col+1); //Plow down right one square
		plow(row+1, col-1); //Plow down left one square
		plow(row-1, col+1); //Plow up right one square
		plow(row-1, col-1); //Plow up left one square

	}
	static int count = 0;
	static Timer t;
	public void beginPlow(){
		rowQueue.clear();
		colQueue.clear();
		generateButton.setEnabled(false);
		plowButton.setEnabled(false);
		for(int i = 0; i < MAXCOLS; i++){
			plow(0, i);
			if(visited[0][i] == true)
				break;
		}
		count = rowQueue.size();
		t = new Timer();
		t.schedule(new UpdateTask(), 0, 80);
	}
	
	private class UpdateTask extends TimerTask {
		@Override
		public void run() {
			count--;
			if(count < 0){
				t.cancel();
				t.purge();
				generateButton.setEnabled(true);
				plowButton.setEnabled(true);
				return;
			}
			int row = rowQueue.poll();
			int col = colQueue.poll();
			snowLabel[row][col].setText("0");
			snowLabel[row][col].setForeground(Color.WHITE);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Generate")){
			designRightPanel();
		}
		else if(e.getActionCommand().equals("Plow")){
			beginPlow();
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run() {
				new SnowPlow();
			}
		});
	}
}
