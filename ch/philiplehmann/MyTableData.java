/*
 * Created on 13.07.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ch.philiplehmann;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.JComboBox;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Color;

import java.util.regex.*;

class MyTableData extends AbstractTableModel {
	private String[] columnNames = { "Netaddress", "IP range", "Broadcast", "Subnetmask", "Host number" };
	private Object[][] data = {};

	MyTableData() {

	}

	public void setData(Object[][] _data) {
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[x].length; y++) {
				fireTableRowsDeleted(x, y);
			}
		}
		data = _data;
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[x].length; y++) {
				fireTableCellUpdated(x, y);
			}
		}
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}
}