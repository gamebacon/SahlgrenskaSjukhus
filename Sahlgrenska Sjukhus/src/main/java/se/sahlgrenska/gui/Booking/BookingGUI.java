package se.sahlgrenska.gui.Booking;

import se.sahlgrenska.gui.util.HelperGUI;
import se.sahlgrenska.gui.util.UtilGUI;
import se.sahlgrenska.main.Driver;
import se.sahlgrenska.main.Util;
import se.sahlgrenska.sjukhus.Booking;
import se.sahlgrenska.sjukhus.Hospital;
import se.sahlgrenska.sjukhus.Ward;
import se.sahlgrenska.sjukhus.person.employee.Accessibility;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class BookingGUI extends HelperGUI {
    private Hospital hospital;
    private JPanel mainPanel;
    private JPanel bannerPanel;
    private JPanel bookingPanel;
    private JLabel userLbl;
    private JLabel titleLbl;
    private JLabel userOutLbl;
    private JLabel dateOutLbl;
    private JPanel menuPanel;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JPanel bottomPanel;
    private JTextArea noteTxtArea;
    private JLabel noteLbl;
    private JPanel noteTxtAreaPanel;
    private JPanel bookingBtnPanel;
    private JButton cancelBtn;
    private JButton createBtn;
    private JTextField patPersNrTxtField;
    private JLabel persLbl;
    private JPanel persNrFieldPanel;
    private JTextField bookingDateTxtField;
    private JLabel bookingDateLbl;
    private JPanel bookingFieldPanel;
    private JComboBox bookingDurationComboBox;
    private JPanel bookingDurationFieldPanel;
    private JLabel bookingDurationLbl;
    private JList participationList;
    private JButton addPartBtn;
    private JButton removePartBtn;
    private JLabel participationListLbl;
    private JPanel participationPanel;
    private JPanel bookingLocationPanel;
    private JPanel wardPanel;
    private JPanel roomPanel;
    private JComboBox wardComboBox;
    private JComboBox roomComboBox;
    private JLabel wardLbl;
    private JLabel roomLbl;
    private JPanel neededItemsPanel;
    private JTable itemsTable;
    private JButton addItemsBtn;
    private JButton removeItemsBtn;
    private JPanel titlePanel;
    private JScrollPane itemScrollPanel;
    private LocalDateTime date;


    private Booking booking;
    private int minWindowSize = 600;
    private int maxWindowSize = 700;




    public BookingGUI() {
        init(mainPanel, "Skapa bokning", new Dimension(minWindowSize, maxWindowSize), Accessibility.RECEPTIONIST);

        setUpBookingData();

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Driver.getMainMenu().setVisible(true);
            }
        });

        participationList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
            }
        });
    }


    private void setUpBookingData() {
        dateOutLbl.setText(LocalDateTime.now().format(Util.dateFormatter));
        itemsTable.setBackground(Color.WHITE);
        roomComboBox.setEnabled(false);
        removeItemsBtn.setEnabled(false);
        removePartBtn.setEnabled(false);
  //      fillItemTableFromRoom();
 //       wardComboBox.addItem(hospital.getWards());

    }



    private String [] fillItemTableFromRoom(ArrayList<Ward> wards){

        String [] wardNames = new String[wards.size()];
        for(int i = 0; i < wards.size() ;i++){
            wardNames[i] = wards.get(i).getName();
        }
     return wardNames;
    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
        Color tableHeaderColour = new Color(199, 199, 199);
        hospital = Driver.getHospital();
        String [] wardNames = fillItemTableFromRoom(hospital.getWards());
        wardComboBox = new JComboBox(wardNames);


        String[] columns = {"Item name", "Quantity"};
        String[][] data = {{"Defibrilator", "5"}, {"MRI", "2"}, {"Panodil", "10"},{"Defibrilator", "5"}, {"MRI", "2"}, {"Panodil", "10"}, {"Defibrilator", "5"}, {"MRI", "2"}, {"Panodil", "10"}, {"Defibrilator", "5"}, {"MRI", "2"}, {"Panodil", "10"}};
        itemsTable = new JTable(data, columns);
        UtilGUI.changeJTableHeaderColour(itemsTable, tableHeaderColour);


    }
}
