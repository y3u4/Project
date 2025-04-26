package ucdf2307ict_oop;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class StudentReschedule extends javax.swing.JFrame {
    private String studentID;
    private int row = -1;
    private String[] columnName = {"Day", "Time", "Lecturer","New Day", "New Time", "New Lecturer", "Status"};
    private DefaultTableModel model = new DefaultTableModel(columnName, 0);
    private File studentAppointmentFile = new File("studentAppointment.txt");
    private File studentRescheduleFile = new File("studentReschedule.txt");
    private File lecSlotsFile = new File("lecturerSlots.txt");


    public StudentReschedule(String studentID) {
        this.studentID = studentID;
        model.setColumnIdentifiers(columnName);
        initComponents();
        loadRescheduleRequests();
    }
    
    private void loadRescheduleRequests() {
        model.setRowCount(0); // Clear existing data

        // Create a list to store the rows before adding to table
        List<Object[]> rows = new ArrayList<>();
        
        // Get current date
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE (dd/MM/yyyy)");

        try (BufferedReader br = new BufferedReader(new FileReader(studentRescheduleFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines

                String[] parts = line.split(",");
                if (parts.length == 8) { // Ensure correct number of columns
                    if (parts[0].equals(studentID) && 
                        !parts[7].equals("Approved") && 
                        !parts[7].equals("Rejected")) {
                        
                        // Extract date from the old day format "Day (dd/MM/yyyy)"
                        String dateStr = parts[1].substring(parts[1].indexOf("(") + 1, parts[1].indexOf(")"));
                        try {
                            // Parse the date
                            Date appointmentDate = dateFormat.parse(parts[1]);
                            
                            // Only add if the date is after current date
                            if (appointmentDate.after(currentDate)) {
                                Object[] rowData = {
                                    parts[1], // Old Day
                                    parts[2], // Old Time
                                    parts[3], // Old Lecturer
                                    parts[4], // New Day
                                    parts[5], // New Time
                                    parts[6], // New Lecturer
                                    parts[7]  // Status
                                };
                                rows.add(rowData);
                            }
                        } catch (ParseException e) {
                            System.out.println("Error parsing date: " + e.getMessage());
                        }
                    }
                }
            }

            // Sort the rows
            Collections.sort(rows, new Comparator<Object[]>() {
                @Override
                public int compare(Object[] row1, Object[] row2) {
                    // First compare dates
                    String date1 = (String) row1[0]; // Old Day
                    String date2 = (String) row2[0]; // Old Day
                    
                    // Extract date from format "Day (dd/MM/yyyy)"
                    String dateStr1 = date1.substring(date1.indexOf("(") + 1, date1.indexOf(")"));
                    String dateStr2 = date2.substring(date2.indexOf("(") + 1, date2.indexOf(")"));
                    
                    // Compare dates
                    int dateCompare = dateStr1.compareTo(dateStr2);
                    if (dateCompare != 0) {
                        return dateCompare;
                    }
                    
                    // If dates are equal, compare times
                    String time1 = (String) row1[1]; // Old Time
                    String time2 = (String) row2[1]; // Old Time
                    
                    // Extract start time from format "hh:mm AM/PM - hh:mm AM/PM"
                    String startTime1 = time1.split(" - ")[0];
                    String startTime2 = time2.split(" - ")[0];
                    
                    return startTime1.compareTo(startTime2);
                }
            });

            // Add sorted rows to the table
            for (Object[] row : rows) {
                model.addRow(row);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading reschedule requests: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        RescheduleRequestTable = new javax.swing.JTable();
        BackButton = new javax.swing.JButton();
        ExitButton = new javax.swing.JButton();
        cancelRequestButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setFont(new java.awt.Font("Modern No. 20", 1, 36)); // NOI18N
        jLabel1.setText("Reschedule List");

        RescheduleRequestTable.setFont(new java.awt.Font("Modern No. 20", 0, 12)); // NOI18N
        RescheduleRequestTable.setModel(model);
        RescheduleRequestTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                RescheduleRequestTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(RescheduleRequestTable);

        BackButton.setBackground(new java.awt.Color(153, 153, 153));
        BackButton.setFont(new java.awt.Font("Modern No. 20", 1, 12)); // NOI18N
        BackButton.setForeground(new java.awt.Color(255, 255, 255));
        BackButton.setText("< Back");
        BackButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        ExitButton.setBackground(new java.awt.Color(255, 0, 51));
        ExitButton.setFont(new java.awt.Font("Modern No. 20", 1, 12)); // NOI18N
        ExitButton.setForeground(new java.awt.Color(255, 255, 255));
        ExitButton.setText("Log out");
        ExitButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });

        cancelRequestButton.setFont(new java.awt.Font("Modern No. 20", 0, 12)); // NOI18N
        cancelRequestButton.setText("Cancel Request");
        cancelRequestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelRequestButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ExitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 50, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(402, 402, 402)
                .addComponent(cancelRequestButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ExitButton)
                    .addComponent(BackButton))
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(cancelRequestButton)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RescheduleRequestTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RescheduleRequestTableMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_RescheduleRequestTableMouseReleased

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        StudentDashboard sd = new StudentDashboard(studentID);
        sd.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackButtonActionPerformed

    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitButtonActionPerformed
        StudentLogin sl = new StudentLogin();
        sl.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ExitButtonActionPerformed

    private void cancelRequestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelRequestButtonActionPerformed
        int row = RescheduleRequestTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a reschedule request to cancel!");
            return;
        }

        try {
            // Get the selected reschedule request details
            String oldDay = (String) model.getValueAt(row, 0);
            String oldTime = (String) model.getValueAt(row, 1);
            String oldLecturer = (String) model.getValueAt(row, 2);
            String newDay = (String) model.getValueAt(row, 3);
            String newTime = (String) model.getValueAt(row, 4);
            String newLecturer = (String) model.getValueAt(row, 5);

            // Remove from studentReschedule.txt
            List<String> remainingReschedules = new ArrayList<>();
            try (Scanner sc = new Scanner(studentRescheduleFile)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine().trim();
                    if (!line.isEmpty()) {
                        String[] parts = line.split(",");
                        // Skip the reschedule request being cancelled
                        if (!(parts[0].equals(studentID) && 
                              parts[1].equals(oldDay) && 
                              parts[2].equals(oldTime) && 
                              parts[3].equals(oldLecturer) &&
                              parts[4].equals(newDay) &&
                              parts[5].equals(newTime) &&
                              parts[6].equals(newLecturer) &&
                              parts[7].equals("Pending"))) {
                            remainingReschedules.add(line);
                        }
                    }
                }
            }

            // Write back to studentReschedule.txt
            try (FileWriter fw = new FileWriter(studentRescheduleFile);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                for (String reschedule : remainingReschedules) {
                    bw.write(reschedule + "\n");
                }
            }

            // Add the new slot back to lecturerSlots.txt
            try (FileWriter fw = new FileWriter(lecSlotsFile, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(newDay + "," + newTime + "," + newLecturer + "\n");
            }

            // Remove from table
            model.removeRow(row);

            JOptionPane.showMessageDialog(this, "Reschedule request cancelled successfully!");
            
            StudentDashboard sd = new StudentDashboard(studentID);
            sd.setVisible(true);
            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cancelling reschedule request: " + e.getMessage());
        }
    }//GEN-LAST:event_cancelRequestButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentReschedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentReschedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentReschedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentReschedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new StudentReschedule().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    private javax.swing.JButton ExitButton;
    private javax.swing.JTable RescheduleRequestTable;
    private javax.swing.JButton cancelRequestButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
