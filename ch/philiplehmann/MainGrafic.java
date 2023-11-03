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

/**
 * @author philip.lehmann
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MainGrafic extends JFrame {

	public JTextField ipAddress;
	public JTextField subnetmask;
	public JTable result;
	public JPanel top;
	public MyTableData model;
	public JScrollPane sp;

	private void create() {
		if (!checkValues(ipAddress.getText(), subnetmask.getText()))
			return;
		model.setData(createSubnet(ipAddress.getText(), subnetmask.getText()));
		result = new JTable(model);
		result.setTableHeader(result.getTableHeader());
		// getContentPane().removeAll();
		getContentPane().remove(sp);
		sp = new JScrollPane(result);
		getContentPane().add(sp, BorderLayout.CENTER);
		setVisible(true);
	}

	public MainGrafic() {
		setTitle("Subnet Calculator - Philip Lehmann");
		setSize(500, 600);
		getContentPane().setLayout(new BorderLayout());

		final JLabel lipAddress = new JLabel("IP Address");
		final JLabel lsubnetmask = new JLabel("Subnetmask");

		String [] netClassItems = { "A", "B", "C" };
		final JComboBox netClass = new JComboBox<String>(netClassItems);

		String [] netNumberItems = { "1", "2", "4", "8", "16", "32", "64", "128" };
		final JComboBox netNumber = new JComboBox<String>(netNumberItems);

		top = new JPanel(new GridLayout(2, 5, 5, 5));
		top.setSize(500, 50);
		ipAddress = new JTextField("192.168.0.20");
		subnetmask = new JTextField("255.255.255.0");
		ipAddress.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if (!checkValues(ipAddress.getText(), subnetmask.getText())) {
					ipAddress.setBackground(Color.RED);
				} else {
					ipAddress.setBackground(Color.WHITE);
					create();
				}
			}

			public void keyTyped(KeyEvent e) {
			}
		});
		subnetmask.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if (!checkValues(ipAddress.getText(), subnetmask.getText())) {
					subnetmask.setBackground(Color.RED);
				} else {
					subnetmask.setBackground(Color.WHITE);
					String[] split = subnetmask.getText().split("[.]");
					int netNumber1 = 0;
					int netClass1 = 0;
					for (int i = 0; i < 4; i++) {
						netClass1 = i;
						if (split[i].equals("255"))
							continue;
						else {
							switch (Integer.parseInt(split[i])) {
							case 0:
								netNumber1 = 0;
								break;
							case 128:
								netNumber1 = 1;
								break;
							case 192:
								netNumber1 = 2;
								break;
							case 224:
								netNumber1 = 3;
								break;
							case 240:
								netNumber1 = 4;
								break;
							case 248:
								netNumber1 = 5;
								break;
							case 252:
								netNumber1 = 6;
								break;
							case 254:
								netNumber1 = 7;
								break;
							}
							break;
						}
					}
					netClass.setSelectedIndex(netClass1 - 1);
					netNumber.setSelectedIndex(netNumber1);
					create();
				}
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		netClass.setSelectedIndex(2);
		netNumber.setSelectedIndex(0);
		ItemListener il = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int n = netClass.getSelectedIndex() + 1;
				String str = "";
				for (int i = 0; i < n; i++) {
					str += "255.";
				}
				switch (netNumber.getSelectedIndex()) {
				case 0:
					str += "0";
					break;
				case 1:
					str += "128";
					break;
				case 2:
					str += "192";
					break;
				case 3:
					str += "224";
					break;
				case 4:
					str += "240";
					break;
				case 5:
					str += "248";
					break;
				case 6:
					str += "252";
					break;
				case 7:
					str += "254";
					break;
				}
				n++;
				for (int i = 0; i < 4 - n; i++) {
					str += ".0";
				}
				subnetmask.setText(str);

				create();
			}
		};
		netClass.addItemListener(il);
		netNumber.addItemListener(il);

		top.add(lipAddress);
		top.add(ipAddress);
		top.add(lsubnetmask);
		top.add(subnetmask);
		top.add(new JPanel());
		top.add(new JLabel("Net Class"));
		top.add(netClass);
		top.add(new JPanel());
		top.add(new JLabel("Subnet number"));
		top.add(netNumber);

		model = new MyTableData();
		model.setData(createSubnet(ipAddress.getText(), subnetmask.getText()));
		result = new JTable(model);
		result.setTableHeader(result.getTableHeader());

		sp = new JScrollPane(result);

		getContentPane().add(top, BorderLayout.NORTH);
		getContentPane().add(sp, BorderLayout.CENTER);
		addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			public void windowDeactivated(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowOpened(WindowEvent e) {
			}
		});
	}

	public void load() {
		setVisible(true);
	}

	private boolean checkValues(String ip, String net) {
		Pattern p = Pattern.compile("[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}");
		Matcher m1 = p.matcher(ip);
		Matcher m2 = p.matcher(net);
		if (!m1.matches() || !m2.matches())
			return false;
		String[] ipArr = ip.split("[.]");
		String[] netArr = net.split("[.]");
		if (ipArr.length != 4 || netArr.length != 4)
			return false;

		String bits = "";
		for (int i = 0; i < 4; i++) {
			if (!(Integer.parseInt(ipArr[i]) <= 255 && Integer.parseInt(ipArr[i]) >= 0))
				return false;
			if (!(Integer.parseInt(netArr[i]) <= 255 && Integer.parseInt(netArr[i]) >= 0))
				return false;
			String str = Integer.toBinaryString(Integer.parseInt(netArr[i]));
			while (str.length() < 8) {
				str = "0" + str;
			}
			bits += str;
		}
		boolean test = false;
		for (int i = 0; i < 32; i++) {
			if (bits.charAt(i) == 48)
				test = true;
			if (test && bits.charAt(i) == 49)
				return false;
		}
		return true;
	}

	private Object[][] createSubnet(String ip, String net) {
		String[] ipArr = ip.split("[.]");
		String[] netArr = net.split("[.]");
		if (ipArr.length != 4 || netArr.length != 4)
			return new Object[0][5];
		String bits = "";
		for (int i = 0; i < 4; i++) {
			String str = Integer.toBinaryString(Integer.parseInt(netArr[i]));
			while (str.length() < 8) {
				str = "0" + str;
			}
			bits += str;
		}
		int netBits = 0;
		for (int i = 0; i < 32; i++) {
			if (bits.charAt(i) == 49)
				netBits++;
		}
		int bitsForSubnet = netBits % 8;
		int numberSubnet = hoch(2, bitsForSubnet);

		int ipParts = netBits / 8;
		String firstIp = "";
		for (int i = 0; i < ipParts; i++) {
			firstIp += ipArr[i] + ".";
		}

		Object[][] data = new Object[numberSubnet][5];
		for (int i = 0; i < numberSubnet; i++) {
			// create networkname
			int netName = i << (8 - bitsForSubnet);
			String networkName = firstIp + netName;
			for (int u = ipParts + 1; u < 4; u++) {
				networkName += ".0";
			}
			data[i][0] = networkName;
			// end create networkname

			// create ip range
			String[] netsplit = networkName.split("[.]");
			String fip = netsplit[0] + "." + netsplit[1] + "." + netsplit[2] + "."
					+ (Integer.parseInt(netsplit[3]) + 1);
			netName = (i + 1 << (8 - bitsForSubnet)) - 1;
			networkName = firstIp + netName;
			for (int u = ipParts + 1; u < 4; u++) {
				networkName += ".255";
			}
			// broadcast
			data[i][2] = networkName;
			// end broadcast
			netsplit = networkName.split("[.]");
			String lip = netsplit[0] + "." + netsplit[1] + "." + netsplit[2] + "."
					+ (Integer.parseInt(netsplit[3]) - 1);
			data[i][1] = fip + " - " + lip;
			// end create ip range
			data[i][3] = net;
			data[i][4] = Integer.toString(hoch(2, 32 - netBits) - 2);
		}
		return data;
	}

	private int hoch(int basis, int exponent) {
		int number = 1;
		for (int i = 0; i < exponent; i++) {
			number *= basis;
		}
		return number;
	}
}
