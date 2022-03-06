package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.stream.IntStream;

import javax.swing.JPanel;

import View.JPanelBoardGame;
import View.JPanelControl;

public class ControlGame extends JPanel{
	
	public ControlGame() {
		
	}
	public void actionStartGame(JPanelBoardGame gamePanel,JPanelControl controlPanel) {
		controlPanel.btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame(gamePanel, controlPanel);

			}
		});
	}
	public void startGame(JPanelBoardGame gamePanel,JPanelControl controlPanel) {
		controlPanel.btnStart.setEnabled(false);
		gamePanel.game=new BoardGame();
		gamePanel.game.generate(gamePanel.game, 0);
		gamePanel.game.generateBlankCell(gamePanel.game);
		gamePanel.game.copyToCellBig(gamePanel.game);
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(gamePanel.game.board[i].c[j].val==0) {
					gamePanel.jpCellBig[i].btnCellBig[j].setText("");
					gamePanel.jpCellBig[i].btnCellBig[j].setForeground(Color.RED);
				}
				else gamePanel.jpCellBig[i].btnCellBig[j].setText(gamePanel.game.board[i].c[j].val+"");	
			}
			gamePanel.add(gamePanel.jpCellBig[i]);
		}
		playGame(gamePanel);
	}
	public void playGame(JPanelBoardGame gamePanel) {
		for(int nb=0;nb<9;nb++) {
			for(int n=0;n<9;n++) {
				if( gamePanel.game.board[nb].c[n].val==0) 
					actionClickCell(gamePanel,nb,n);
			}
		}
	}
	public void actionSolve(JPanelBoardGame gamePanel,JPanelControl controlPanel) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		controlPanel.btnSolve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				solveGame(gamePanel, list);
				for (int x = 0; x < list.size(); x++) {
					System.out.println(list.get(x));
				}
			}
		});
	}
	public boolean solveGame(JPanelBoardGame jp,ArrayList<Integer> list) {
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(jp.game.matrix[i][j].val==0) {
					for(int k=1;k<=9;k++) {
						jp.game.matrix[i][j].val=k;
						jp.jpCellBig[(i/3)*3+(j/3)].btnCellBig[(i%3)*3+(j%3)].setText(k+"");
						list.add(k);
//						try {	
//							Thread.sleep(1000);						
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						System.out.println(i+","+j+":"+k);
						if(jp.game.isValid(jp.game, i, j) && solveGame(jp,list)) return true;
						jp.game.matrix[i][j].val=0;
						jp.jpCellBig[(i/3)*3+(j/3)].btnCellBig[(i%3)*3+(j%3)].setText("");
					}
					System.out.println("Quay lui");
					return false;
				}
			}
		}
		return true;
	}
	
	public void actionClickCell(JPanelBoardGame gamePanel,int xb,int x) {
		int i=gamePanel.game.findRow(gamePanel.game.board[xb].idxMatrix[x]);
		int j=gamePanel.game.findCol(gamePanel.game.board[xb].idxMatrix[x],gamePanel.game.findRow(gamePanel.game.board[xb].idxMatrix[x]));
		
		gamePanel.jpCellBig[xb].btnCellBig[x].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println(xb+","+x+","+gamePanel.game.board[xb].idxMatrix[x]);
					System.out.println(i+","+j);
					gamePanel.game.matrix[i][j].val=0;
					gamePanel.game.matrix[i][j].nb.resetNum();
					if(gamePanel.game.checkLose(gamePanel.game,i,j))
						gamePanel.jpCellBig[xb].btnCellBig[x].setBackground(Color.RED);
					gamePanel.game.matrix[i][j].nb.showNum();
				}
			});
		gamePanel.jpCellBig[xb].btnCellBig[x].addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				gamePanel.jpCellBig[xb].btnCellBig[x].setBackground(Color.WHITE);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				int v = e.getKeyCode();
				if ((v >= 49 && v <= 57) || (v >= 97 && v <= 105)) {					
					if (v >= 49 && v <= 57)
						v -= 48;
					if (v >= 97 && v <= 105)
						v -= 96;
					
//					if(gamePanel.game.checkLose(gamePanel.game,i,j)) {
//						gamePanel.jpCellBig[xb].btnCellBig[x].setBackground(Color.RED);
//						System.out.println("YOU LOSE");
//					}
					//Neu so nhap vao da duoc dung thi se hien ra la trung lap
					if(gamePanel.game.isValid(gamePanel.game, i, j)) {
						gamePanel.jpCellBig[xb].btnCellBig[x].setBackground(Color.RED);
						System.out.println("TRUNG LAP");
					}
					else {
						gamePanel.jpCellBig[xb].btnCellBig[x].setText(v+"");
						gamePanel.game.matrix[i][j].val=v;
//						gamePanel.game.markUsed(gamePanel.game, i, j);
						gamePanel.game.showBoard();
					}
				}
				else {
					gamePanel.jpCellBig[xb].btnCellBig[x].setBackground(Color.RED);
					System.out.println("HAY NHAP SO");
				}
				if(gamePanel.game.checkFinish(gamePanel.game)) System.out.println("YOU WIN");
			}
		});
		
		gamePanel.jpCellBig[xb].btnCellBig[x].addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				gamePanel.jpCellBig[xb].btnCellBig[x].setBackground(new Color(102,46,28));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
//				for(int u=0;u<9;u++) {
//					
//				}
//				System.out.println(xb+","+x);
				gamePanel.jpCellBig[xb].btnCellBig[x].setBackground(new Color(235,220,178));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
