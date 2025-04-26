
package ucdf2307ict_oop;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LecturerAppointment extends javax.swing.JFrame {
    private String Username;
    private String appointmentDetails;
    private String feedbackStatus;
    private String[] columnName = {"Student ID","Day", "Time", "Lecturer"};//the column name that you want to display on table
    private String[] pastcolumnName = {"Day", "Time", "Lecturer","Feedback Status"};
    private DefaultTableModel upcomingmodel = new DefaultTableModel();
    private DefaultTableModel pastmodel = new DefaultTableModel();
    private int row = -1;
    private File studentAppointmentFile = new File("studentAppointment.txt");
    private File lecFeedbackStatusile = new File("LecturerFeedbackStatusFile.txt");
    private File lecFeedbackFile = new File("LecturerFeedbackFile.txt");
    private File lecturerAppointmentFile = new File("lecturerAppointment.txt");
    
    public LecturerAppointment(String Username) throws ParseException {
        upcomingmodel.setColumnIdentifiers(columnName);
        pastmodel.setColumnIdentifiers(pastcolumnName);
        this.Username = Username;
        initComponents();
        getLecturerAppointment();
    }
        
    private void getLecturerAppointment() throws ParseException {
        // Define file paths
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE (dd/MM/yyyy),hh:mm a - hh:mm a");
        Date currentDate = new Date();

        // Clear the table models to avoid duplicate entries when reloading
        upcomingmodel.setRowCount(0);
        pastmodel.setRowCount(0);

        try (BufferedReader reader = new BufferedReader(new FileReader(studentAppointmentFile));
            BufferedWriter lecturerWriter = new BufferedWriter(new FileWriter(lecturerAppointmentFile, true))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) continue;

                String studentID = parts[0].trim();
                String day = parts[1].trim();
                String time = parts[2].trim();
                String lecturerName = parts[3].trim();

                // Filter appointments by lecturer
                if (!lecturerName.equalsIgnoreCase(this.Username)) continue;

                // Parse date and time
                Date appointmentDateTime = dateFormat.parse(day + "," + time);
                
                // Add the appointment to lecturerAppointment.txt if not already present
                String appointmentEntry = studentID + "," + day + "," + time + "," + lecturerName;
                if (!isAppointmentInFile(appointmentEntry)) {
                    lecturerWriter.write(appointmentEntry);
                    lecturerWriter.newLine();
                }

                // Add to appropriate table model
                if (appointmentDateTime.after(currentDate)) {
                    upcomingmodel.addRow(new Object[]{studentID, day, time, lecturerName});
                } else {
                    // Assuming feedback status as "Not Provided" for past appointments
                    pastmodel.addRow(new Object[]{day, time, lecturerName, "Not Provided"});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading appointments file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
        checkFeedbackStatus();
    }
    
        private void checkFeedbackStatus() {
            try (BufferedReader feedbackReader = new BufferedReader(new FileReader(lecFeedbackFile))) {
                String feedbackLine;

                // Read feedback file line by line
                while ((feedbackLine = feedbackReader.readLine()) != null) {
                    // Parse the line for day, time, lecturer, and feedback
                    String[] feedbackParts = feedbackLine.split(",");
                    if (feedbackParts.length != 4) continue;

                    String feedbackDay = feedbackParts[0].trim();
                    String feedbackTime = feedbackParts[1].trim();
                    String feedbackLecturer = feedbackParts[2].trim();
                    String feedbackContent = feedbackParts[3].trim();

                    // Match with lecturer appointments
                    for (int i = 0; i < pastmodel.getRowCount(); i++) {
                        String appointmentDay = pastmodel.getValueAt(i, 0).toString().trim();
                        String appointmentTime = pastmodel.getValueAt(i, 1).toString().trim();
                        String appointmentLecturer = pastmodel.getValueAt(i, 2).toString().trim();

                        if (feedbackDay.equalsIgnoreCase(appointmentDay)
                                && feedbackTime.equalsIgnoreCase(appointmentTime)
                                && feedbackLecturer.equalsIgnoreCase(appointmentLecturer)) {
                            // Check if feedback is provided
                            if (!feedbackContent.isEmpty()) {
                                pastmodel.setValueAt("Provided", i, 3);
                            } else {
                                pastmodel.setValueAt("Not Provided", i, 3);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading feedback file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
    // Helper method to check if an appointment already exists in lecturerAppointment.txt
    private boolean isAppointmentInFile(String appointmentEntry) {
        try (BufferedReader lecturerReader = new BufferedReader(new FileReader(lecturerAppointmentFile))) {
            String line;
            while ((line = lecturerReader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase(appointmentEntry)) {
                    return true;
                }
            }
        } catch (IOException e) {
            Logger.getLogger(LecturerAppointment.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        DashboardPageButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        ProvideFeedbackButton1 = new javax.swing.JButton();
        ViewFeedbackButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 494));

        jTable1.setModel(upcomingmodel);
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        resizeTableColumns();
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Modern No. 20", 1, 24)); // NOI18N
        jLabel1.setText("Upcoming Appointment");

        DashboardPageButton1.setFont(new java.awt.Font("Modern No. 20", 0, 16)); // NOI18N
        DashboardPageButton1.setText("Back to Dashboard");
        DashboardPageButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DashboardPageButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(DashboardPageButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(DashboardPageButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));

        jTable2.setModel(pastmodel);
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        resizeTable2Columns();
        jScrollPane2.setViewportView(jTable2);

        jLabel2.setFont(new java.awt.Font("Modern No. 20", 1, 24)); // NOI18N
        jLabel2.setText("Past Appointment");

        ProvideFeedbackButton1.setFont(new java.awt.Font("Modern No. 20", 0, 18)); // NOI18N
        ProvideFeedbackButton1.setText("Provide Feedback");
        ProvideFeedbackButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProvideFeedbackButton1ActionPerformed(evt);
            }
        });

        ViewFeedbackButton2.setFont(new java.awt.Font("Modern No. 20", 0, 18)); // NOI18N
        ViewFeedbackButton2.setText("View Student Feedback");
        ViewFeedbackButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewFeedbackButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ViewFeedbackButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ProvideFeedbackButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(138, 138, 138))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(ProvideFeedbackButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ViewFeedbackButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ViewFeedbackButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewFeedbackButton2ActionPerformed
        LecturerViewStudentFeedback vsf = new LecturerViewStudentFeedback(Username);
        vsf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ViewFeedbackButton2ActionPerformed

    private void ProvideFeedbackButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProvideFeedbackButton1ActionPerformed
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow != -1){
            String day = (String) pastmodel.getValueAt(selectedRow, 0);
            String time = (String) pastmodel.getValueAt(selectedRow, 1);
            String status = (String) pastmodel.getValueAt(selectedRow, 3);
            if (!status.equalsIgnoreCase("Provided")) {
                appointmentDetails = day + ", " + time;
                try {
                    LecturerFeedback lf = new LecturerFeedback(Username, appointmentDetails);
                    lf.setVisible(true);
                    this.dispose();
                } catch (IOException ex) {
                    Logger.getLogger(LecturerAppointment.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "This appointment already has feedback provided.");
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Please select an appointment to provide feedback.");
        }
        
    }//GEN-LAST:event_ProvideFeedbackButton1ActionPerformed

    private void DashboardPageButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboardPageButton1ActionPerformed
        LecturerDashboard ld = new LecturerDashboard(Username);
        ld.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_DashboardPageButton1ActionPerformed

    private void resizeTableColumns() {
        // Access the column model of the table
        javax.swing.table.TableColumnModel columnModel = jTable1.getColumnModel();
        // Set preferred widths for each column
        columnModel.getColumn(0).setPreferredWidth(80); // Student ID
        columnModel.getColumn(1).setPreferredWidth(140); // Day
        columnModel.getColumn(2).setPreferredWidth(140); // Time
        columnModel.getColumn(3).setPreferredWidth(145); // Lecturer
    }
    
    private void resizeTable2Columns() {
        // Access the column model of the table
        javax.swing.table.TableColumnModel pastcolumnModel = jTable2.getColumnModel();
        // Set preferred widths for each column
        pastcolumnModel.getColumn(0).setPreferredWidth(140); // Day
        pastcolumnModel.getColumn(1).setPreferredWidth(140); // Time
        pastcolumnModel.getColumn(2).setPreferredWidth(145); // Lecturer
        pastcolumnModel.getColumn(3).setPreferredWidth(110); // Feedback Status
    }
    
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
            java.util.logging.Logger.getLogger(LecturerAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LecturerAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LecturerAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LecturerAppointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new LecturerAppointment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DashboardPageButton1;
    private javax.swing.JButton ProvideFeedbackButton1;
    private javax.swing.JButton ViewFeedbackButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
